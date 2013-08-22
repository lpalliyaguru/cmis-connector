package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;

public class CreateDocumentByIdFromContentTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("createDocumentByIdFromContent");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testCreateDocumentByIdFromContent_payload_is_String() {
		try {
			ObjectId result = createDocumentByIdFromContent(lookupFlowConstruct("create-document-by-id-from-content"),
					rootFolderId(),
					(String) testObjects.get("filename"),
					(String) testObjects.get("contentRef"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			assertNotNull(result);
			testObjects.put("objectId", result.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testCreateDocumentByIdFromContent_assert_content_ref_attrib_is_valid() {
		try {
			ObjectId result = createDocumentByIdFromContent(lookupFlowConstruct("create-document-by-id-from-content-content-ref"),
					rootFolderId(),
					(String) testObjects.get("filename"),
					testObjects,
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			assertNotNull(result);
			testObjects.put("objectId", result.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	// If this test passes then this jira is solved: https://www.mulesoft.org/jira/browse/CLDCONNECT-1039
	public void testCreateDocumentByIdFromContent_no_properties() {
		try {
			ObjectId result = createDocumentByIdFromContent(lookupFlowConstruct("create-document-by-id-from-content-no-properties"),
					rootFolderId(),
					(String) testObjects.get("filename"),
					(String) testObjects.get("contentRef"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			assertNotNull(result);
			testObjects.put("objectId", result.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(getObjectById(objectId), objectId, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
