package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.WorkgroupRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.WorkgroupJpaRepository;
import org.demo.persistence.WorkgroupPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.WorkgroupJpaMapper;
import org.demo.persistence.impl.jpa.record.WorkgroupJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Workgroup persistence service - JPA implementation
 * 
 */
@Named("WorkgroupPersistence")
@Component
@Transactional
public class WorkgroupPersistenceJpa implements WorkgroupPersistence {

	@Autowired
	private WorkgroupJpaRepository repository;
	private final WorkgroupJpaMapper mapper = new WorkgroupJpaMapper();

	@Override
	public WorkgroupRecord findById(Short id) {
		WorkgroupJpaRecord entity = repository.findOne(id);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<WorkgroupRecord> findAll() {
		List<WorkgroupJpaRecord> entities = (List<WorkgroupJpaRecord>) repository.findAll();
		List<WorkgroupRecord> records = new java.util.LinkedList<WorkgroupRecord>();
		for (WorkgroupJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public WorkgroupRecord create(WorkgroupRecord record) {
		WorkgroupJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new WorkgroupJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		WorkgroupJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(WorkgroupRecord record) {
		WorkgroupJpaRecord entitySaved = null;
		WorkgroupJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public WorkgroupRecord save(WorkgroupRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(Short id) {
		if (exists(id)) {
			repository.delete(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(WorkgroupRecord record) {
		if (exists(record)) {
			repository.delete(record.getId());
			return true;
		}
		return false;
	}

	@Override
	public long countAll() {
		return repository.count();
	}

	@Override
	public boolean exists(Short id) {
		return repository.exists(id);
	}

	@Override
	public boolean exists(WorkgroupRecord record) {
		if (record != null) {
			return repository.exists(record.getId());
		}
		return false;
	}

	public WorkgroupJpaRepository getWorkgroupJpaRepository() {
		return repository;
	}

	public void setWorkgroupJpaRepository(WorkgroupJpaRepository repository) {
		this.repository = repository;
	}
}
