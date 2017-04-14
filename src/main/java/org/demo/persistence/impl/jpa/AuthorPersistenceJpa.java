package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.AuthorRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.AuthorJpaRepository;
import org.demo.persistence.AuthorPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.AuthorJpaMapper;
import org.demo.persistence.impl.jpa.record.AuthorJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author persistence service - JPA implementation
 * 
 */
@Named("AuthorPersistence")
@Component
@Transactional
public class AuthorPersistenceJpa implements AuthorPersistence {

	@Autowired
	private AuthorJpaRepository repository;
	private final AuthorJpaMapper mapper = new AuthorJpaMapper();

	@Override
	public AuthorRecord findById(Integer id) {
		AuthorJpaRecord entity = repository.findOne(id);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<AuthorRecord> findAll() {
		List<AuthorJpaRecord> entities = (List<AuthorJpaRecord>) repository.findAll();
		List<AuthorRecord> records = new java.util.LinkedList<AuthorRecord>();
		for (AuthorJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public AuthorRecord create(AuthorRecord record) {
		AuthorJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new AuthorJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		AuthorJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(AuthorRecord record) {
		AuthorJpaRecord entitySaved = null;
		AuthorJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public AuthorRecord save(AuthorRecord record) {
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
	public boolean delete(AuthorRecord record) {
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
	public boolean exists(Integer id) {
		return repository.exists(id);
	}

	@Override
	public boolean exists(AuthorRecord record) {
		if (record != null) {
			return repository.exists(record.getId());
		}
		return false;
	}

	public AuthorJpaRepository getAuthorJpaRepository() {
		return repository;
	}

	public void setAuthorJpaRepository(AuthorJpaRepository repository) {
		this.repository = repository;
	}
}
