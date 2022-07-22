package com.acdirican.inventorymaster.cli;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.acdirican.inventorymaster.model.Product;
import com.acdirican.inventorymaster.model.Supplier;
import com.acdirican.inventorymaster.repository.ProductRepository;
import com.acdirican.inventorymaster.repository.Repository;
import com.acdirican.inventorymaster.repository.SupplierRepository;

/**
 * Command line user interface of the software
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */
public class Cli {

	private Scanner scanner = null;
	private Repository repository;
	private ProductRepository productRepository;
	private SupplierRepository supplierRepository;

	private ProductCli productCli;
	private SupplierCli supplierCli;

	public Cli(Repository repository) {
		try {
			repository.connect();
			System.out.println("DB conneciton is successfull!");
		} catch (SQLException e) {
			System.err.println("Could not be connected to the mysql database!");
			e.printStackTrace();
		}
		this.repository = repository;
		this.productRepository = repository.getProductRepository();
		this.supplierRepository = repository.getSupplierRepository();
		this.productCli = new ProductCli(this, productRepository);
		this.supplierCli = new SupplierCli(this, supplierRepository);

		this.scanner = Utils.scanner;
		init();
	}

	private void init() {

		String cmd = null;
		String output = "";
		do {
			System.out.print(">>");
			cmd = scanner.nextLine();
			System.out.println();
			output = execute(cmd.trim().toLowerCase());
			System.out.println(output);
			System.out.println();
		} while (!cmd.equals(Command.EXIT));
		repository.close();
	}

	private String execute(String cmd) {
		String[] parameters = cmd.split("\\s");
		switch (parameters[0]) {
		case Command.LIST: {
			return list(parameters);
		}

		case Command.FIND: {
			return find(parameters);
		}

		case Command.ADD: {
			return add(parameters);
		}
		
		case Command.GETWITHID: {
			return getWithID(parameters);
		}
		
		case Command.GETWITHINDEX: {
			return getWithIndex(parameters);
		}

		case Command.DELETE: {
			return delete(parameters);
		}

		case Command.DELETE_ALL: {
			return delete_all(parameters);
		}

		case Command.UPDATE: {

			return update(parameters);
		}

		case Command.META: {
			return metadata();
		}

		case Command.HELP: {
			return Command.help();
		}
		case Command.EXIT:
			return "bye bye";

		default:
			return Command.ERROR + "Unknown command!";
		}
	}

	private String update(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.update(ID);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return supplierCli.update(ID);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;
	}

	private String delete_all(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}

		List<Integer> id_list = new ArrayList<>();
		for (int i = 2; i < parameters.length; i++) {
			int ID = Integer.parseInt(parameters[i]);
			id_list.add(ID);
		}

		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.delete_all(id_list);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return supplierCli.delete_all(id_list);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;
	}

	private String add(String[] parameters) {
		if (parameters.length < 2) {
			return Command.ERROR + "Missign argument!";
		}

		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.add();
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return supplierCli.add();
		}
		return Command.ERROR_UNKNOWN_PARAMATER;

	}
	
	private String find(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}
		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.find(parameters[2]);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return supplierCli.find(parameters[2]);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;

	}
	private String getWithIndex(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}
		int index = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.getWithIndex(index);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return  supplierCli.getWithIndex(index);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;

	}
	
	private String getWithID(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.getWithID(ID);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return  supplierCli.getWithID(ID);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;

	}

	private String delete(String[] parameters) {
		if (parameters.length < 3) {
			return Command.ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(Command.PRODUCT)) {
			return productCli.delete(ID);
		} else if (parameters[1].equalsIgnoreCase(Command.SUPPLIER)) {
			return supplierCli.delete(ID);
		}
		return Command.ERROR_UNKNOWN_PARAMATER;

	}

	private String list(String[] parameters) {
		if (parameters.length < 2) {
			return Command.ERROR + "Missign argument!";
		}
		switch (parameters[1]) {
		case Command.PRODUCT: {
			return productCli.list();
		}
		case Command.SUPPLIER: {
			return supplierCli.list();
		}
		case Command.LISTMORETHEN: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_morethan(quantity);
		}

		case Command.LISTLESSTHEN: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_lessthan(quantity);
		}

		case Command.LISTEQUALS: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_equals(quantity);
		}

		case Command.LISTDEPLETEDS: {
			return productCli.list_depleteds();
		}
		default:
			return Command.ERROR + " Invalid list parameter";
		}
	}

	private String metadata() {
		return repository.metaData();
	}

	public ProductCli getProductCli() {
		return productCli;
	}

	public SupplierCli getSupplierCli() {
		return supplierCli;
	}

	public Repository getRepository() {
		return repository;
	}

	Supplier selectSupplier() {
		System.out.println("Select supplier:");
		Utils.line();
		getSupplierCli().list();
		System.out.println("Selection:");
		String supplierIDStr = productCli.scanner.nextLine().trim();
		return supplierIDStr.equals("") 
				? null 
				: getRepository().findSupplier(Integer.parseInt(supplierIDStr)).orElse(null);
	}
}
