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
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.runtime.util.AbstractIterator;
import org.apache.chemistry.opencmis.client.runtime.util.CollectionIterable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class FolderTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("folder");
			
			List<String> subFolders = (List<String>) testObjects.get("subfolders");
			List<ObjectId> subFolderIds = new ArrayList<ObjectId>();
			
			String rootFolderId = rootFolderId();
			String parentFolderId = createFolder((String) testObjects.get("parentFolder"), rootFolderId).getId();
			
			testObjects.put("folderId", parentFolderId);
			
			for (String subFolder : subFolders) {
				ObjectId subFolderId = createFolder(subFolder, parentFolderId);
				subFolderIds.add(subFolderId);
			}
			
			testObjects.put("subFolderIds", subFolderIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testFolder() {
		try {
			MessageProcessor flow = lookupMessageProcessor("folder");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			CollectionIterable<CmisObject> cmisObjs = (CollectionIterable<CmisObject>) response.getMessage().getPayload();
			
			AbstractIterator<CmisObject> ai = cmisObjs.iterator();
			
			List<String> cmisObjsNames = new ArrayList<String>();
			
			while(ai.hasNext()) {
				CmisObject cmisObj = ai.next();
				cmisObjsNames.add(cmisObj.getName());
			}
			
			assertEquals(2, cmisObjsNames.size());
			
			List<String> subFolders = (List<String>) testObjects.get("subfolders");
			
			assertTrue(cmisObjsNames.contains(subFolders.get(0)));
			assertTrue(cmisObjsNames.contains(subFolders.get(1)));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			deleteTree(null, (String) testObjects.get("folderId"), true, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
