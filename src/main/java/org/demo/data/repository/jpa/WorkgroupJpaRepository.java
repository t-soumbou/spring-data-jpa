package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.WorkgroupJpaRecord;

/**
 * Repository : Workgroup.
 */
public interface WorkgroupJpaRepository extends PagingAndSortingRepository<WorkgroupJpaRecord, Short> {

}
