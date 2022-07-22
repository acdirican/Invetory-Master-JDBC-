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

import com.acdirican.inventorymaster.model.Supplier;
import com.acdirican.inventorymaster.model.Supplier;

/**
 * Repository class for the supplier entity.
 * 
 * @author Ahmet Cengizhan Dirican
 *
 */
public class SupplierRepository extends AbstracyRepository {
	
	public SupplierRepository( Repository repository) {
		super(repository);
	}
	
	public List<Supplier> list() {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from supplier");
			List<Supplier> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Supplier(rs.getInt(1), rs.getString(2)));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}

	}

	public boolean add(Supplier supplier) {
		String SQL = "Insert Into supplier Values(NULL, " + "'" + supplier.getName() + "')";
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

	public Supplier fetch(int index){
		Statement statement;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE
					, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery("select * from supplier");
			if (rs.absolute(index)) {
				return new Supplier(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}	
		return null;
		
	}

	public boolean delete(int ID) {
		String SQL = "Delete from supplier Where ID = " + ID;
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

	public Supplier get(int ID) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from supplier where ID = " + ID);

			if (rs.next()) {
				return new Supplier(rs.getInt(1), rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	public boolean update(Supplier supplier) {
		String SQL = "Update supplier Set " + "name='" + supplier.getName() + "' Where ID = " + supplier.getID();
		// System.out.println(SQL);
		Statement statement;
		try {
			statement = connection.createStatement();
			int numOfAffectedRows = statement.executeUpdate(SQL);
			return numOfAffectedRows > 0;
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
	}

	public List<Supplier> find(String name) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from supplier where Name Like '%" + name + "%'");

		List<Supplier> list = new ArrayList<>();

		while (rs.next()) {
			list.add(new Supplier(rs.getInt(1), rs.getString(2)));
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
				String SQL = "delete from supplier where ID = " + ID;
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
