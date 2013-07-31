package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class GetObjectByIdTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getObjectById");
			String rootFolderId = rootFolderId();
			testObjects.put("parentObjectId", rootFolderId);
			ObjectId createFolderResult = createFolder((String) testObjects.get("folderName"), rootFolderId);
			
			testObjects.put("objectId", createFolderResult.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetObjectById() {
		try {
			CmisObject result = getObjectById((String) testObjects.get("objectId"));
			assertNotNull(result);
			testObjects.put("cmisObject", result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			delete((CmisObject) testObjects.get("cmisObject"), (String) testObjects.get("objectId"), true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
