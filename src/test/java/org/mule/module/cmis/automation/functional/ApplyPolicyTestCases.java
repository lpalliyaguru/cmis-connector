/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@Ignore("Alfresco Public Server does not support policy controllable objects.")
public class ApplyPolicyTestCases extends AbstractTestCases {

    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("applyPolicyTestData");
        documentObjectId = getDocumentObjectId(getRootFolderId());
    }

    @Test
    public void testApplyPolicy() {
        try {
            CmisObject cmisObject = getConnector().getObjectById(documentObjectId.getId());

            getConnector().applyPolicy(cmisObject, documentObjectId.getId(), (List<ObjectId>) testData.get("policyIdsRef"));

            cmisObject = getConnector().getObjectById(documentObjectId.getId());
            assertNotNull(cmisObject);
            fail("Perform assertions once exception is fixed");
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);

    }
}
