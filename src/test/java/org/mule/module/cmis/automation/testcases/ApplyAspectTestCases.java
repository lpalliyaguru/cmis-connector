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

import java.util.HashMap;
import java.util.Map;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;

public class ApplyAspectTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("applyAspect");

			String rootFolderId = rootFolderId();
			String filename = testObjects.get("filename").toString();
			String mimeType = testObjects.get("mimeType").toString();
			String content = testObjects.get("content").toString();
			String objectType = testObjects.get("objectType").toString();
			Map<String, Object> propertiesRef = (Map<String, Object>) testObjects.get("propertiesRef");
			VersioningState versioningState = (VersioningState) testObjects.get("versioningState");
			
			ObjectId documentObjectId = createDocumentById(rootFolderId, filename, content, mimeType, versioningState, objectType, propertiesRef);
			testObjects.put("objectId", documentObjectId.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testApplyAspect() {
		try {
			String objectId = (String) testObjects.get("objectId");
			String aspectName = "P:" + testObjects.get("aspectName").toString();
			
			Map<String, String> aspectProperties = (Map<String, String>) testObjects.get("aspectProperties");
			testObjects.put("propertiesRef", aspectProperties);
			
			MessageProcessor flow = lookupMessageProcessor("apply-aspect");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// We are using alfresco, so type cast it specifically to an alfresco document
			AlfrescoDocument document = (AlfrescoDocument) getObjectById(objectId);
			assertTrue(document.hasAspect(aspectName));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String documentId = (String) testObjects.get("objectId");
			delete(documentId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}	
}
