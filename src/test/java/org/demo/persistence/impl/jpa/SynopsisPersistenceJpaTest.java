package org.demo.persistence.impl.jpa;

import org.demo.data.repository.jpa.SynopsisJpaRepository;
import org.demo.persistence.SynopsisPersistenceGenericTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.demo.persistence.impl.jpa.commons.ApplicationConfiguration;

/**
 * JUnit tests for Synopsis persistence service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SynopsisPersistenceJpaTest extends SynopsisPersistenceGenericTest {
	
	@Autowired
	private SynopsisJpaRepository	repository;

	@Test
	public void testPersistenceService() {

    	SynopsisPersistenceJpa persistenceService = new SynopsisPersistenceJpa();
    	persistenceService.setSynopsisJpaRepository(repository);


    	testPersistenceService(persistenceService);
	}	
}
