/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class DeleteTreeTestCases extends CMISTestParent {

    private String folderId;
    private String documentId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("deleteTreeTestData");
        upsertOnTestRunMessage("parentObjectId", getRootFolderId());

        folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();

        upsertOnTestRunMessage("folderId", folderId);
        documentId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();

        upsertOnTestRunMessage("folderRef", getObjectById(folderId));

    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testDeleteTree() {
        List<String> objectsFailedToDelete = null;
        try {
            objectsFailedToDelete = runFlowAndGetPayload("delete-tree");
            assertNotNull(objectsFailedToDelete);
            assertEquals(0, objectsFailedToDelete.size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
        try {
            getObjectById(documentId);
            fail("Object should not have been found");
        } catch (Exception e) {
            if (!(e.getCause() instanceof CmisObjectNotFoundException)) {
                fail(ConnectorTestUtils.getStackTrace(e));
            }
        }

    }
}
