package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.BookOrderJpaRecord;

/**
 * Repository : BookOrder.
 */
public interface BookOrderJpaRepository extends PagingAndSortingRepository<BookOrderJpaRecord, Integer> {

}
