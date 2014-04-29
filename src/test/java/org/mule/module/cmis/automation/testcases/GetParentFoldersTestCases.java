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

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetParentFoldersTestCases extends CMISTestParent {

	private String folderId;
	private CmisObject cmisObjectRef;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getParentFoldersTestData");
		upsertOnTestRunMessage("parentObjectId", getRootFolderId());
		folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
		cmisObjectRef = getObjectById(folderId);
		
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testGetParentFolders() {
		try {
			List<Folder> folders = getParentFolders(folderId);
			assertNotNull(folders);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testGetParentFolders_With_CmisObjectRef() {
		try {
			List<Folder> folders = getParentFolders(folderId, cmisObjectRef);
			assertNotNull(folders);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
			deleteObject(folderId, true);

	}
}
