/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.*;

public class GetOrCreateFolderByPathTestCases extends CMISTestParent {

    private String folderId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getOrCreateFolderByPathTestData");
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetOrCreateFolderByPath() {
        CmisObject createCmisObj;
        CmisObject getCmisObj;

        try {
            //Test create the Folder
            createCmisObj = runFlowAndGetPayload("get-or-create-folder-by-path");
            assertNotNull(createCmisObj.getId());

            //Test get the Folder
            getCmisObj = runFlowAndGetPayload("get-or-create-folder-by-path");
            assertEquals(createCmisObj.getId(), getCmisObj.getId());

            folderId = createCmisObj.getId();
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteTree(folderId, true, true);
    }
}
