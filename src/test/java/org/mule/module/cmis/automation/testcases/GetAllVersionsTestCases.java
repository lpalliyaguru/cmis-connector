/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetAllVersionsTestCases extends CMISTestParent {

    private String objectId;
    private String folderId;
    private List<HashMap<String, Object>> versions;

    protected void checkIn(String checkInContent, String checkInComment, String major) throws Exception {
        upsertOnTestRunMessage("checkInContent", checkInContent);
        upsertOnTestRunMessage("checkInComment", checkInComment);
        upsertOnTestRunMessage("major", major);
        objectId = ((ObjectId) runFlowAndGetPayload("check-in")).getId();
    }

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAllVersionsTestData");

        folderId = createFolderAndUpsertFolderIdOnTestRunMessage();
        objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();

        versions = getTestRunMessageValue("versions");
        for (HashMap<String, Object> version : versions) {
            upsertOnTestRunMessage("documentId", objectId);

            checkOut(objectId);

            checkIn(version.get("contentRef").toString(),
                    version.get("checkinComment").toString(),
                    version.get("major").toString());
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetAllVersions() {
        try {
            List<Document> documentVersions = runFlowAndGetPayload("get-all-versions");

            // Assert that there are the same number of versions as was inserted.
            // Updated versions + original version
            // version updates + 1
            assertTrue(documentVersions.size() == versions.size() + 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteTree(folderId, true, true);
    }


}
