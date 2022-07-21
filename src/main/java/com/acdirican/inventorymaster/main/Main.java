package com.acdirican.inventorymaster.main;

import java.sql.SQLException;

import com.acdirican.inventorymaster.cli.Cli;
import com.acdirican.inventorymaster.repository.ProductRepository;

/**
 * Starter class of the project.
 *  
 * @author Ahmet Cengizhan Dirican
 *
 */
public class Main {
	public static void main(String[] args) {
		ProductRepository db =  new ProductRepository();
		Cli cli =  new Cli(db);		
	}
}
