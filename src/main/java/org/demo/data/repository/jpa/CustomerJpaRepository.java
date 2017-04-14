package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.CustomerJpaRecord;

/**
 * Repository : Customer.
 */
public interface CustomerJpaRepository extends PagingAndSortingRepository<CustomerJpaRecord, String> {

}
