package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.BadgeJpaRecord;

/**
 * Repository : Badge.
 */
public interface BadgeJpaRepository extends PagingAndSortingRepository<BadgeJpaRecord, Integer> {

}
