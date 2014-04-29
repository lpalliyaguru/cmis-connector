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

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class QueryTestCases extends CMISTestParent {


	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("queryTestData");
		upsertOnTestRunMessage("parentObjectId", getRootFolderId());
		ObjectId objectId = runFlowAndGetPayload("create-folder");
		upsertOnTestRunMessage("objectId", objectId.getId());
		String folderName = getTestRunMessageValue("folderName").toString();
		upsertOnTestRunMessage("filter", String.format("cmis:name = '%s'", folderName));
		upsertOnTestRunMessage("statement", String.format("SELECT * FROM cmis:folder WHERE cmis:name = '%s'", folderName));
		Thread.sleep(QUERY_DELAY);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject(getTestRunMessageValue("objectId").toString(), true);
	}

	@Category({RegressionTests.class})
	@Test
	public void testQuery() {
		try {				
			ItemIterable<QueryResult> payload = runFlowAndGetPayload("query");
			ItemIterable<QueryResult> page = payload.getPage();
			
			long pageNumItems = page.getPageNumItems();
			assertTrue(pageNumItems == 1L);
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	

	@Category({RegressionTests.class})
	@Test
	public void testQuery_Filtered() {
		upsertOnTestRunMessage("filter", null);
		try {
			ItemIterable<QueryResult> payload = runFlowAndGetPayload("query");
			ItemIterable<QueryResult> page = payload.getPage();
			
			long pageNumItems = page.getPageNumItems();
			assertTrue(pageNumItems == 1L);
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
