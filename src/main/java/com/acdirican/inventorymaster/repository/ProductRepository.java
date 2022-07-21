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

/**
 * Database manager of the software
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */
public class ProductRepository {

	private Connection connection;

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
		//this.connection.setAutoCommit(true);
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

	public List<Product> list() throws SQLException {
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from products");
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}
		return list;

	}

	public List<Product> listMoreThan(double quantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("select * from products where Quantity >= ?");
		statement.setDouble(1, quantity);

		ResultSet rs = statement.executeQuery();

		List<Product> list = new ArrayList<>();

		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}

		return list;

	}

	public List<Product> listLessThan(double quantity) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("select * from products where Quantity <= ?");
		statement.setDouble(1, quantity);

		ResultSet rs = statement.executeQuery();

		List<Product> list = new ArrayList<>();

		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}

		return list;

	}

	public boolean add(Product product) throws SQLException {
		String SQL = "Insert Into products Values(NULL, " + "'" + product.getName() + "', " + product.getQuantity() + ")";
		Statement statement = connection.createStatement();
		int numOfAffectedRows = statement.executeUpdate(SQL);
		return numOfAffectedRows > 0;
	}

	public Product fetch(int i) throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

		ResultSet rs = statement.executeQuery("select * from products");
		if (rs.absolute(i)) {
			return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3));
		}

		return null;
	}

	public boolean delete(int ID) throws SQLException {
		String SQL = "Delete from products Where ID = " + ID;
		Statement statement = connection.createStatement();
		int numOfAffectedRows = statement.executeUpdate(SQL);
		return numOfAffectedRows > 0;
	}

	public Product get(int ID) throws SQLException {
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from products where ID = " + ID);

		if (rs.next()) {
			return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3));
		}

		return null;
	}

	public boolean update(Product product) throws SQLException {
		String SQL = "Update products Set " + "name='" + product.getName() + "', " + "quantity=" + product.getQuantity()
				+ " " + "Where ID = " + product.getID();
		// System.out.println(SQL);
		Statement statement = connection.createStatement();
		int numOfAffectedRows = statement.executeUpdate(SQL);

		return numOfAffectedRows > 0;
	}

	public List<Product> find(String name) throws SQLException {
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from products where Name Like '%" + name + "%'");

		List<Product> list = new ArrayList<>();

		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}

		return list;
	}

	public String metaData() throws SQLException {

		StringJoiner joiner = new StringJoiner("\n");

		joiner.add("Database:");
		DatabaseMetaData dbmd = connection.getMetaData();
		joiner.add("Driver Name: " + dbmd.getDriverName());
		joiner.add("Driver Version: " + dbmd.getDriverVersion());
		joiner.add("UserName: " + dbmd.getUserName());
		joiner.add("Database Product Name: " + dbmd.getDatabaseProductName());
		joiner.add("Database Product Version: " + dbmd.getDatabaseProductVersion());

		joiner.add("");
		joiner.add("Products Table:");
		PreparedStatement ps = connection.prepareStatement("select * from products");
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		joiner.add("Total columns: " + rsmd.getColumnCount());

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			joiner.add(i + ": " + rsmd.getColumnName(i) + "\t" + rsmd.getColumnTypeName(i) + "(" + rsmd.getColumnType(i)
					+ ")");
		}

		return joiner.toString();
	}

	/*
	 * List products whose quantities are equals to the given value using a stored procedure
	 */
	public List<Product> listEquals(double quantity) throws SQLException {
		CallableStatement statement = connection.prepareCall("{call get_paroducts_byquantity(?)}");
		statement.setDouble(1, quantity);
		statement.execute();
		ResultSet rs = statement.getResultSet();
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}
		return list;
	}

	/*
	 * List delpeted products using a stored procedure
	 */
	public List<Product> listDepleteds() throws SQLException {
		CallableStatement statement = connection.prepareCall("{call get_delpeted_products()}");
		statement.execute();
		ResultSet rs = statement.getResultSet();
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
		}
		return list;
	}

	public int deleteAll(List<Integer> id_list) throws SQLException {
		if (id_list.size() == 0) {
			return -1;
		}
		
		Statement statement = connection.createStatement();
		
		for (Integer ID : id_list) {
			String SQL = "delete from products where ID = " + ID;
			statement.addBatch(SQL);
		}
		int[] numberOfAffectedRows = statement.executeBatch();
		
		int sum = 0;
		for (int r : numberOfAffectedRows) {
			sum += r;
		}
		return sum;
	}
}
