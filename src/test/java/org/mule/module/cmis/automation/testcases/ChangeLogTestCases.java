/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.ChangeEvent;
import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.ChangeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;

public class ChangeLogTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("changelog");

			RepositoryInfo repositoryInfo = getRepositoryInfo();
			String changeLogToken = repositoryInfo.getLatestChangeLogToken();
			
			testObjects.put("changeLogToken", changeLogToken);
			
			String folderName = (String) testObjects.get("folderName");
			ObjectId objectId = createFolder(folderName, rootFolderId());
			testObjects.put("objectId", objectId.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testChangelog() {
		try {
			String objectId = (String) testObjects.get("objectId");
			
			MessageProcessor flow = lookupMessageProcessor("changelog");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			ChangeEvents changeEvents = (ChangeEvents) response.getMessage().getPayload();

			List<ChangeEvent> events = changeEvents.getChangeEvents();
			
			boolean foundEvent = false;
			for (ChangeEvent event : events) {
				if (event.getChangeType().equals(ChangeType.CREATED) && event.getObjectId().equals(objectId)) {
					foundEvent = true;
					break;
				}
			}
			assertTrue(foundEvent);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(objectId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
