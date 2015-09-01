/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.module.cmis.model.VersioningState;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CreateDocumentByPathTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("createDocumentByPathTestData");
        folderObjectId = getFolderObjectId();
    }

    @Test
    public void testCreateDocumentByPath_rootPath() {
        try {
            ObjectId document = getConnector().createDocumentByPath("/", (String) testData.get("filename"), testData.get("contentRef"),
                    (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"),
                    (Map<String, Object>) testData.get("propertiesRef"), (Boolean) testData.get("force"));
            assertNotNull(document.getId());

            documentObjectId = getConnector().getObjectById(document.getId());
            assertNotNull(documentObjectId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    public void testCreateDocumentByPath_nonRootPath() {
        try {
            ObjectId document = getConnector().createDocumentByPath("/" + testData.get("folderName"), (String) testData.get("filename"), testData.get("contentRef"),
                    (String) testData.get("mimeType"), (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"),
                    null, (Boolean) testData.get("force"));
            assertNotNull(document.getId());

            documentObjectId = getConnector().getObjectById(document.getId());
            assertNotNull(documentObjectId);

            List<Folder> folders = getConnector().getParentFolders(null, documentObjectId.getId());
            assertTrue(folders.size() == 1);
            assertEquals(testData.get("folderName"), folders.get(0).getName());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }


}
