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

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;

public class CheckInTestCases extends CMISTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("checkIn");
			
			String rootFolderId = rootFolderId();
			String filename = testObjects.get("filename").toString();
			String mimeType = testObjects.get("mimeType").toString();
			String content = testObjects.get("contentRef").toString();
			String objectType = testObjects.get("objectType").toString();
			Map<String, Object> propertiesRef = (Map<String, Object>) testObjects.get("propertiesRef");
			VersioningState versioningState = (VersioningState) testObjects.get("versioningState");
			
			ObjectId documentObjectId = createDocumentByIdFromContent(rootFolderId, filename, content, mimeType, versioningState, objectType, propertiesRef);
			testObjects.put("documentObjectId", documentObjectId);
			testObjects.put("documentId", documentObjectId.getId());
			
			checkOut(documentObjectId.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCheckIn() {
		try {
			MessageProcessor flow = lookupMessageProcessor("check-in");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			ObjectId checkedInId = (ObjectId) response.getMessage().getPayload();
			assertTrue(checkedInId != null && !checkedInId.getId().isEmpty() && !checkedInId.getId().trim().isEmpty());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("documentId");
			delete(objectId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
