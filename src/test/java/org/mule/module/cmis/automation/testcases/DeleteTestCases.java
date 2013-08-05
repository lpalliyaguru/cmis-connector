package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeleteTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("delete");
			testObjects.put("parentObjectId", rootFolderId());
			ObjectId result = createFolder((String) testObjects.get("folderName"), (String) testObjects.get("parentObjectId"));
			
			testObjects.put("objectId", result.getId());
			testObjects.put("cmisObject", getObjectById(result.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDelete() {
		try {
			Object result = (Object) delete((CmisObject) testObjects.get("cmisObject"), (String) testObjects.get("objectId"), (Boolean) testObjects.get("allVersions"));
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
