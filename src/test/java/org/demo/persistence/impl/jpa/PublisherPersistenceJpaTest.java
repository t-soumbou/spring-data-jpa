package org.demo.persistence.impl.jpa;

import org.demo.data.repository.jpa.PublisherJpaRepository;
import org.demo.persistence.PublisherPersistenceGenericTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.demo.persistence.impl.jpa.commons.ApplicationConfiguration;

/**
 * JUnit tests for Publisher persistence service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class PublisherPersistenceJpaTest extends PublisherPersistenceGenericTest {
	
	@Autowired
	private PublisherJpaRepository	repository;

	@Test
	public void testPersistenceService() {

    	PublisherPersistenceJpa persistenceService = new PublisherPersistenceJpa();
    	persistenceService.setPublisherJpaRepository(repository);


    	testPersistenceService(persistenceService);
	}	
}
