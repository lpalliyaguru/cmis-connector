package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class RepositoryTestCases extends CMISTestParent {
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("repositories");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testRepositories() {
		try {
			MessageProcessor flow = lookupFlowConstruct("repositories");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Repository> repositories = (List<Repository>) response.getMessage().getPayload();
			assertTrue(repositories.size() > 0);
			
			// May perform more assertions in the future
			// However, different repository implementations (such as assigned IDs) may differ
			// from one CMIS implementation to another.
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
