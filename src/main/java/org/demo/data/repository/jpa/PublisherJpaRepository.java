package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.PublisherJpaRecord;

/**
 * Repository : Publisher.
 */
public interface PublisherJpaRepository extends PagingAndSortingRepository<PublisherJpaRecord, Integer> {

}
