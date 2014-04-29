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
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class ChangeLogTestCases extends CMISTestParent {
	
	private String objectId;

	@Before
	public void setUp() throws Exception {
			initializeTestRunMessage("changelogTestData");

			RepositoryInfo repositoryInfo = getRepositoryInfo();
			upsertOnTestRunMessage("changeLogToken", repositoryInfo.getLatestChangeLogToken());
			
			upsertOnTestRunMessage("parentObjectId", getRootFolderId());
			objectId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
			
			upsertOnTestRunMessage("objectId", objectId);
	}

	@Category({RegressionTests.class})
	@Test
	public void testChangelog() {
		try {
			ChangeEvents changeEvents = runFlowAndGetPayload("changelog");
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
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject(objectId, true);

	}
	
}
