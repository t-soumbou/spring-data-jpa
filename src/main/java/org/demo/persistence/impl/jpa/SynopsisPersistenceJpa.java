package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.SynopsisRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.SynopsisJpaRepository;
import org.demo.persistence.SynopsisPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.SynopsisJpaMapper;
import org.demo.persistence.impl.jpa.record.SynopsisJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Synopsis persistence service - JPA implementation
 * 
 */
@Named("SynopsisPersistence")
@Component
@Transactional
public class SynopsisPersistenceJpa implements SynopsisPersistence {

	@Autowired
	private SynopsisJpaRepository repository;
	private final SynopsisJpaMapper mapper = new SynopsisJpaMapper();

	@Override
	public SynopsisRecord findById(Integer bookId) {
		SynopsisJpaRecord entity = repository.findOne(bookId);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<SynopsisRecord> findAll() {
		List<SynopsisJpaRecord> entities = (List<SynopsisJpaRecord>) repository.findAll();
		List<SynopsisRecord> records = new java.util.LinkedList<SynopsisRecord>();
		for (SynopsisJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public SynopsisRecord create(SynopsisRecord record) {
		SynopsisJpaRecord entity = repository.findOne(record.getBookId());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new SynopsisJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		SynopsisJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(SynopsisRecord record) {
		SynopsisJpaRecord entitySaved = null;
		SynopsisJpaRecord entity = repository.findOne(record.getBookId());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public SynopsisRecord save(SynopsisRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(Integer bookId) {
		if (exists(bookId)) {
			repository.delete(bookId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(SynopsisRecord record) {
		if (exists(record)) {
			repository.delete(record.getBookId());
			return true;
		}
		return false;
	}

	@Override
	public long countAll() {
		return repository.count();
	}

	@Override
	public boolean exists(Integer bookId) {
		return repository.exists(bookId);
	}

	@Override
	public boolean exists(SynopsisRecord record) {
		if (record != null) {
			return repository.exists(record.getBookId());
		}
		return false;
	}

	public SynopsisJpaRepository getSynopsisJpaRepository() {
		return repository;
	}

	public void setSynopsisJpaRepository(SynopsisJpaRepository repository) {
		this.repository = repository;
	}
}
