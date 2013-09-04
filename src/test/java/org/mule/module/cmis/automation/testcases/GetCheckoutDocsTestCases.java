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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;

public class GetCheckoutDocsTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getCheckoutDocs");
			List<HashMap<String, Object>> documents = (List<HashMap<String, Object>>) testObjects.get("docs");

			assertTrue("At least one document must be defined for this test to run.", documents.size() > 0);
			
			String rootFolderId = rootFolderId();
			List<String> documentObjectIds = new ArrayList<String>();
			List<String> checkoutObjectIds = new ArrayList<String>();
			
			for (HashMap<String, Object> document : documents) {
				String filename = document.get("filename").toString();
				String mimeType = document.get("mimeType").toString();
				String content = document.get("content").toString();
				String objectType = document.get("objectType").toString();
				Map<String, Object> propertiesRef = (Map<String, Object>) document.get("propertiesRef");
				VersioningState versioningState = (VersioningState) document.get("versioningState");
				
				// Create the document in CMIS
				ObjectId documentObjectId = createDocumentById(rootFolderId, filename, content, mimeType, versioningState, objectType, propertiesRef);
				documentObjectIds.add(documentObjectId.getId());
				testObjects.put("documentObjectIds", documentObjectIds);
				
				// Check out the document
				ObjectId pwcObjectId = checkOut(documentObjectId.getId());
				checkoutObjectIds.add(pwcObjectId.getId());
				testObjects.put("checkoutObjectIds", checkoutObjectIds);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetCheckoutDocs() {
		try {
			// Wait for Alfresco to index the checked out documents
			Thread.sleep(20000);
			
			List<String> pwcObjectIds = (List<String>) testObjects.get("checkoutObjectIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-checkout-docs");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			int listSize = 0;
			
			ItemIterable<Document> checkedOutDocuments = (ItemIterable<Document>) response.getMessage().getPayload();
			for (Document doc : checkedOutDocuments) {
				listSize++;
				assertTrue(pwcObjectIds.contains(doc.getId()));
			}
			
			assertTrue(pwcObjectIds.size() == listSize);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
			
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() {
		try {
			List<String> documentObjectIds = (List<String>) testObjects.get("documentObjectIds");
			for (String documentId : documentObjectIds) {
				cancelCheckOut(documentId);
				delete(documentId, true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
