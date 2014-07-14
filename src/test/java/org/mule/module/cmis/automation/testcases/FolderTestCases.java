/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.runtime.util.AbstractIterator;
import org.apache.chemistry.opencmis.client.runtime.util.CollectionIterable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class FolderTestCases extends CMISTestParent {

	private String parentFolderId;
	private List<String> subFoldersIds = new ArrayList<String>();
	private List<String> subFoldersNames;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("folderTestData");
		subFoldersNames = getTestRunMessageValue("subfolders");

		upsertOnTestRunMessage("parentObjectId", getRootFolderId());
		parentFolderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
		
		upsertOnTestRunMessage("parentObjectId", parentFolderId);
		
		for (String subFolder : subFoldersNames) {
			upsertOnTestRunMessage("folderName", subFolder);
            ObjectId subFolderId = runFlowAndGetPayload("create-folder");
			subFoldersIds.add(subFolderId.getId());
		}
		
		upsertOnTestRunMessage("folderId", parentFolderId);

	}
	
	@After
	public void tearDown() throws Exception {
		deleteTree(parentFolderId, true, true);
	}

	@Category({RegressionTests.class})
	@Test
	public void testFolder() {
		List<String> cmisObjsNames = new ArrayList<String>();
		List<String> cmisObjsIds = new ArrayList<String>();
		
		try {
			CollectionIterable<CmisObject> cmisObjs = runFlowAndGetPayload("folder");
			AbstractIterator<CmisObject> ai = cmisObjs.iterator();

			while(ai.hasNext()) {
				CmisObject cmisObj = ai.next();
				cmisObjsNames.add(cmisObj.getName());
				cmisObjsIds.add(cmisObj.getId());
			}
		
			assertTrue(cmisObjsNames.containsAll(subFoldersNames));
			assertTrue(cmisObjsIds.containsAll(subFoldersIds));
			assertEquals(cmisObjsNames.size(), subFoldersNames.size());

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
