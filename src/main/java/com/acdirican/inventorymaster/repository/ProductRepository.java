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
public class ProductRepository extends AbstracyRepository{
	
	public ProductRepository(Repository repository) {
		super(repository);
	}
	
	public List<Product> list()  {
		Statement statement;
		try {
			statement = connection.createStatement();
			String SQL = "select p.*, s.Name from products p, supplier s where p.SupplierID = s.ID";
			ResultSet rs = statement.executeQuery(SQL);
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, new Supplier(rs.getInt(4), rs.getString(5))));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Product> listMoreThan(double quantity) {
		List<Product> list = new ArrayList<>();
		
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("select * from products where Quantity >= ?");
			statement.setDouble(1, quantity);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, repository.findSupplier(rs.getInt(4))));
			}

			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Product> listLessThan(double quantity) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("select * from products where Quantity <= ?");
			statement.setDouble(1, quantity);

			ResultSet rs = statement.executeQuery();

			List<Product> list = new ArrayList<>();

			while (rs.next()) {
				list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, repository.findSupplier(rs.getInt(4))));
			}

			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean add(Product product) {
		String SQL = "Insert Into products Values(NULL, " 
						+ "'" + product.getName() + "', " 
						+ product.getQuantity() + ", "
						+ product.getSupplier().getID() + ")";
	
		Statement statement;
		try {
			statement = connection.createStatement();
			int numOfAffectedRows = statement.executeUpdate(SQL);
			return numOfAffectedRows > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Product fetch(int i){
		Statement statement;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = statement.executeQuery("select * from products");
			if (rs.absolute(i)) {
				return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, repository.findSupplier(rs.getInt(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(int ID) {
		String SQL = "Delete from products Where ID = " + ID;
		Statement statement;
		try {
			statement = connection.createStatement();
			int numOfAffectedRows = statement.executeUpdate(SQL);
			return numOfAffectedRows > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public Product get(int ID) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from products where ID = " + ID);

			if (rs.next()) {
				return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), 
						repository.findSupplier(rs.getInt(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	public boolean update(Product product) {
		String SQL = "Update products Set " 
				+ "name='" + product.getName() + "', " 
				+ "quantity=" + product.getQuantity() +", "
				+ "SupplierID=" + product.getSupplier().getID() + " "
				+ "Where ID = " + product.getID();
		
		// System.out.println(SQL);
		Statement statement;
		try {
			statement = connection.createStatement();
			int numOfAffectedRows = statement.executeUpdate(SQL);
			return numOfAffectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Product> find(String name) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from products where Name Like '%" + name + "%'");

		List<Product> list = new ArrayList<>();

		while (rs.next()) {
			list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), 
					repository.findSupplier(rs.getInt(4))));
		}

		return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	/*
	 * List products whose quantities are equals to the given value using a stored procedure
	 */
	public List<Product> listEquals(double quantity){
		CallableStatement statement;
		try {
			statement = connection.prepareCall("{call get_paroducts_byquantity(?)}");
			statement.setDouble(1, quantity);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, repository.findSupplier(rs.getInt(4))));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/*
	 * List delpeted products using a stored procedure
	 */
	public List<Product> listDepleteds() {
		CallableStatement statement;
		try {
			statement = connection.prepareCall("{call get_delpeted_products()}");
			statement.execute();
			ResultSet rs = statement.getResultSet();
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)
						, repository.findSupplier(rs.getInt(4))));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public int deleteAll(List<Integer> id_list){
		if (id_list.size() == 0) {
			return -1;
		}
		
		Statement statement;
		try {
			statement = connection.createStatement();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;	
	}

}
