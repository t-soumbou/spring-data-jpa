package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.EmployeeGroupJpaRecord;
import org.demo.persistence.impl.jpa.record.EmployeeGroupJpaRecordKey;

/**
 * Repository : EmployeeGroup.
 */
public interface EmployeeGroupJpaRepository extends PagingAndSortingRepository<EmployeeGroupJpaRecord, EmployeeGroupJpaRecordKey> {

}
