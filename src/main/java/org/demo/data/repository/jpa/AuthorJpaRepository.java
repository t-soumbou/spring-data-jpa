package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.AuthorJpaRecord;

/**
 * Repository : Author.
 */
public interface AuthorJpaRepository extends PagingAndSortingRepository<AuthorJpaRecord, Integer> {

}
