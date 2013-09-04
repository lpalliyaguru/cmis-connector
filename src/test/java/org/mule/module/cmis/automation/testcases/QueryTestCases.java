/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class QueryTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("query");
			
			String folderName = (String) testObjects.get("folderName");
			
			ObjectId objectId = createFolder(folderName, rootFolderId());
			testObjects.put("objectId", objectId.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testQuery() {
		try {

			Thread.sleep(20000);
			
			MessageProcessor flow = lookupFlowConstruct("query");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			ItemIterable<QueryResult> payload = (ItemIterable<QueryResult>) response.getMessage().getPayload();
			ItemIterable<QueryResult> page = payload.getPage();
			
			long pageNumItems = page.getPageNumItems();
			assertTrue(pageNumItems == 1L);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testQuery_Filtered() {
		try {
			Thread.sleep(20000);
			
			MessageProcessor flow = lookupFlowConstruct("query-filtered");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			ItemIterable<QueryResult> payload = (ItemIterable<QueryResult>) response.getMessage().getPayload();
			ItemIterable<QueryResult> page = payload.getPage();
			
			long pageNumItems = page.getPageNumItems();
			assertTrue(pageNumItems == 1L);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(objectId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
