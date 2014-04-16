/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;

public class MoveObjectTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("moveObject");
			String rootFolderId = rootFolderId();
			
			ObjectId targetFolderId = createFolder((String) testObjects.get("targetFolderName"), rootFolderId);

			ObjectId fileObjectId = createDocumentById(rootFolderId,
					(String) testObjects.get("filename"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));
			
			testObjects.put("sourceFolderId", rootFolderId);
			testObjects.put("targetFolderId", targetFolderId.getId());
			testObjects.put("objectId", fileObjectId.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class })
	@Test
	public void testMoveObject() {
		try {
			MessageProcessor flow = lookupMessageProcessor("move-object");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			FileableCmisObject result = (FileableCmisObject) response.getMessage().getPayload();

			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(objectId, true);
			
			String targetFolderId = (String) testObjects.get("targetFolderId");
			delete(targetFolderId, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
