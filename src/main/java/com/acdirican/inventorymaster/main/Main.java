package com.acdirican.inventorymaster.main;

import java.sql.SQLException;

import com.acdirican.inventorymaster.cli.Cli;
import com.acdirican.inventorymaster.repository.Repository;

/**
 * Starter class of the project.
 *  
 * @author Ahmet Cengizhan Dirican
 *
 */
public class Main {
	public static void main(String[] args) {
		Repository db =  new Repository();
		Cli cli =  new Cli(db);		
	}
}
