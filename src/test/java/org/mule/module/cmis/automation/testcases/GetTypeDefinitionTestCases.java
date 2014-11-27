/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GetTypeDefinitionTestCases extends CMISTestParent {

    @Category({RegressionTests.class})
    @Test
    public void testGetTypeDefinition() {
        initializeTestRunMessage("getTypeDefinitionTestData");
        try {
            ObjectType objType = runFlowAndGetPayload("get-type-definition");
            assertNotNull(objType);
            assertEquals(objType.getId(), getTestRunMessageValue("typeId"));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
