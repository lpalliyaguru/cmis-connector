/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.fail;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;

public class DeleteTestCases extends CMISTestParent {
	
	private String folderId;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("deleteTestData");
		upsertOnTestRunMessage("parentObjectId", getRootFolderId());
		
		folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
		
		upsertOnTestRunMessage("objectId", folderId);
		upsertOnTestRunMessage("cmisObjectRef", getObjectById(folderId));

	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDelete_with_objectId() {
		upsertOnTestRunMessage("cmisObjectRef", null);
		try {
			runFlowAndGetPayload("delete");				
			getObjectById(folderId);
			fail("Object should not have been found");
		} catch (Exception e) {
			if (!(e.getCause() instanceof CmisObjectNotFoundException)) {
				e.printStackTrace();
				fail();
			}
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDelete_with_cmisObjectRef() {
		upsertOnTestRunMessage("objectId", null);
		try {
			runFlowAndGetPayload("delete");
			getObjectById(folderId);
			fail("Object should not have been found");
		} catch (Exception e) {
			if (!(e.getCause() instanceof CmisObjectNotFoundException)) {
				e.printStackTrace();
				fail();
			}
		}
	}
}
