package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class GetTypeDefinitionTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getTypeDefinition");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetTypeDefinition() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-type-definition");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			ObjectType objType = (ObjectType) response.getMessage().getPayload();
			assertNotNull(objType);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
