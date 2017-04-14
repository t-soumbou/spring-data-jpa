package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.BookOrderRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.BookOrderJpaRepository;
import org.demo.persistence.BookOrderPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.BookOrderJpaMapper;
import org.demo.persistence.impl.jpa.record.BookOrderJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookOrder persistence service - JPA implementation
 * 
 */
@Named("BookOrderPersistence")
@Component
@Transactional
public class BookOrderPersistenceJpa implements BookOrderPersistence {

	@Autowired
	private BookOrderJpaRepository repository;
	private final BookOrderJpaMapper mapper = new BookOrderJpaMapper();

	@Override
	public BookOrderRecord findById(Integer id) {
		BookOrderJpaRecord entity = repository.findOne(id);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<BookOrderRecord> findAll() {
		List<BookOrderJpaRecord> entities = (List<BookOrderJpaRecord>) repository.findAll();
		List<BookOrderRecord> records = new java.util.LinkedList<BookOrderRecord>();
		for (BookOrderJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public BookOrderRecord create(BookOrderRecord record) {
		BookOrderJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new BookOrderJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		BookOrderJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(BookOrderRecord record) {
		BookOrderJpaRecord entitySaved = null;
		BookOrderJpaRecord entity = repository.findOne(record.getId());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public BookOrderRecord save(BookOrderRecord record) {
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
	public boolean delete(BookOrderRecord record) {
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
	public boolean exists(BookOrderRecord record) {
		if (record != null) {
			return repository.exists(record.getId());
		}
		return false;
	}

	public BookOrderJpaRepository getBookOrderJpaRepository() {
		return repository;
	}

	public void setBookOrderJpaRepository(BookOrderJpaRepository repository) {
		this.repository = repository;
	}
}
