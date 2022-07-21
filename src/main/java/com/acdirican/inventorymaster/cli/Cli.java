package com.acdirican.inventorymaster.cli;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.acdirican.inventorymaster.db.DB;
import com.acdirican.inventorymaster.entities.Product;

/**
 * Command line user interface of the software
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */
public class Cli {
	
	private static final String HELP = "help";
	private static final String EXIT = "exit";
	private static final String LIST = "list";
	private static final String LISTMORETHEN = "list_morethan";
	private static final String LISTLESSTHEN = "list_lessthan";
	private static final String LISTEQUALS = "list_equals";
	private static final String LISTDEPLETEDS = "list_depleteds";
	private static final String FIND = "find";
	private static final String UPDATE = "update";
	private static final String FETCH = "fetch";
	private static final String ADD = "add";

	private static final String DELETE = "delete";
	private static final String DELETE_ALL = "delete_all"
			;
	private static final String META = "meta";
	
	private static final String ERROR = "ERROR: ";
	private static final String DBERROR = ERROR + " DB connection or query error!";
	
	
	
	
	private final Scanner scanner;
	private DB db;

	public Cli(DB db) {
		try {
			db.connect();
			System.out.println("DB conneciton is successfull!");
		} catch (SQLException e) {
			System.err.println("Could not be connected to the mysql database!");
			e.printStackTrace();
		}
		this.db = db;
		this.scanner =  new Scanner(System.in);
		
		init();
	}

	private void init() {
		
		String cmd = null;
		String output ="";
		do {
			System.out.print(">>");
			cmd = scanner.nextLine();
			System.out.println();
			output = execute(cmd.trim().toLowerCase());
			System.out.println(output);
			System.out.println();
		} while(!cmd.equals(EXIT));
		db.close();
	}

	private String execute(String cmd) {
		String[] parameters = cmd.split("\\s");
		switch (parameters[0]) {
			case LIST: {
				return list();
			}
			
			case FIND: {
				return find(parameters);
			}
			
			case LISTMORETHEN: {
				return list_morethan(parameters);
			}
			
			case LISTLESSTHEN: {
				return list_lessthan(parameters);
			}
			
			case LISTEQUALS: {
				return list_equals(parameters);
			}
			
			case LISTDEPLETEDS: {
				return list_depleteds();
			}
			
			case ADD: {
				return add();
			}
			
			case FETCH: {
				return fetch(parameters);
			}
			
			case DELETE: {
				return delete(parameters);
			}
			
			case DELETE_ALL: {
				return delete_all(parameters);
			}
			
			case UPDATE: {
				return update(parameters);
			}
			
			case META: {
				return metadata();
			}
			
			case HELP: {
				return help();
			}
			case EXIT:
				return "bye bye";
				
			default:
				return ERROR  + "Unknown command!";
		}
	}

