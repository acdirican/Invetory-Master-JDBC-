package com.acdirican.inventorymaster.model;

import java.util.List;

public class Supplier {
	
	private int ID;
	private String name;
	private List<Product> products;
	
	public Supplier(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public Supplier(String name) {
		this(0, name);
	}
	public Supplier(int ID) {
		this.ID=ID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Supplier [ID=" + ID + ", name=" + name + ", products=" + products + "]";
	}
	
	
}
