package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.BookJpaRecord;

/**
 * Repository : Book.
 */
public interface BookJpaRepository extends PagingAndSortingRepository<BookJpaRecord, Integer> {

}
