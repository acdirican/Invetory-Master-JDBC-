package com.acdirican.inventorymaster.model;

import java.util.List;

import com.acdirican.inventorymaster.repository.Repository;

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
	
	public List<Product> getProducts() {
		//lazy load of supplier's products
		if (products == null) {
			products = Repository.getSupplierRepository().getProducts(this);
		}
		return products;
	}
	
}
