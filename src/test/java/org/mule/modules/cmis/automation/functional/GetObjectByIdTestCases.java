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
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetObjectByIdTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getObjectByIdTestData");
        folderObjectId = getFolderObjectId();
    }

    @Test
    public void testGetObjectById() {
        try {
            CmisObject cmisObject = getConnector().getObjectById(folderObjectId.getId());
            assertNotNull(cmisObject.getId());
            assertEquals(cmisObject.getId(), folderObjectId.getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, folderObjectId.getId(), true);
    }
}
