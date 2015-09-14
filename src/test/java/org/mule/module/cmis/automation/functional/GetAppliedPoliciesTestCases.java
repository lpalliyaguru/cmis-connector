/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Policy;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Ignore("Alfresco Public Server does not support policy controllable objects.")
public class GetAppliedPoliciesTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getAppliedPoliciesTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());

        getConnector().applyPolicy(null, documentObjectId.getId(), (List<ObjectId>) testData.get("policyIdsRef"));
    }

    @Test
    public void testGetAppliedPolicies() {
        try {
            List<Policy> policies = getConnector().getAppliedPolicies(null, documentObjectId.getId());
            assertTrue(policies.size() == ((List<ObjectId>) testData.get("policyIdsRef")).size());

            for (Policy policy : policies) {
                boolean contains = false;
                for (ObjectId policyId : (List<ObjectId>) testData.get("policyIdsRef")) {
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
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }

}
