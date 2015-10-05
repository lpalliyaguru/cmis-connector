/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.module.cmis.model.VersioningState;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CreateRelationshipTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;
    private ObjectId anotherDocumentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("createRelationshipTestData");

        folderObjectId = getFolderObjectId();
        documentObjectId = getConnector().createDocumentById(folderObjectId.getId(), (String) testData.get("aFileName"), testData.get("contentRef"),
                (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"),
                (Map<String, Object>) testData.get("propertiesRef"));

        anotherDocumentObjectId = getConnector().createDocumentById(folderObjectId.getId(), (String) testData.get("anotherFileName"), testData.get("contentRef"),
                (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"),
                (Map<String, Object>) testData.get("propertiesRef"));
    }

    @Test
    public void testCreateRelationship() {
        try {
            boolean found = false;

            CmisObject cmisObject = getConnector().getObjectById(anotherDocumentObjectId.getId());

            List<Relationship> result = getConnector().getObjectRelationships(cmisObject, anotherDocumentObjectId.getId());
            assertTrue(result.isEmpty());

            ObjectId relationshipId = getConnector().createRelationship(documentObjectId.getId(), anotherDocumentObjectId.getId(), (String) testData.get("relationshipType"));
            assertNotNull(relationshipId);

            cmisObject = getConnector().getObjectById(anotherDocumentObjectId.getId());
            result = getConnector().getObjectRelationships(cmisObject, anotherDocumentObjectId.getId());
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
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
