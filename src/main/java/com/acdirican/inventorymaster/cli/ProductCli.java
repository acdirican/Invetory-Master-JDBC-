package com.acdirican.inventorymaster.cli;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.acdirican.inventorymaster.model.Product;
import com.acdirican.inventorymaster.model.Supplier;
import com.acdirican.inventorymaster.repository.ProductRepository;

public class ProductCli extends AbstractCLi {
	

	private ProductRepository productRepository;
	

	public ProductCli(Cli cli, ProductRepository productRepository) {
		super(cli);
		this.productRepository = productRepository;
	}

	String delete_all(List<Integer> id_list) {
		if (!Utils.confirm("Are you sure to delete all the product with the given IDs? [y/n]")) {
			return "Delete cancelled";
		}

		if (id_list.size() == 0) {
			return "No product ID for deletion!";
		}

		int result = productRepository.deleteAll(id_list);
		if (result == id_list.size()) {
			return "All products deleted succesfull.";
		} else if (result > 0) {
			return "Products partially deleted.";
		} else if (result == 0) {
			return "No product deleted.";
		}

		return Cli.DBERROR;

	}

	String list_depleteds() {

		List<Product> products = productRepository.listDepleteds();

		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}

	String list_equals(double quantity) {

		List<Product> products = productRepository.listEquals(quantity);
		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}

	String find(String name) {

		List<Product> products = productRepository.find(name);
		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}

	String list_morethan(double quantity) {

		List<Product> products = productRepository.listMoreThan(quantity);
		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}

	String list_lessthan(double quantity) {

		List<Product> products = productRepository.listLessThan(quantity);
		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}

	String update(int ID) {
		Product product = null;
		;

		product = productRepository.get(ID);
		if (product == null) {
			return Cli.DBERROR;
		}

		if (product != null) {
			Utils.line();
			System.out.println(product);
			Utils.line();
			System.out.println("Live empty if you don't want to update the field!");
			
			System.out.println("Enter product name:");
			String name = scanner.nextLine().trim();
			
			System.out.println("Enter product quantity:");
			String quantityStr = scanner.nextLine().trim();
			
			Supplier supplier = cli.selectSupplier();

			Utils.line();

			if (!name.equals("")) {
				product.setName(name);
			}
			if (!quantityStr.equals("")) {
				product.setQuantity(Double.parseDouble(quantityStr));
			}
			if (supplier!=null) {
				product.setSupplier(supplier);
			}

			if (productRepository.update(product)) {
				return "Product succesfully updated!";
			}

		}

		return Cli.ERROR + "Product with the ID " + ID + " could not be found!";
	}

	String delete(int ID) {
		if (!Utils.confirm("Are you sure to delete? [y/n]")) {
			return "Delete cancelled";
		}

		boolean result = productRepository.delete(ID);
		if (result) {
			return "Product delete is succesfull.";
		}

		return Cli.ERROR + "Product with the ID " + ID + " could not be found!";
	}

	String fetch(int index) {
		Product product;

		product = productRepository.fetch(index);
		if (product != null) {
			System.out.println(product);
			return "Product fetch is succesfull.";
		}
		return Cli.ERROR + "Product with the index number " + index + " could not be found!";
	}

	String add() {
		Utils.line();
		System.out.println("Enter product name:");
		String name = scanner.nextLine();
		System.out.println("Enter product quantity:");
		double quantity = Double.parseDouble(scanner.nextLine());
		Supplier supplier = cli.selectSupplier();
		if (supplier == null) {
			return "You should select a supplier ID from the given list.";
		}
		
		Product product = new Product(name, quantity, supplier);

		if (productRepository.add(product))
			return "A new product added.";
		else
			return "Data coul not be added.";

	}

	private static void printProductList(List<Product> products) {
		System.out.printf("%-10s %-30s %-20s %-30s\n", "ID", "Name", "Quantity", "Supplier");
		Utils.line();
		for (Product product : products) {
			System.out.printf("%-10d %-30s %-20f %-30s\n", product.getID(), product.getName(), product.getQuantity(),
					product.getSupplier().getName());
		}
		Utils.line();
	}

	String list() {
		List<Product> products = productRepository.list();
		if (products == null) {
			return Cli.DBERROR;
		}
		printProductList(products);
		return products.size() + " products have been successfull listed.";

	}


}
