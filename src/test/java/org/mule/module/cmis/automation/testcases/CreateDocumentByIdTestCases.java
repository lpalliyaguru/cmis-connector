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
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateDocumentByIdTestCases extends CMISTestParent {

    private String folderId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("createDocumentByIdTestData");
        upsertOnTestRunMessage("parentObjectId", getRootFolderId());
        folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
        upsertOnTestRunMessage("folderId", folderId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testCreateDocumentById() {
        try {
            ObjectId result = runFlowAndGetPayload("create-document-by-id");
            assertNotNull(result.getId());
            assertNotNull(getObjectById(result.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testCreateDocumentById_no_properties() {
        upsertOnTestRunMessage("propertiesRef", null);
        upsertOnTestRunMessage("contentRef", "this is the file content");
        try {
            ObjectId result = runFlowAndGetPayload("create-document-by-id");
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
