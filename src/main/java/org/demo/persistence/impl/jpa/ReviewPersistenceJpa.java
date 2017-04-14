package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.ReviewRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.ReviewJpaRepository;
import org.demo.persistence.ReviewPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.ReviewJpaMapper;
import org.demo.persistence.impl.jpa.record.ReviewJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.demo.persistence.impl.jpa.record.ReviewJpaRecordKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Review persistence service - JPA implementation
 * 
 */
@Named("ReviewPersistence")
@Component
@Transactional
public class ReviewPersistenceJpa implements ReviewPersistence {

	@Autowired
	private ReviewJpaRepository repository;
	private final ReviewJpaMapper mapper = new ReviewJpaMapper();
	
	
	private ReviewJpaRecordKey buildKey(ReviewRecord record){
		return new  ReviewJpaRecordKey( record.getCustomerCode(), record.getBookId() );
	}
	
	private ReviewJpaRecordKey buildKey(String customerCode, Integer bookId){
		return new ReviewJpaRecordKey( customerCode, bookId );
	}

	@Override
	public ReviewRecord findById( String customerCode, Integer bookId ) {
		ReviewJpaRecordKey key = buildKey( customerCode, bookId );
		ReviewJpaRecord entity = repository.findOne(key);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<ReviewRecord> findAll() {
		List<ReviewJpaRecord> entities = (List<ReviewJpaRecord>) repository.findAll();
		List<ReviewRecord> records = new java.util.LinkedList<ReviewRecord>();
		for (ReviewJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public ReviewRecord create(ReviewRecord record) {
		ReviewJpaRecordKey key = buildKey(record);
		ReviewJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new ReviewJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		ReviewJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(ReviewRecord record) {
		ReviewJpaRecord entitySaved = null;
		ReviewJpaRecordKey key = buildKey(record);
		ReviewJpaRecord entity = repository.findOne(key);
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public ReviewRecord save(ReviewRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(String customerCode, Integer bookId ) {
		ReviewJpaRecordKey key = buildKey( customerCode, bookId );
		if (exists(customerCode, bookId)) {
			repository.delete(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(ReviewRecord record) {
		ReviewJpaRecordKey key = buildKey(record);
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
	public boolean exists(String customerCode, Integer bookId) {
		ReviewJpaRecordKey key = buildKey( customerCode, bookId );
		return repository.exists(key);
	}

	@Override
	public boolean exists(ReviewRecord record) {
		ReviewJpaRecordKey key = buildKey(record);
		if (record != null) {
			return repository.exists(key);
		}
		return false;
	}

	public ReviewJpaRepository getReviewJpaRepository() {
		return repository;
	}

	public void setReviewJpaRepository(ReviewJpaRepository repository) {
		this.repository = repository;
	}
}
