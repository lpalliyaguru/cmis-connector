/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.VersioningState;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;

public class GetContentStreamTestCases extends CMISTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("getContentStream");
			ObjectId result = createDocumentById(rootFolderId(),
					(String) testObjects.get("filename"),
					(String) testObjects.get("content"),
					(String) testObjects.get("mimeType"),
					(VersioningState) testObjects.get("versioningState"),
					(String) testObjects.get("objectType"),
					(Map<String, Object>) testObjects.get("propertiesRef"));

			testObjects.put("objectId", result.getId());
			testObjects.put("cmisObjectRef", getObjectById(result.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ RegressionTests.class })
	@Test
	public void testGetContentStream() {
		try {
			String objectId = (String) testObjects.get("objectId");
			ContentStream result = getContentStream(objectId);
			assertNotNull(result);

			String content = IOUtils.toString(result.getStream());
			assertEquals((String) testObjects.get("content"), content);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetContentStream_With_CmisObject() {
		try {
			CmisObject cmisObject = (CmisObject) testObjects.get("cmisObjectRef");
			ContentStream result = getContentStream(cmisObject);
			assertNotNull(result);

			String content = IOUtils.toString(result.getStream());
			assertEquals((String) testObjects.get("content"), content);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(objectId, true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