	private String delete_all(String[] parameters) {
		if (!confirm("Are you sure to delete all the product with the given IDs? [y/n]")) {
			return "Delete cancelled";
		}
		
		List<Integer> id_list = new ArrayList<>();
		for (int i = 1; i < parameters.length; i++) {
			int ID = Integer.parseInt(parameters[i]);
			id_list.add(ID);
		}
		
		if (id_list.size()==0) {
			return "No product ID for deletion!";
		}
		
		try {
			
			int result = db.deleteAll(id_list);
			if (result == id_list.size()) {
				return "All products deleted succesfull.";
			}
			else if (result>0){
				return "Products partially deleted.";
			}
			else {
				return "No product deleted.";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
		

	}

	private String help() {
		return "List of Commands:\n" 
				+ "help\n"
				+ "exit \n"
				+ "list\n"
				+ "list_morethan <QUANTITY> \n"
				+ "list_lessthan <QUANTITY>\n"
				+ "list_equals <QUANTITY>\n"
				+ "list_depleteds \n"
				+ "find <NAME>\n"
				+ "update <ID>\n"
				+ "fetch <INDEX>\n"
				+ "add\n"
				+ "delete <ID>\n"
				+ "delete_all <ID>+\n"
				+ "meta";
	}

	/*
	 
	 CREATE PROCEDURE get_delpeted_products ()  
		BEGIN  
		    SELECT * FROM products WHERE quantity = 0;
		END;

	 */
	private String list_depleteds() {
		try {
			List<Product> products = db.listDepleteds();
			printList(products);
			return "Listing is successfull.";
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}
	
	private String list_equals(String[] parameters) {
		double quantity = Double.parseDouble(parameters[1]);
		try {
			List<Product> products = db.listEquals(quantity);
			printList(products);
			return "Listing is successfull.";
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}

	private String metadata() {
		try {
			return db.metaData();
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}

	private String find(String[] parameters) {
		try {
			List<Product> products = db.find(parameters[1]);
			printList(products);
			return "Listing is successfull.";
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}

	private String list_morethan(String[] parameters) {
		double quantity = Double.parseDouble(parameters[1]);
		try {
			List<Product> products = db.listMoreThan(quantity);
			printList(products);
			return "Listing is successfull.";
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}
	
	private String list_lessthan(String[] parameters) {
		double quantity = Double.parseDouble(parameters[1]);
		try {
			List<Product> products = db.listLessThan(quantity);
			printList(products);
			return "Listing is successfull.";
			
		} catch (SQLException e) {
			e.printStackTrace();
			return DBERROR;
		}
	}

	private static void printList(List<Product> products) {
		System.out.printf("%-10s %-50s %10s","ID","Name","Quantity\n");
		line();
		for (Product product : products) {
			System.out.printf("%-10d %-50s %10f\n", product.getID(), product.getName(), product.getQuantity());
		}
		line();		
	}

	private String update(String[] parameters) {
		int ID = parameters.length == 1
				? 0
				: Integer.parseInt(parameters[1]);
		Product product = null;;
		try {
			product = db.get(ID);
		} catch (SQLException e) {
			return DBERROR;
		}
		
		if (product !=  null) {
			line();
			System.out.println(product);
			line();
			System.out.println("Live empty if you don't want to update the field!");
			System.out.println("Enter product name:");
			String name = scanner.nextLine().trim();
			System.out.println("Enter product quantity:");
			String quantityStr = scanner.nextLine().trim();
			line();
			try {
				if (!name.equals("")) {
					product.setName(name);
				}
				if (!quantityStr.equals("")) {
					product.setQuantity(Double.parseDouble(quantityStr));
				}
				db.update(product);
			} catch (SQLException e) {
				e.printStackTrace();
				return DBERROR;
			}
			
			return "Product succesfully updated!";
		}
		
		return ERROR + "Product with the ID " + ID + " could not be found!";
	}

	private String delete(String[] parameters) {
		if (!confirm("Are you sure to delete? [y/n]")) {
			return "Delete cancelled";
		}
		int ID = parameters.length == 1
				? 0
				: Integer.parseInt(parameters[1]);
		try {
			
			boolean result = db.delete(ID);
			if (result) {
				return "Product delete is succesfull.";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ERROR + "Product with the ID " + ID + " could not be found!";
	}

	private boolean confirm(String msg) {
		System.out.println(msg);
		String answer = scanner.nextLine().trim().toLowerCase();
		return answer.equals("yes") || answer.equals("y");
	}

	private String fetch(String[] parameters) {
		int i = parameters.length == 1
				? 0
				: Integer.parseInt(parameters[1]);
		Product product;
		try {
			
			product = db.fetch(i);
			if (product!=null) {
				System.out.println(product);
				return "Product fetch is succesfull.";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ERROR + "Product with the index number " + i + " could not be found!";
	}

	private String add() {
		line();
		System.out.println("Enter product name:");
		String name = scanner.nextLine();
		System.out.println("Enter product quantity:");
		double quantity = Double.parseDouble(scanner.nextLine());
		line();
		Product product =  new Product(name, quantity);
		
		try {
			db.add(product);
			return "A new product added.";
		} catch (SQLException e) {
			return "Data coul not be added.";
		}
	}

	private String list() {
		try {
			List<Product> products = db.list();
			printList(products);
			return "Listing is successfull.";
		} catch (SQLException e) {
			return DBERROR;
		}
		
	}

	private static void line() {
		System.out.println("------------------------------------------------------------------------");	
	}
		

}
