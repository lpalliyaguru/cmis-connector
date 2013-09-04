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

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;

public class CreateRelationshipTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("createRelationship");
			String rootFolderId = rootFolderId();

			ObjectId file1ObjectId = createDocumentById(rootFolderId,
					(String) testObjects.get("filename1"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));
			
			ObjectId file2ObjectId = createDocumentById(rootFolderId,
					(String) testObjects.get("filename2"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			testObjects.put("parentObjectId", file1ObjectId.getId());
			testObjects.put("childObjectId", file2ObjectId.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateRelationship() {
		try {
			Object result = createRelationship((String) testObjects.get("parentObjectId"), 
					(String) testObjects.get("childObjectId"), 
					(String) testObjects.get("relationshipType"));
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			String parentObjectId = (String) testObjects.get("parentObjectId");
			String childObjectId = (String) testObjects.get("childObjectId");
			delete(getObjectById(parentObjectId), parentObjectId, true);
			delete(getObjectById(childObjectId), childObjectId, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
