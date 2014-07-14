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

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetCheckoutDocsTestCases extends CMISTestParent {

	private List<String> documentObjectIds;
	private List<String> checkoutObjectIds;
	
	@Before
	public void setUp() throws Exception {
		documentObjectIds = new ArrayList<String>();
		checkoutObjectIds = new ArrayList<String>();	
		
		initializeTestRunMessage("getCheckoutDocsTestData");
		List<HashMap<String, Object>> documents = getTestRunMessageValue("docs");
		
		for (HashMap<String, Object> document : documents) {
			// Create the document in CMIS
			upsertOnTestRunMessage(document);
			upsertOnTestRunMessage("folderId", getRootFolderId());
			String objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();			
			documentObjectIds.add(objectId);
			
			// Check out the document
			ObjectId pwcObjectId = checkOut(objectId);
			checkoutObjectIds.add(pwcObjectId.getId());	
		}
		
		// Wait for Alfresco to index the checked out documents
		Thread.sleep(GET_CHECKOUT_DOCS_DELAY);	
			
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetCheckoutDocs() {
		List<String> checkedOutDocumentsIds = new ArrayList<String>(); 
		try {
			ItemIterable<Document> checkedOutDocuments = runFlowAndGetPayload("get-checkout-docs");
			for (Document doc : checkedOutDocuments) {
				checkedOutDocumentsIds.add(doc.getId());
			}
			assertTrue(checkedOutDocumentsIds.containsAll(checkoutObjectIds));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
			

	@After
	public void tearDown() throws Exception {
		for (String objectId : documentObjectIds) {
			cancelCheckOut(objectId, getObjectById(objectId));
		}

	}
}
