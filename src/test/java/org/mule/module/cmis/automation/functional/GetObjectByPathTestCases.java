/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.*;

public class GetObjectByPathTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;
    private String path;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getObjectByPathTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());
        path = "/" + testData.get("folderName") + "/" + testData.get("filename");

    }

    @Test
    public void testGetObjectByPath() {
        try {
            CmisObject cmisObj = getConnector().getObjectByPath(path);
            assertNotNull(cmisObj);
            assertEquals(testData.get("filename"), cmisObj.getName());
            assertEquals(documentObjectId.getId(), cmisObj.getId());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
