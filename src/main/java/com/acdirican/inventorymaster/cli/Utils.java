package com.acdirican.inventorymaster.cli;

import java.util.Scanner;

/**
 * Utiliy class for CLI operations
 * 
 */
public final class Utils {
	
	static Scanner scanner;
	
	static {
		scanner =  new Scanner(System.in);
	}
	
	private Utils() {}
	
	static boolean confirm(String msg) {
		System.out.println(msg);
		String answer = scanner.nextLine().trim().toLowerCase();
		return answer.equals("yes") || answer.equals("y");
	}

	
	static void line() {
		System.out.println("------------------------------------------------------------------------");	
	}

}
