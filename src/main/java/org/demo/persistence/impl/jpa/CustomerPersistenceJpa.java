package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.CustomerRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.CustomerJpaRepository;
import org.demo.persistence.CustomerPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.CustomerJpaMapper;
import org.demo.persistence.impl.jpa.record.CustomerJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Customer persistence service - JPA implementation
 * 
 */
@Named("CustomerPersistence")
@Component
@Transactional
public class CustomerPersistenceJpa implements CustomerPersistence {

	@Autowired
	private CustomerJpaRepository repository;
	private final CustomerJpaMapper mapper = new CustomerJpaMapper();

	@Override
	public CustomerRecord findById(String code) {
		CustomerJpaRecord entity = repository.findOne(code);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<CustomerRecord> findAll() {
		List<CustomerJpaRecord> entities = (List<CustomerJpaRecord>) repository.findAll();
		List<CustomerRecord> records = new java.util.LinkedList<CustomerRecord>();
		for (CustomerJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public CustomerRecord create(CustomerRecord record) {
		CustomerJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new CustomerJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		CustomerJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(CustomerRecord record) {
		CustomerJpaRecord entitySaved = null;
		CustomerJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public CustomerRecord save(CustomerRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(String code) {
		if (exists(code)) {
			repository.delete(code);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(CustomerRecord record) {
		if (exists(record)) {
			repository.delete(record.getCode());
			return true;
		}
		return false;
	}

	@Override
	public long countAll() {
		return repository.count();
	}

	@Override
	public boolean exists(String code) {
		return repository.exists(code);
	}

	@Override
	public boolean exists(CustomerRecord record) {
		if (record != null) {
			return repository.exists(record.getCode());
		}
		return false;
	}

	public CustomerJpaRepository getCustomerJpaRepository() {
		return repository;
	}

	public void setCustomerJpaRepository(CustomerJpaRepository repository) {
		this.repository = repository;
	}
}
