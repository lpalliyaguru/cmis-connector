/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateDocumentByIdFromContentTestCases extends CMISTestParent {

    private String folderId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("createDocumentByIdFromContentTestData");
        upsertOnTestRunMessage("parentObjectId", getRootFolderId());
        folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
        upsertOnTestRunMessage("folderId", folderId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testCreateDocumentByIdFromContent() throws Exception {
        try {
            ObjectId result = runFlowAndGetPayload("create-document-by-id-from-content");
            assertNotNull(result.getId());
            assertNotNull(getObjectById(result.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @Category({RegressionTests.class})
    @Test
    public void testCreateDocumentByIdFromContent_no_properties() {
        upsertOnTestRunMessage("propertiesRef", null);
        try {
            ObjectId result = runFlowAndGetPayload("create-document-by-id-from-content");
            assertNotNull(result.getId());
            assertNotNull(getObjectById(result.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteTree(folderId, true, true);
    }
}
