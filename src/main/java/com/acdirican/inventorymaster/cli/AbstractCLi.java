package com.acdirican.inventorymaster.cli;

import java.util.Scanner;

public class AbstractCLi {
	protected Cli cli;
	protected Scanner scanner;
	
	public AbstractCLi(Cli cli) {
		super();
		this.cli = cli;
		this.scanner = Utils.scanner;
	}
	
	public Cli getCli() {
		return cli;
	}
	
}
