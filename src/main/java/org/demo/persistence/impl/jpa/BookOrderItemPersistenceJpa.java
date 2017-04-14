package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.BookOrderItemRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.BookOrderItemJpaRepository;
import org.demo.persistence.BookOrderItemPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.BookOrderItemJpaMapper;
import org.demo.persistence.impl.jpa.record.BookOrderItemJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.demo.persistence.impl.jpa.record.BookOrderItemJpaRecordKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookOrderItem persistence service - JPA implementation
 * 
 */
@Named("BookOrderItemPersistence")
@Component
@Transactional
public class BookOrderItemPersistenceJpa implements BookOrderItemPersistence {

	@Autowired
	private BookOrderItemJpaRepository repository;
	private final BookOrderItemJpaMapper mapper = new BookOrderItemJpaMapper();
	
	
	private BookOrderItemJpaRecordKey buildKey(BookOrderItemRecord record){
		return new  BookOrderItemJpaRecordKey( record.getBookOrderId(), record.getBookId() );
	}
	
	private BookOrderItemJpaRecordKey buildKey(Integer bookOrderId, Integer bookId){
		return new BookOrderItemJpaRecordKey( bookOrderId, bookId );
	}

	@Override
	public BookOrderItemRecord findById( Integer bookOrderId, Integer bookId ) {
		BookOrderItemJpaRecordKey key = buildKey( bookOrderId, bookId );
		BookOrderItemJpaRecord entity = repository.findOne(key);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<BookOrderItemRecord> findAll() {
		List<BookOrderItemJpaRecord> entities = (List<BookOrderItemJpaRecord>) repository.findAll();
		List<BookOrderItemRecord> records = new java.util.LinkedList<BookOrderItemRecord>();
		for (BookOrderItemJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public BookOrderItemRecord create(BookOrderItemRecord record) {
		BookOrderItemJpaRecordKey key = buildKey(record);
		BookOrderItemJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new BookOrderItemJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		BookOrderItemJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(BookOrderItemRecord record) {
		BookOrderItemJpaRecord entitySaved = null;
		BookOrderItemJpaRecordKey key = buildKey(record);
		BookOrderItemJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public BookOrderItemRecord save(BookOrderItemRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(Integer bookOrderId, Integer bookId ) {
		BookOrderItemJpaRecordKey key = buildKey( bookOrderId, bookId );
		if (exists(bookOrderId, bookId)) {
			repository.delete(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(BookOrderItemRecord record) {
		BookOrderItemJpaRecordKey key = buildKey(record);
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
	public boolean exists(Integer bookOrderId, Integer bookId) {
		BookOrderItemJpaRecordKey key = buildKey( bookOrderId, bookId );
		return repository.exists(key);
	}

	@Override
	public boolean exists(BookOrderItemRecord record) {
		BookOrderItemJpaRecordKey key = buildKey(record);
		if (record != null) {
			return repository.exists(key);
		}
		return false;
	}

	public BookOrderItemJpaRepository getBookOrderItemJpaRepository() {
		return repository;
	}

	public void setBookOrderItemJpaRepository(BookOrderItemJpaRepository repository) {
		this.repository = repository;
	}
}
