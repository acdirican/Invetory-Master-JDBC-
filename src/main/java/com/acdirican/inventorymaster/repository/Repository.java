package com.acdirican.inventorymaster.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.acdirican.inventorymaster.model.Product;
import com.acdirican.inventorymaster.model.Supplier;

/**
 * Repository class for the product entity.
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */
public class Repository {

	static final String ERROR = "Database error!";
	
	private Connection connection;
	private ProductRepository productRepository;
	private SupplierRepository supplierRepository;
	
	public boolean connect() throws SQLException {
		String myDriver = "com.mysql.cj.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost:3306/inventorymaster_db";
	    String myUser = "root";
	    String myPassword="";
	    try {
			Class.forName(myDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	      
		this.connection = DriverManager.getConnection(myUrl, myUser, myPassword);
		this.productRepository = new ProductRepository(this);
		this.supplierRepository =  new SupplierRepository(this);
		
		return true;
	}

	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				System.err.println("Could not be closed the connection to the mysql database!");
			}
		}
	}
	
	public String metaData(String tableName) {
		StringJoiner joiner =  new StringJoiner("\n");
		joiner.add(tableName + " table\n");
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select * from " + tableName);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			joiner.add("Total columns: " + rsmd.getColumnCount());

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				joiner.add(i + ": " + rsmd.getColumnName(i) + "\t" + rsmd.getColumnTypeName(i) + "(" + rsmd.getColumnType(i)
						+ ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			joiner.add(Repository.ERROR);
		}
		
		
		return joiner.toString();
	}
	
	public String metaData()  {

		StringJoiner joiner = new StringJoiner("\n");

		joiner.add("Database:");
		DatabaseMetaData dbmd;
		try {
			
			dbmd = connection.getMetaData();
			
			joiner.add("Driver Name: " + dbmd.getDriverName());
			joiner.add("Driver Version: " + dbmd.getDriverVersion());
			joiner.add("UserName: " + dbmd.getUserName());
			joiner.add("Database Product Name: " + dbmd.getDatabaseProductName());
			joiner.add("Database Product Version: " + dbmd.getDatabaseProductVersion());

			joiner.add("");
			joiner.add(metaData("product"));
			joiner.add(metaData("supplier"));
			
		} catch (SQLException e) {
			return ERROR;
		}
		return joiner.toString();
	}
	
	public ProductRepository getProductRepository() {
		return productRepository;
	}
	
	public SupplierRepository getSupplierRepository() {
		return supplierRepository;
	}

	public Supplier findSupplier(int ID) {
		return supplierRepository.get(ID);
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
	
}
