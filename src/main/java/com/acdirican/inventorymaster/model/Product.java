package com.acdirican.inventorymaster.model;

/**
 * Product entity. This class represents a product in the database.
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */

public class Product {
	private int ID;
	private String name;
	private double quantity;
	private Supplier supplier;
	
	public Product(int iD, String name, double quantity, Supplier supplier) {
		super();
		ID = iD;
		this.name = name;
		this.quantity = quantity;
		this.supplier = supplier;
	}

	public Product(String name, double quantity) {
		this(0, name, quantity, null);
	}

	public Product(int ID, String name, double quantity) {
		this(ID, name, quantity, null);
	}


	public Product(String name, double quantity, Supplier supplier) {
		this(0, name, quantity, supplier);
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

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public String toString() {
		return "Product [ID=" + ID + ", name=" + name + ", quantity=" + quantity + ", supplier:" + supplier.getName() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (ID != other.ID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		return true;
	}

	public Supplier getSupplier() {
		return supplier;
	}
	
	
	
}
