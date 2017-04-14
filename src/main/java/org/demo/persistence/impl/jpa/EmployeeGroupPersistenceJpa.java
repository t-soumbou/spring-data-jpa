package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.EmployeeGroupRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.EmployeeGroupJpaRepository;
import org.demo.persistence.EmployeeGroupPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.EmployeeGroupJpaMapper;
import org.demo.persistence.impl.jpa.record.EmployeeGroupJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.demo.persistence.impl.jpa.record.EmployeeGroupJpaRecordKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmployeeGroup persistence service - JPA implementation
 * 
 */
@Named("EmployeeGroupPersistence")
@Component
@Transactional
public class EmployeeGroupPersistenceJpa implements EmployeeGroupPersistence {

	@Autowired
	private EmployeeGroupJpaRepository repository;
	private final EmployeeGroupJpaMapper mapper = new EmployeeGroupJpaMapper();
	
	
	private EmployeeGroupJpaRecordKey buildKey(EmployeeGroupRecord record){
		return new  EmployeeGroupJpaRecordKey( record.getEmployeeCode(), record.getGroupId() );
	}
	
	private EmployeeGroupJpaRecordKey buildKey(String employeeCode, Short groupId ){
		return new EmployeeGroupJpaRecordKey( employeeCode, groupId );
	}

	@Override
	public EmployeeGroupRecord findById( String employeeCode, Short groupId ) {
		EmployeeGroupJpaRecordKey key = buildKey( employeeCode, groupId );
		EmployeeGroupJpaRecord entity = repository.findOne(key);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<EmployeeGroupRecord> findAll() {
		List<EmployeeGroupJpaRecord> entities = (List<EmployeeGroupJpaRecord>) repository.findAll();
		List<EmployeeGroupRecord> records = new java.util.LinkedList<EmployeeGroupRecord>();
		for (EmployeeGroupJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public EmployeeGroupRecord create(EmployeeGroupRecord record) {
		EmployeeGroupJpaRecordKey key = buildKey(record);
		EmployeeGroupJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new EmployeeGroupJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		EmployeeGroupJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(EmployeeGroupRecord record) {
		EmployeeGroupJpaRecord entitySaved = null;
		EmployeeGroupJpaRecordKey key = buildKey(record);
		EmployeeGroupJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public EmployeeGroupRecord save(EmployeeGroupRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(String employeeCode, Short groupId ) {
		EmployeeGroupJpaRecordKey key = buildKey( employeeCode, groupId );
		if (exists(employeeCode, groupId)) {
			repository.delete(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(EmployeeGroupRecord record) {
		EmployeeGroupJpaRecordKey key = buildKey(record);
		if (exists(record)) {
			repository.delete(key);
			return true;
		}
		return false;
	}

	@Override
	public long countAll() {
		return repository.count();
	}

	@Override
	public boolean exists(String employeeCode, Short groupId) {
		EmployeeGroupJpaRecordKey key = buildKey( employeeCode, groupId );
		return repository.exists(key);
	}

	@Override
	public boolean exists(EmployeeGroupRecord record) {
		EmployeeGroupJpaRecordKey key = buildKey(record);
		if (record != null) {
			return repository.exists(key);
		}
		return false;
	}

	public EmployeeGroupJpaRepository getEmployeeGroupJpaRepository() {
		return repository;
	}

	public void setEmployeeGroupJpaRepository(EmployeeGroupJpaRepository repository) {
		this.repository = repository;
	}
}
