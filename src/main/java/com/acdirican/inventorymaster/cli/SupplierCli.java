package com.acdirican.inventorymaster.cli;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.acdirican.inventorymaster.model.Supplier;
import com.acdirican.inventorymaster.repository.ProductRepository;
import com.acdirican.inventorymaster.repository.SupplierRepository;

public class SupplierCli extends AbstractCLi{
	private SupplierRepository supplierRepository;
	public SupplierCli(Cli cli, SupplierRepository supplierRepository) {
		super(cli);
		this.supplierRepository = supplierRepository;
		this.scanner = Utils.scanner;
	}

	String delete_all(List<Integer> id_list) {
		if (!Utils.confirm("Are you sure to delete all the supplier with the given IDs? [y/n]")) {
			return "Delete cancelled";
		}

		if (id_list.size() == 0) {
			return "No supplier ID for deletion!";
		}

		int result = supplierRepository.deleteAll(id_list);
		if (result == id_list.size()) {
			return "All suppliers deleted succesfull.";
		} else if (result > 0) {
			return "Products partially deleted.";
		} else if (result == 0) {
			return "No supplier deleted.";
		}

		return Cli.DBERROR;

	}

	
	String find(String name) {

		List<Supplier> suppliers = supplierRepository.find(name);
		if (suppliers == null) {
			return Cli.DBERROR;
		}
		printSupplierList(suppliers);
		return suppliers.size() + " suppliers have been successfull listed.";

	}

	

	String update(int ID) {
		Supplier supplier = null;
		
		supplier = supplierRepository.get(ID);
		if (supplier == null) {
			return Cli.DBERROR;
		}

		if (supplier != null) {
			Utils.line();
			System.out.println(supplier);
			Utils.line();
			System.out.println("Live empty if you don't want to update the field!");
			System.out.println("Enter supplier name:");
			String name = scanner.nextLine().trim();
			System.out.println("Enter supplier quantity:");
			String quantityStr = scanner.nextLine().trim();
			Utils.line();

			if (!name.equals("")) {
				supplier.setName(name);
			}

			if (supplierRepository.update(supplier)) {
				return "Supplier succesfully updated!";
			}
		}
		return Cli.ERROR + "Supplier with the ID " + ID + " could not be found!";
	}

	String delete(int ID) {
		if (!Utils.confirm("Are you sure to delete? [y/n]")) {
			return "Delete cancelled";
		}

		boolean result = supplierRepository.delete(ID);
		if (result) {
			return "Supplier delete is succesfull.";
		}

		return Cli.ERROR + "Supplier with the ID " + ID + " could not be found!";
	}

	String fetch(int index) {
		Supplier supplier = supplierRepository.fetch(index);
		if (supplier != null) {
			System.out.println(supplier);
			return "Supplier fetch is succesfull.";
		}

		return Cli.ERROR + "Supplier with the index number " + index + " could not be found!";
	}

	String add() {
		Utils.line();
		System.out.println("Enter supplier name:");
		String name = scanner.nextLine();
		Utils.line();
		Supplier supplier = new Supplier(name);

		if (supplierRepository.add(supplier))
			return "A new supplier added.";
		else
			return "Data coul not be added.";

	}

	static void printSupplierList(List<Supplier> suppliers) {
		System.out.printf("%-10s %-50s\n", "ID", "Name");
		Utils.line();
		for (Supplier supplier : suppliers) {
			System.out.printf("%-10d %-50s\n", supplier.getID(), supplier.getName());
		}
		Utils.line();
	}

	String list() {
		List<Supplier> suppliers = supplierRepository.list();
		if (suppliers == null) {
			return Cli.DBERROR;
		}
		printSupplierList(suppliers);
		return suppliers.size() + " suppliers have been successfull listed.";

	}
}
