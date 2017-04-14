package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.BookRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.BookJpaRepository;
import org.demo.persistence.BookPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.BookJpaMapper;
import org.demo.persistence.impl.jpa.record.BookJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Book persistence service - JPA implementation
 * 
 */
@Named("BookPersistence")
@Component
@Transactional
public class BookPersistenceJpa implements BookPersistence {

	@Autowired
	private BookJpaRepository repository;
	private final BookJpaMapper mapper = new BookJpaMapper();

	@Override
	public BookRecord findById(Integer id) {
		BookJpaRecord entity = repository.findOne(id);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<BookRecord> findAll() {
		List<BookJpaRecord> entities = (List<BookJpaRecord>) repository.findAll();
		List<BookRecord> records = new java.util.LinkedList<BookRecord>();
		for (BookJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public BookRecord create(BookRecord record) {
		BookJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new BookJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		BookJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(BookRecord record) {
		BookJpaRecord entitySaved = null;
		BookJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public BookRecord save(BookRecord record) {
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
	public boolean delete(BookRecord record) {
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
	public boolean exists(BookRecord record) {
		if (record != null) {
			return repository.exists(record.getId());
		}
		return false;
	}

	public BookJpaRepository getBookJpaRepository() {
		return repository;
	}

	public void setBookJpaRepository(BookJpaRepository repository) {
		this.repository = repository;
	}
}
