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

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class CheckOutTestCases extends CMISTestParent {

	private String objectId;
	
	@Before
	public void setUp() throws Exception {
			initializeTestRunMessage("checkOutTestData");	
			upsertOnTestRunMessage("folderId", getRootFolderId());
			objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
			upsertOnTestRunMessage("documentId", objectId);
			upsertOnTestRunMessage("documentRef", getObjectById(objectId));

	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCheckOut() {
		try {
			ObjectId pwcObjectId = runFlowAndGetPayload("check-out");
			assertTrue(pwcObjectId != null);
			assertTrue(pwcObjectId.getId() != null && !pwcObjectId.getId().isEmpty() && !pwcObjectId.getId().trim().isEmpty());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject(getTestRunMessageValue("documentId").toString(), true); 
	}
	
}
