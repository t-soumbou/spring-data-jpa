package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.persistence.impl.jpa.record.CountryJpaRecord;

/**
 * Repository : Country.
 */
public interface CountryJpaRepository extends PagingAndSortingRepository<CountryJpaRecord, String> {

}
