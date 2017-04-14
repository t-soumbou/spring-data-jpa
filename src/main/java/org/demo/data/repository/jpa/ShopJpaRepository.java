package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.ShopJpaRecord;

/**
 * Repository : Shop.
 */
public interface ShopJpaRepository extends PagingAndSortingRepository<ShopJpaRecord, String> {

}
