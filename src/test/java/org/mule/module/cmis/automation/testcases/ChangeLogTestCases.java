package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class ChangeLogTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("changelog");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	@Ignore
	public void testChangelog() {
		testObjects.put("changeLogToken", "41784");
		try {
			MessageProcessor flow = lookupFlowConstruct("changelog");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			ChangeEvents changeEvents = (ChangeEvents) response.getMessage().getPayload();
			
			System.out.println(changeEvents);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
