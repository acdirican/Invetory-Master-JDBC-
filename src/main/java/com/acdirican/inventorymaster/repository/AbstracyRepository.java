package com.acdirican.inventorymaster.repository;

import java.sql.Connection;

/**
 * Abstract base class for entity repositories
 *
 * @author Ahmet Cengizhan Dirican
 *
 */
public class AbstracyRepository {
	protected Repository repository;
	protected Connection connection;
	public AbstracyRepository(Repository repository) {
		this.repository = repository;
		this.connection = repository.getConnection();
	}
}
