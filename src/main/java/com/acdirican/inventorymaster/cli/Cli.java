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

	private static final String HELP = "help";
	private static final String EXIT = "exit";
	
	private static final String GET = "get";
	private static final String LIST = "list";
	
		private static final String LISTMORETHEN = "morethan";
		private static final String LISTLESSTHEN = "lessthan";
		private static final String LISTEQUALS = "equals";
		private static final String LISTDEPLETEDS = "depleted";
	
		private static final String PRODUCT = "product";
		private static final String SUPPLIER = "supplier";
	
	private static final String FIND = "find";
	private static final String UPDATE = "update";
	private static final String INDEXOF = "indexof";
	private static final String ADD = "add";

	private static final String DELETE = "delete";
	private static final String DELETE_ALL = "delete_all";

	private static final String META = "meta";

	static final String ERROR = "ERROR: ";
	static final String DBERROR = ERROR + " DB connection or query error!";
	static final String ERROR_UNKNOWN_PARAMATER = ERROR + " Unknown paramter!";

	

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
		this.productRepository = Repository.getProductRepository();
		this.supplierRepository = Repository.getSupplierRepository();
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
		} while (!cmd.equals(EXIT));
		repository.close();
	}

	private String execute(String cmd) {
		String[] parameters = cmd.split("\\s");
		switch (parameters[0]) {
		case LIST: {
			return list(parameters);
		}

		case FIND: {
			return find(parameters);
		}

		case ADD: {
			return add(parameters);
		}
		
		case GET: {
			return get(parameters);
		}
		
		case INDEXOF: {
			return indexOf(parameters);
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
			return ERROR + "Unknown command!";
		}
	}

	private String update(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.update(ID);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return supplierCli.update(ID);
		}
		return ERROR_UNKNOWN_PARAMATER;
	}

	private String delete_all(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}

		List<Integer> id_list = new ArrayList<>();
		for (int i = 2; i < parameters.length; i++) {
			int ID = Integer.parseInt(parameters[i]);
			id_list.add(ID);
		}

		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.delete_all(id_list);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return supplierCli.delete_all(id_list);
		}
		return ERROR_UNKNOWN_PARAMATER;
	}

	private String add(String[] parameters) {
		if (parameters.length < 2) {
			return ERROR + "Missign argument!";
		}

		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.add();
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return supplierCli.add();
		}
		return ERROR_UNKNOWN_PARAMATER;

	}
	
	private String find(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}
		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.find(parameters[2]);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return supplierCli.find(parameters[2]);
		}
		return ERROR_UNKNOWN_PARAMATER;

	}
	private String indexOf(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}
		int index = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.indexOf(index);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return  supplierCli.indexOf(index);
		}
		return ERROR_UNKNOWN_PARAMATER;

	}
	
	private String get(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.get(ID);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return  supplierCli.get(ID);
		}
		return ERROR_UNKNOWN_PARAMATER;

	}

	private String delete(String[] parameters) {
		if (parameters.length < 3) {
			return ERROR + "Missign argument!";
		}
		int ID = Integer.parseInt(parameters[2]);
		if (parameters[1].equalsIgnoreCase(PRODUCT)) {
			return productCli.delete(ID);
		} else if (parameters[1].equalsIgnoreCase(SUPPLIER)) {
			return supplierCli.delete(ID);
		}
		return ERROR_UNKNOWN_PARAMATER;

	}

	private String list(String[] parameters) {
		if (parameters.length < 2) {
			return ERROR + "Missign argument!";
		}
		switch (parameters[1]) {
		case PRODUCT: {
			return productCli.list();
		}
		case SUPPLIER: {
			return supplierCli.list();
		}
		case LISTMORETHEN: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_morethan(quantity);
		}

		case LISTLESSTHEN: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_lessthan(quantity);
		}

		case LISTEQUALS: {
			double quantity = Double.parseDouble(parameters[2]);
			return productCli.list_equals(quantity);
		}

		case LISTDEPLETEDS: {
			return productCli.list_depleteds();
		}
		default:
			return ERROR + " Invalid list parameter";
		}
	}

	private String help() {
		return "List of Commands:\n" 
				
				+ "help\n" 
				+ "exit \n" 
				
				+ "list [product/supplier]\n" 
				
				+ "list morethan [QUANTITY] \n"
				+ "list lessthan [QUANTITY]\n" 
				+ "list equals [QUANTITY]\n" 
				+ "list depleted\n" + "find <NAME>\n"
				
				+ "indexof [product/supplier] [INDEX]\n" 
				+ "get [product/supplier] [ID]\n" 
				+ "update [product/supplier] [ID]\n" 
								
				+ "add [product/supplier]\n" 
				+ "delete [product/supplier] [ID]\n" 
				+ "delete_all [product/supplier]  [ID]+\n" 
				
				+ "meta";
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
		return supplierIDStr.equals("") ? null : getRepository().findSupplier(Integer.parseInt(supplierIDStr));
	}
}
