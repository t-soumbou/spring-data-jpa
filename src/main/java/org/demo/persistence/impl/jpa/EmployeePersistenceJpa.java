package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.EmployeeRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.EmployeeJpaRepository;
import org.demo.persistence.EmployeePersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.EmployeeJpaMapper;
import org.demo.persistence.impl.jpa.record.EmployeeJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Employee persistence service - JPA implementation
 * 
 */
@Named("EmployeePersistence")
@Component
@Transactional
public class EmployeePersistenceJpa implements EmployeePersistence {

	@Autowired
	private EmployeeJpaRepository repository;
	private final EmployeeJpaMapper mapper = new EmployeeJpaMapper();

	@Override
	public EmployeeRecord findById(String code) {
		EmployeeJpaRecord entity = repository.findOne(code);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<EmployeeRecord> findAll() {
		List<EmployeeJpaRecord> entities = (List<EmployeeJpaRecord>) repository.findAll();
		List<EmployeeRecord> records = new java.util.LinkedList<EmployeeRecord>();
		for (EmployeeJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public EmployeeRecord create(EmployeeRecord record) {
		EmployeeJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new EmployeeJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		EmployeeJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(EmployeeRecord record) {
		EmployeeJpaRecord entitySaved = null;
		EmployeeJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public EmployeeRecord save(EmployeeRecord record) {
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
	public boolean delete(EmployeeRecord record) {
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
	public boolean exists(EmployeeRecord record) {
		if (record != null) {
			return repository.exists(record.getCode());
		}
		return false;
	}

	public EmployeeJpaRepository getEmployeeJpaRepository() {
		return repository;
	}

	public void setEmployeeJpaRepository(EmployeeJpaRepository repository) {
		this.repository = repository;
	}
}
