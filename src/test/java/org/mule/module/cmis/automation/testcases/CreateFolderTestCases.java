/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;

public class CreateFolderTestCases extends CMISTestParent {

	private String objectId;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("createFolderTestData");
		upsertOnTestRunMessage("parentObjectId", getRootFolderId());
	}

	@After
	public void tearDown() throws Exception {
		deleteObject(objectId, true);
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testCreateFolder() {
		ObjectId objectId = null;
		try {
			objectId = runFlowAndGetPayload("create-folder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(objectId.getId());
		this.objectId = objectId.getId();
	}


}
