/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.*;

public class MoveObjectTestCases extends CMISTestParent {

    private String aFolderId;
    private String documentId;
    private CmisObject cmisObject;


    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("moveObjectTestData");
        String rootFolderId = getRootFolderId();

        // document is created on the root folder
        upsertOnTestRunMessage("folderId", rootFolderId);
        documentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();

        upsertOnTestRunMessage("parentObjectId", rootFolderId);
        aFolderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
        upsertOnTestRunMessage("objectId", aFolderId);

        cmisObject = runFlowAndGetPayload("get-object-by-id");

        upsertOnTestRunMessage("sourceFolderId", rootFolderId);
        upsertOnTestRunMessage("targetFolderId", aFolderId);
        upsertOnTestRunMessage("objectId", documentId);

    }

    @Category({RegressionTests.class})
    @Test
    public void testMoveObject() {
        try {
            FileableCmisObject result = runFlowAndGetPayload("move-object");
            assertNotNull(result);
            assertEquals(result.getParents().size(), 1);
            assertEquals(result.getParents().get(0).getName(), cmisObject.getName());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteObject(documentId, true);
        deleteObject(aFolderId, true);
    }

}
