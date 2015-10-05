/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetAllVersionsTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getAllVersionsTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());
        String docId = documentObjectId.getId();
        for (HashMap<String, Object> version : (List<HashMap<String, Object>>) testData.get("versions")) {
            CmisObject cmisObject = getConnector().getObjectById(docId);

            String checkOutId = getConnector().checkOut(cmisObject, null).getId();
            docId = (getConnector().checkIn(null, checkOutId, version.get("contentRef"), (String) testData.get("filename"), (String) testData.get("mimeType"),
                    (Boolean) version.get("major"), (String) version.get("checkinComment"), (Map<String, Object>) testData.get("propertiesRef"))).getId();
        }
    }

    @Test
    public void testGetAllVersions() {
        try {
            List<Document> documentVersions = getConnector().getAllVersions(null, documentObjectId.getId(), null, null);

            // Assert that there are the same number of versions as was inserted.
            // Updated versions + original version
            // version updates + 1
            assertTrue(documentVersions.size() == ((List<Map<String, Object>>) testData.get("versions")).size() + 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
