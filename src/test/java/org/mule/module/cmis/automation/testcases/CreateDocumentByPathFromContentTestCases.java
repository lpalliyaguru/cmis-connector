/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;

public class CreateDocumentByPathFromContentTestCases extends CMISTestParent {

	private static String TEST_FOLDER_NAME = "folder1";

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("createDocumentByPathFromContent");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testCreateDocumentByPathFromContent_rootPath() {
		testObjects.put("folderPath", "/");
		try {
			ObjectId result = createDocumentByPathFromContent(
					lookupMessageProcessor("create-document-by-path-from-content"),
					(String) testObjects.get("folderPath"),
					(String) testObjects.get("filename"),
					(String) testObjects.get("contentRef"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"),
					(Boolean) testObjects.get("force"));

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
	public void testCreateDocumentByPathFromContent_nonRootPath() {
		testObjects.put("folderPath", "/" + TEST_FOLDER_NAME);
		try {
			ObjectId result = createDocumentByPathFromContent(
					lookupMessageProcessor("create-document-by-path-from-content"),
					(String) testObjects.get("folderPath"),
					(String) testObjects.get("filename"),
					(String) testObjects.get("contentRef"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"),
					(Boolean) testObjects.get("force"));

			assertNotNull(result);
			String objectId = result.getId();
			testObjects.put("objectId", objectId);
//			CmisObject cmisObject = getObjectById(objectId);
			List<Folder> folders = getParentFolders(objectId);

			assertTrue(folders.size() == 1);
			Folder folder = folders.get(0);
			assertEquals(TEST_FOLDER_NAME, folder.getName());

			testObjects.put("parentFolder", folder);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testCreateDocumentByPathFromContent_assert_content_ref_attrib_is_valid() {
		testObjects.put("folderPath", "/");
		try {
			ObjectId result = createDocumentByPathFromContent(
					lookupMessageProcessor("create-document-by-path-from-content-content-ref"),
					(String) testObjects.get("folderPath"),
					(String) testObjects.get("filename"),
					testObjects,
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"),
					(Boolean) testObjects.get("force"));

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
	public void testCreateDocumentByPathFromContent_no_properties() {
		testObjects.put("folderPath", "/");
		try {
			ObjectId result = createDocumentByPathFromContent(
					lookupMessageProcessor("create-document-by-path-from-content-no-properties"),
					(String) testObjects.get("folderPath"),
					(String) testObjects.get("filename"),
					(String) testObjects.get("contentRef"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"),
					(Boolean) testObjects.get("force"));

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
			delete(objectId, true);

			Folder folder = (Folder) testObjects.get("parentFolder");
			if (folder != null && TEST_FOLDER_NAME.equals(folder.getName())) {
				delete(folder.getId(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
