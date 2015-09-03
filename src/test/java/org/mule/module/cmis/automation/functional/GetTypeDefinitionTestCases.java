/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.*;

public class GetTypeDefinitionTestCases extends AbstractTestCases {

    @Test
    public void testGetTypeDefinition() {
        testData = TestDataBuilder.getTestData("getTypeDefinitionTestData");
        try {
            ObjectType objType = getConnector().getTypeDefinition((String) testData.get("typeId"));
            assertNotNull(objType);
            assertEquals(objType.getId(), testData.get("typeId"));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
