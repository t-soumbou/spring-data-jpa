package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.CountryRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.CountryJpaRepository;
import org.demo.persistence.CountryPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.CountryJpaMapper;
import org.demo.persistence.impl.jpa.record.CountryJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Country persistence service - JPA implementation
 * 
 */
@Named("CountryPersistence")
@Component
@Transactional
public class CountryPersistenceJpa implements CountryPersistence {

	@Autowired
	private CountryJpaRepository repository;
	private final CountryJpaMapper mapper = new CountryJpaMapper();

	@Override
	public CountryRecord findById(String code) {
		CountryJpaRecord entity = repository.findOne(code);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<CountryRecord> findAll() {
		List<CountryJpaRecord> entities = (List<CountryJpaRecord>) repository.findAll();
		List<CountryRecord> records = new java.util.LinkedList<CountryRecord>();
		for (CountryJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public CountryRecord create(CountryRecord record) {
		CountryJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new CountryJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		CountryJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(CountryRecord record) {
		CountryJpaRecord entitySaved = null;
		CountryJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public CountryRecord save(CountryRecord record) {
		update(record);
		return record;
	}

	@Override
	public boolean deleteById(String code) {
		if (exists(code)) {
			repository.delete(code);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(CountryRecord record) {
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
	public boolean exists(String code) {
		return repository.exists(code);
	}

	@Override
	public boolean exists(CountryRecord record) {
		if (record != null) {
			return repository.exists(record.getCode());
		}
		return false;
	}

	public CountryJpaRepository getCountryJpaRepository() {
		return repository;
	}

	public void setCountryJpaRepository(CountryJpaRepository repository) {
		this.repository = repository;
	}
}
