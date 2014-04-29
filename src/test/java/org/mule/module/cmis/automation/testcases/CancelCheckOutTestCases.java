/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class CancelCheckOutTestCases extends CMISTestParent {

	private String objectId;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("cancelCheckOutTestData");
		upsertOnTestRunMessage("folderId", getRootFolderId());
		objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
		upsertOnTestRunMessage("objectId", objectId);
		checkOut(objectId);
	}

	@Category({RegressionTests.class})
	@Test
	public void testCancelCheckOut() {
		try {
			runFlowAndGetPayload("cancel-check-out");
			ItemIterable<Document> checkedOutDocs = getCheckedOutDocuments();		
			for (Document doc : checkedOutDocs) {
				assertFalse(doc.getId().equals(objectId));
			}			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject(objectId, true);
	}
}
