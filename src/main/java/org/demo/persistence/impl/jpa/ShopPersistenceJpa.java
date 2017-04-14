package org.demo.persistence.impl.jpa;

import java.util.List;

import javax.inject.Named;

import org.demo.data.record.ShopRecord; // "Neutral Record" class 
import org.demo.data.repository.jpa.ShopJpaRepository;
import org.demo.persistence.ShopPersistence; // Persistence service interface
import org.demo.persistence.impl.jpa.mapper.ShopJpaMapper;
import org.demo.persistence.impl.jpa.record.ShopJpaRecord; // "JPA Record" class (with JPA mapping) 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Shop persistence service - JPA implementation
 * 
 */
@Named("ShopPersistence")
@Component
@Transactional
public class ShopPersistenceJpa implements ShopPersistence {

	@Autowired
	private ShopJpaRepository repository;
	private final ShopJpaMapper mapper = new ShopJpaMapper();

	@Override
	public ShopRecord findById(String code) {
		ShopJpaRecord entity = repository.findOne(code);
		return mapper.mapEntityToRecord(entity);
	}

	@Override
	public List<ShopRecord> findAll() {
		List<ShopJpaRecord> entities = (List<ShopJpaRecord>) repository.findAll();
		List<ShopRecord> records = new java.util.LinkedList<ShopRecord>();
		for (ShopJpaRecord entity : entities) {
			records.add(mapper.mapEntityToRecord(entity));
		}
		return records;
	}

	@Override
	public ShopRecord create(ShopRecord record) {
		ShopJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			throw new IllegalStateException("already.exists");
		}
		entity = new ShopJpaRecord();
		mapper.mapRecordToEntity(record, entity);
		ShopJpaRecord entitySaved = repository.save(entity);
		return mapper.mapEntityToRecord(entitySaved);
	}

	@Override
	public boolean update(ShopRecord record) {
		ShopJpaRecord entitySaved = null;
		ShopJpaRecord entity = repository.findOne(record.getCode());
		if (entity != null) {
			mapper.mapRecordToEntity(record, entity);
			entitySaved = repository.save(entity);
		}
		return (entitySaved != null);
	}

	@Override
	public ShopRecord save(ShopRecord record) {
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
	public boolean delete(ShopRecord record) {
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
	public boolean exists(ShopRecord record) {
		if (record != null) {
			return repository.exists(record.getCode());
		}
		return false;
	}

	public ShopJpaRepository getShopJpaRepository() {
		return repository;
	}

	public void setShopJpaRepository(ShopJpaRepository repository) {
		this.repository = repository;
	}
}
