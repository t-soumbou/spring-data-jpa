package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.BadgeRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.BadgeJpaRepository;
import org.demo.persistence.BadgePersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.BadgeJpaMapper;
import org.demo.persistence.impl.jpa.record.BadgeJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Badge persistence service - JPA implementation
 * 
 */
@Named("BadgePersistence")
@Component
@Transactional
public class BadgePersistenceJpa implements BadgePersistence {

	@Autowired
	private BadgeJpaRepository repository;
	private final BadgeJpaMapper mapper = new BadgeJpaMapper();

	@Override
	public BadgeRecord findById(Integer id) {
		BadgeJpaRecord entity = repository.findOne(id);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<BadgeRecord> findAll() {
		List<BadgeJpaRecord> entities = (List<BadgeJpaRecord>) repository.findAll();
		List<BadgeRecord> records = new java.util.LinkedList<BadgeRecord>();
		for (BadgeJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public BadgeRecord create(BadgeRecord record) {
		BadgeJpaRecord entity = repository.findOne(record.getBadgeNumber());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new BadgeJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		BadgeJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(BadgeRecord record) {
		BadgeJpaRecord entitySaved = null;
		BadgeJpaRecord entity = repository.findOne(record.getBadgeNumber());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public BadgeRecord save(BadgeRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(Integer id) {
		if (exists(id)) {
			repository.delete(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(BadgeRecord record) {
		if (exists(record)) {
			repository.delete(record.getBadgeNumber());
			return true;
		}
		return false;
	}

	@Override
	public long countAll() {
		return repository.count();
	}

	@Override
	public boolean exists(Integer id) {
		return repository.exists(id);
	}

	@Override
	public boolean exists(BadgeRecord record) {
		if (record != null) {
			return repository.exists(record.getBadgeNumber());
		}
		return false;
	}

	public BadgeJpaRepository getBadgeJpaRepository() {
		return repository;
	}

	public void setBadgeJpaRepository(BadgeJpaRepository repository) {
		this.repository = repository;
	}
}
