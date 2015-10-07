/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplyAspectTestCases extends AbstractTestCases {

    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("applyAspectTestData");
        documentObjectId = getDocumentObjectId(getRootFolderId());
    }

    @Test
    public void testApplyAspect() {
        try {
            String aspectName = (String) testData.get("aspectName");
            getConnector().applyAspect(documentObjectId.getId(), aspectName, (Map<String, Object>) testData.get("propertiesRef"));

            AlfrescoDocument document = (AlfrescoDocument) getConnector().getObjectById(documentObjectId.getId());
            assertTrue(document.hasAspect("P:" + aspectName));

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);
    }
}
