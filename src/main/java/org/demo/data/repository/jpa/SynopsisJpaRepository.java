package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.SynopsisJpaRecord;

/**
 * Repository : Synopsis.
 */
public interface SynopsisJpaRepository extends PagingAndSortingRepository<SynopsisJpaRecord, Integer> {

}
