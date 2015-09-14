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
import org.mule.module.cmis.model.VersioningState;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateDocumentByIdTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("createDocumentByIdTestData");
        folderObjectId = getFolderObjectId();
    }

    @Test
    public void testCreateDocumentById() {
        try {
            ObjectId documentId = getConnector().createDocumentById(folderObjectId.getId(), (String) testData.get("filename"),
                    testData.get("contentRef"), (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"),
                    (String) testData.get("objectType"), (Map<String, Object>) testData.get("propertiesRef"));
            assertNotNull(documentId.getId());
            CmisObject cmisObject = getConnector().getObjectById(documentId.getId());
            assertNotNull(cmisObject);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    public void testCreateDocumentById_no_properties() {
        try {
            ObjectId documentId = getConnector().createDocumentById(folderObjectId.getId(), (String) testData.get("filename"), testData.get("contentRef"), (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"), null);
            assertNotNull(documentId.getId());
            CmisObject cmisObject = getConnector().getObjectById(documentId.getId());
            assertNotNull(cmisObject);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
