/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateFolderTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("createFolderTestData");
    }

    @Test
    public void testCreateFolder() {
        try {
            folderObjectId = getConnector().createFolder((String) testData.get("folderName"), getRootFolderId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(folderObjectId.getId());
    }

    @After
    public void tearDown() throws Exception {
        if (folderObjectId != null) {
            getConnector().delete(null, folderObjectId.getId(), true);
        }
    }
}
