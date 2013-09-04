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

import java.util.HashMap;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GetAclTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getAcl");
			testObjects.put("parentObjectId", rootFolderId());
			ObjectId result = createFolder((String) testObjects.get("folderName"), (String) testObjects.get("parentObjectId"));
			
			testObjects.put("objectId", result.getId());
			testObjects.put("cmisObjectRef", getObjectById(result.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetAcl() {
		try {
			Acl result = getAcl((CmisObject) testObjects.get("cmisObjectRef"), (String) testObjects.get("objectId"));
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetAcl_null_cmisObject() {
		try {
			Acl result = getAcl(null, (String) testObjects.get("objectId"));
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}				
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetAcl_with_cmisObjectRef() {
		try {
			Acl result = getAcl(lookupFlowConstruct("get-acl-with-cmis-object-ref"), testObjects, (String) testObjects.get("objectId"));
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			delete((CmisObject) testObjects.get("cmisObjectRef"), (String) testObjects.get("objectId"), true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
