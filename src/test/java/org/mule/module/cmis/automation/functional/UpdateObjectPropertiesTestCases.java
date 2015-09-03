/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UpdateObjectPropertiesTestCases extends AbstractTestCases {

    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("updateObjectPropertiesTestData");
        documentObjectId = getDocumentObjectId(getRootFolderId());
    }

    @Test
    public void testUpdateObjectProperties() {
        try {
            Map<String, Object> updatedProperties = (Map<String, Object>) testData.get("propertiesRefUpdated");
            String titleRenamed = (String) updatedProperties.get("cmis:name");

            CmisObject cmisObject = getConnector().updateObjectProperties(null, documentObjectId.getId(), updatedProperties);
            assertTrue(titleRenamed.equals(cmisObject.getName()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);
    }

}
