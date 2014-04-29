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

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class ApplyAspectTestCases extends CMISTestParent {

	private String objectId;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("applyAspectTestData");
		upsertOnTestRunMessage("folderId", getRootFolderId());
		objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
		upsertOnTestRunMessage("objectId", objectId);

	}

	@Category({RegressionTests.class})
	@Test
	public void testApplyAspect() {
		try {
			String aspectName = "P:" + getTestRunMessageValue("aspectName").toString();
			runFlowAndGetPayload("apply-aspect");
			
			// We are using alfresco, so type cast it specifically to an alfresco document
			AlfrescoDocument document = (AlfrescoDocument) getObjectById(objectId);
			assertTrue(document.hasAspect(aspectName));
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject(objectId, true);
	}	
}
