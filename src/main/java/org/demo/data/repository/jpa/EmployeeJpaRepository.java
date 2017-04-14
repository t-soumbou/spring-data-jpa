package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.EmployeeJpaRecord;

/**
 * Repository : Employee.
 */
public interface EmployeeJpaRepository extends PagingAndSortingRepository<EmployeeJpaRecord, String> {

}
