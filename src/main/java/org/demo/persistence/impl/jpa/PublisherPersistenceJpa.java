package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.PublisherRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.PublisherJpaRepository;
import org.demo.persistence.PublisherPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.PublisherJpaMapper;
import org.demo.persistence.impl.jpa.record.PublisherJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Publisher persistence service - JPA implementation
 * 
 */
@Named("PublisherPersistence")
@Component
@Transactional
public class PublisherPersistenceJpa implements PublisherPersistence {

	@Autowired
	private PublisherJpaRepository repository;
	private final PublisherJpaMapper mapper = new PublisherJpaMapper();

	@Override
	public PublisherRecord findById(Integer code) {
		PublisherJpaRecord entity = repository.findOne(code);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<PublisherRecord> findAll() {
		List<PublisherJpaRecord> entities = (List<PublisherJpaRecord>) repository.findAll();
		List<PublisherRecord> records = new java.util.LinkedList<PublisherRecord>();
		for (PublisherJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public PublisherRecord create(PublisherRecord record) {
		PublisherJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new PublisherJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		PublisherJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(PublisherRecord record) {
		PublisherJpaRecord entitySaved = null;
		PublisherJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public PublisherRecord save(PublisherRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(Integer code) {
		if (exists(code)) {
			repository.delete(code);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(PublisherRecord record) {
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
	public boolean exists(Integer code) {
		return repository.exists(code);
	}

	@Override
	public boolean exists(PublisherRecord record) {
		if (record != null) {
			return repository.exists(record.getCode());
		}
		return false;
	}

	public PublisherJpaRepository getPublisherJpaRepository() {
		return repository;
	}

	public void setPublisherJpaRepository(PublisherJpaRepository repository) {
		this.repository = repository;
	}
}
