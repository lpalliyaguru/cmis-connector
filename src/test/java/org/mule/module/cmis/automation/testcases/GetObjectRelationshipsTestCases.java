package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;

public class GetObjectRelationshipsTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getObjectRelationships");
			
			String rootFolderId = rootFolderId();
			ObjectId result = createFolder((String) testObjects.get("folderName"), rootFolderId);
			String folderId = result.getId();

			ObjectId file1ObjectId = createDocumentById(folderId,
					(String) testObjects.get("filename1"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));
			
			ObjectId file2ObjectId = createDocumentById(folderId,
					(String) testObjects.get("filename2"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));
			
			ObjectId relationshipId = createRelationship(file1ObjectId.getId(), file2ObjectId.getId(), 
					(String) testObjects.get("relationshipType"));
			
			testObjects.put("objectId", file1ObjectId.getId());
			testObjects.put("relationshipId", relationshipId.getId());
			testObjects.put("folderId", folderId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetObjectRelationships() {
		try {
			String objectId = (String) testObjects.get("objectId");
			String relationshipId = (String) testObjects.get("relationshipId");
			
			List<Relationship> result = getObjectRelationships(objectId);
			
			boolean found = false;
			for (Relationship relationship : result) {
				if (relationship.getId().equals(relationshipId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@After
	public void tearDown() {
		try {
			String folderId = (String) testObjects.get("folderId");
			deleteTree(getObjectById(folderId), folderId, true, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
