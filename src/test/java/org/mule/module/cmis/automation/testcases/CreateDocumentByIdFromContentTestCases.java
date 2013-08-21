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
			testObjects = (HashMap<String, Object>) context.getBean("createDocumentByIdFromContent");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateDocumentByIdFromContent() {
		try {
			ObjectId result = createDocumentByIdFromContent(rootFolderId(), (String) testObjects.get("filename"), (String) testObjects.get("contentRef"), (String) testObjects.get("mimeType"), 
					(VersioningState) testObjects.get("versioningState"), (String) testObjects.get("objectType"), (Map<String, Object>) testObjects.get("propertiesRef"));
					
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
