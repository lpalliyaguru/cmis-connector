package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class FolderTestCases extends CMISTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("folder");
			
			List<String> subFolders = (List<String>) testObjects.get("subfolders");
			List<ObjectId> subFolderIds = new ArrayList<ObjectId>();
			
			String parentFolderId = rootFolderId();
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
	
	@Category({RegressionTests.class})
	@Test
	public void testFolder() {
		try {
			List<ObjectId> subfolderIds = (List<ObjectId>) testObjects.get("subFolderIds");
			
			MessageProcessor flow = lookupFlowConstruct("folder");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			CmisObject subFolders = (CmisObject) response.getMessage().getPayload();
			System.out.println(subFolders);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			List<ObjectId> subFolderIds = (List<ObjectId>) testObjects.get("subFolderIds");
			for (ObjectId subFolderId : subFolderIds) {
				String id = subFolderId.getId();
				delete(getObjectById(id), id, true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
