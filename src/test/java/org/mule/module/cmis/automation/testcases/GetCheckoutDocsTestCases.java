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

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getCheckoutDocs");
			List<HashMap<String, Object>> documents = (List<HashMap<String, Object>>) testObjects.get("docs");

			assertTrue("At least one document must be defined for this test to run.", documents.size() > 0);
			
			String rootFolderId = rootFolderId();
			List<String> documentObjectIds = new ArrayList<String>();
			List<String> checkinObjectIds = new ArrayList<String>();
			
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
				
				String checkinComment = (String) document.get("checkinComment");
				Boolean major = (Boolean) document.get("major");
				Map<String, Object> properties = (Map<String, Object>) document.get("properties");
				
				// Check in the created document
				ObjectId checkinObjectId = checkIn(checkinComment, documentObjectId.getId(), filename, content, mimeType, major, properties);
				checkinObjectIds.add(checkinObjectId.getId());
				
				// Check out the document
				checkOut(documentObjectId.getId());
			}
			testObjects.put("checkinObjectIds", checkinObjectIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetCheckoutDocs() {
		try {
			List<String> documents = (List<String>) testObjects.get("documentObjectIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-checkout-docs");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			int listSize = 0;
			
			ItemIterable<Document> checkedOutDocuments = (ItemIterable<Document>) response.getMessage().getPayload();
			for (Document doc : checkedOutDocuments) {
				listSize++;
				assertTrue(documents.contains(doc.getId()));
			}
			
			assertTrue(documents.size() == listSize);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
	@After
	public void tearDown() {
		try {
			List<String> documentObjectIds = (List<String>) testObjects.get("documentObjectIds");
			for (String documentId : documentObjectIds) {
				delete(getObjectById(documentId), documentId, true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
