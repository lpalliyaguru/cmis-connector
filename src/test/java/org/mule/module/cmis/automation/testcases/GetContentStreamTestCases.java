/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetContentStreamTestCases extends CMISTestParent {

	private String objectId;

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getContentStreamTestData");
		upsertOnTestRunMessage("folderId", getRootFolderId());
		objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();

		upsertOnTestRunMessage("objectId", objectId);
		upsertOnTestRunMessage("cmisObjectRef", getObjectById(objectId));

	}

	@Category({ RegressionTests.class })
	@Test
	public void testGetContentStream() {
		try {
			ContentStream result = runFlowAndGetPayload("get-content-stream");
			assertNotNull(result);
			assertEquals(getTestRunMessageValue("contentRef"), IOUtils.toString(result.getStream()));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetContentStreamWithCmisObject() {
		try {
			ContentStream result = runFlowAndGetPayload("get-content-stream-with-cmis-object-ref");
			assertNotNull(result);
			assertEquals(getTestRunMessageValue("contentRef"), IOUtils.toString(result.getStream()));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
		
	@After
	public void tearDown() throws Exception {
		deleteObject(objectId, true);

	}
}
