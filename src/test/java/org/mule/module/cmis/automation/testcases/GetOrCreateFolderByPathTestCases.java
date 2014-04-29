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

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetOrCreateFolderByPathTestCases extends CMISTestParent {
	
	private String folderId;
	
	@Before
	public void setUp() throws Exception {
			initializeTestRunMessage("getOrCreateFolderByPathTestData");
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetOrCreateFolderByPath() {
		CmisObject cmisObj = null;
		try {
			cmisObj = runFlowAndGetPayload("get-or-create-folder-by-path");
			assertNotNull(cmisObj.getId());
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
		folderId = cmisObj.getId();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteTree(folderId, true, true);
	}
}
