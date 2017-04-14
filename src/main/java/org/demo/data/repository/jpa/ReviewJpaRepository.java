package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.ReviewJpaRecord;
import org.demo.persistence.impl.jpa.record.ReviewJpaRecordKey;

/**
 * Repository : Review.
 */
public interface ReviewJpaRepository extends PagingAndSortingRepository<ReviewJpaRecord, ReviewJpaRecordKey> {

}
