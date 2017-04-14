package org.demo.persistence.impl.jpa;

import org.demo.data.repository.jpa.AuthorJpaRepository;
import org.demo.persistence.AuthorPersistenceGenericTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.demo.persistence.impl.jpa.commons.ApplicationConfiguration;

/**
 * JUnit tests for Author persistence service
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class AuthorPersistenceJpaTest extends AuthorPersistenceGenericTest {
	
	@Autowired
	private AuthorJpaRepository	repository;

	@Test
	public void testPersistenceService() {

    	AuthorPersistenceJpa persistenceService = new AuthorPersistenceJpa();
    	persistenceService.setAuthorJpaRepository(repository);


    	testPersistenceService(persistenceService);
	}	
}
