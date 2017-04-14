package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.BookOrderItemJpaRecord;
import org.demo.persistence.impl.jpa.record.BookOrderItemJpaRecordKey;

/**
 * Repository : BookOrderItem.
 */
public interface BookOrderItemJpaRepository extends PagingAndSortingRepository<BookOrderItemJpaRecord, BookOrderItemJpaRecordKey> {

}
