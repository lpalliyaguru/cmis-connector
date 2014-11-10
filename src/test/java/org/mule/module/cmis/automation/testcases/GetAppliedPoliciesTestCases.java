/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Policy;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Ignore("Alfresco Public Server does not support policy controllable objects.")
public class GetAppliedPoliciesTestCases extends CMISTestParent {

    private String objectId;
    private List<ObjectId> policyIds;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAppliedPoliciesTestData");
        upsertOnTestRunMessage("folderId", getRootFolderId());

        objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", getObjectById(objectId));

        runFlowAndGetPayload("apply-policy");
        policyIds = getTestRunMessageValue("policyIdsRef");

    }


    @Category({RegressionTests.class})
    @Test
    public void testGetAppliedPolicies() {
        try {
            List<Policy> policies = runFlowAndGetPayload("get-applied-policies");
            assertTrue(policies.size() == policyIds.size());

            for (Policy policy : policies) {
                boolean contains = false;
                for (ObjectId policyId : policyIds) {
                    if (policyId.getId().equals(policy.getId())) {
                        contains = true;
                        break;
                    }
                }
                assertTrue(contains);
            }
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteObject(objectId, true);
    }

}
