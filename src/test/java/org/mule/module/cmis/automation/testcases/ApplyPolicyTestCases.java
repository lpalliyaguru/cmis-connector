/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.fail;

@Ignore("Alfresco Public Server does not support policy controllable objects.")
public class ApplyPolicyTestCases extends CMISTestParent {

    private String objectId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("applyPolicyTestData");

        upsertOnTestRunMessage("folderId", getRootFolderId());
        objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", getObjectById(objectId));

    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testApplyPolicy() {
        try {
            runFlowAndGetPayload("apply-policy");

            fail("Perform assertions once exception is fixed");
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteObject(objectId, true);

    }
}
