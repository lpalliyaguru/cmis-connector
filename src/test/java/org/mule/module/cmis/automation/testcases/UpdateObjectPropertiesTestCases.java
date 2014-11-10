/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpdateObjectPropertiesTestCases extends CMISTestParent {

    private String objectId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("updateObjectPropertiesTestData");
        upsertOnTestRunMessage("folderId", getRootFolderId());

        ObjectId documentObjectId = runFlowAndGetPayload("create-document-by-id");
        upsertOnTestRunMessage("documentObjectId", documentObjectId);
        objectId = documentObjectId.getId();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", getObjectById(objectId));
    }

    @Category({RegressionTests.class})
    @Test
    public void testUpdateObjectProperties() {
        try {
            Map<String, Object> updatedProperties = getTestRunMessageValue("propertiesRefUpdated");
            upsertOnTestRunMessage("propertiesRef", updatedProperties);

            String titleRenamed = (String) updatedProperties.get("cmis:name");

            CmisObject cmisObject = runFlowAndGetPayload("update-object-properties");
            assertTrue(titleRenamed.equals(cmisObject.getName()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteObject(getTestRunMessageValue("objectId").toString(), true);
    }

}
