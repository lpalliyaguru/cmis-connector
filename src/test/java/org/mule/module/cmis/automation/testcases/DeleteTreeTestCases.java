package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;

public class DeleteTreeTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("deleteTree");
			testObjects.put("parentObjectId", rootFolderId());
			ObjectId folderObjectId = createFolder(
					(String) testObjects.get("folderName"),
					(String) testObjects.get("parentObjectId"));
			String folderId = folderObjectId.getId();

			createDocumentById(folderId, (String) testObjects.get("filename"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			testObjects.put("folderId", folderId);
			testObjects.put("folder", getObjectById(folderId));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testDeleteTree() {
		try {
			List<String> objectsFailedToDelete = deleteTree(
					(CmisObject) testObjects.get("folder"),
					(String) testObjects.get("folderId"),
					(Boolean) testObjects.get("allversions"),
					(Boolean) testObjects.get("continueOnFailure"));
			assertNotNull(objectsFailedToDelete);
			assertEquals(0, objectsFailedToDelete.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
