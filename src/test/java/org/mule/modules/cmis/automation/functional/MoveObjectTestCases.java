/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoveObjectTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;
    private CmisObject folderObjectRef;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("moveObjectTestData");
        // document is created on the root folder
        documentObjectId = getDocumentObjectId(getRootFolderId());
        folderObjectId = getFolderObjectId();
        folderObjectRef = getConnector().getObjectById(folderObjectId.getId());
    }

    @Test
    public void testMoveObject() {
        try {
            FileableCmisObject result = getConnector().moveObject(null, documentObjectId.getId(), getRootFolderId(), folderObjectId.getId());
            assertNotNull(result);
            assertEquals(result.getParents().size(), 1);
            assertEquals(result.getParents().get(0).getName(), folderObjectRef.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);
        getConnector().delete(null, folderObjectId.getId(), true);
    }

}
