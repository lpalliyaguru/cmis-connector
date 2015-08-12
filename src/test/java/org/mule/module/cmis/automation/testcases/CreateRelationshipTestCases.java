/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class CreateRelationshipTestCases extends CMISTestParent {

    private String folderId;
    private String aDocumentId;
    private String anotherDocumentId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("createRelationshipTestData");

        upsertOnTestRunMessage("parentObjectId", getRootFolderId());
        folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
        upsertOnTestRunMessage("folderId", folderId);

        upsertOnTestRunMessage("filename", getTestRunMessageValue("aFileName"));
        aDocumentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
        upsertOnTestRunMessage("parentObjectId", aDocumentId);

        upsertOnTestRunMessage("filename", getTestRunMessageValue("anotherFileName"));
        anotherDocumentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
        upsertOnTestRunMessage("childObjectId", anotherDocumentId);

    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testCreateRelationship() {
        try {
            boolean found = false;

            List<Relationship> result = getObjectRelationships(anotherDocumentId, getObjectById(anotherDocumentId));
            assertTrue(result.isEmpty());

            ObjectId relationshipId = runFlowAndGetPayload("create-relationship");
            assertNotNull(relationshipId);

            result = getObjectRelationships(anotherDocumentId, getObjectById(anotherDocumentId));
            assertEquals(1, result.size());

            for (Relationship relationship : result) {
                if (relationship.getId().equals(relationshipId.getId())) {
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
