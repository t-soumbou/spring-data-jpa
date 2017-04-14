package org.demo.persistence.impl.jpa;

import org.demo.data.repository.jpa.CountryJpaRepository;
import org.demo.persistence.CountryPersistenceGenericTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.demo.persistence.impl.jpa.commons.ApplicationConfiguration;

/**
 * JUnit tests for Country persistence service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CountryPersistenceJpaTest extends CountryPersistenceGenericTest {
	
	@Autowired
	private CountryJpaRepository	repository;

	@Test
	public void testPersistenceService() {

    	CountryPersistenceJpa persistenceService = new CountryPersistenceJpa();
    	persistenceService.setCountryJpaRepository(repository);


    	testPersistenceService(persistenceService);
	}	
}
