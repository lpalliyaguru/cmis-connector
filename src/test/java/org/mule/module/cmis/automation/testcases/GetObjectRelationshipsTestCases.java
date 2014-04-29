/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetObjectRelationshipsTestCases extends CMISTestParent {
	
	private String folderId;
	private String aDocumentId;
	private String anotherDocumentId;
	private String relationshipId;
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getObjectRelationshipsTestData");
		
		folderId = createFolderAndUpsertFolderIdOnTestRunMessage();
		
		upsertOnTestRunMessage("filename", getTestRunMessageValue("aFileName"));
		aDocumentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
		upsertOnTestRunMessage("parentObjectId", aDocumentId);
		
		upsertOnTestRunMessage("filename", getTestRunMessageValue("anotherFileName"));
		anotherDocumentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
		upsertOnTestRunMessage("childObjectId", anotherDocumentId);
		
		relationshipId = ((ObjectId) runFlowAndGetPayload("create-relationship")).getId();

	}

	@Category({RegressionTests.class})
	@Test
	public void testGetObjectRelationships() {
		boolean found = false;
		try {
			List<Relationship> result = getObjectRelationships(aDocumentId, getObjectById(aDocumentId));
			assertNotNull(result);
			for (Relationship relationship : result) {
				if (relationship.getId().equals(relationshipId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	
	@After
	public void tearDown() throws Exception {
		deleteTree(folderId, true, true);
	}

}
