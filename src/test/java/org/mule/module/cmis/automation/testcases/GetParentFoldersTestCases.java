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
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GetParentFoldersTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("getParentFolders");
			String rootFolderId = rootFolderId();
			testObjects.put("parentObjectId", rootFolderId);
			ObjectId createFolderResult = createFolder(
					(String) testObjects.get("folderName"), rootFolderId);

			testObjects.put("objectId", createFolderResult.getId());
			testObjects.put("cmisObjectRef",
					getObjectById(createFolderResult.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testGetParentFolders() {
		try {
			List<Folder> folders = getParentFolders((String) testObjects.get("objectId"));
			assertNotNull(folders);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testGetParentFolders_With_CmisObjectRef() {
		try {
			String objectId = (String) testObjects.get("objectId");
			CmisObject cmisObjectRef = (CmisObject) testObjects.get("cmisObjectRef");
			List<Folder> folders = getParentFolders(objectId, cmisObjectRef);
			assertNotNull(folders);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			delete((String) testObjects.get("objectId"), true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
