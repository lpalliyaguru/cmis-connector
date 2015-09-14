/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class RepositoryInfoTestCases extends AbstractTestCases {

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("repositoryInfoTestData");
    }

    @Test
    public void testRepositoryInfo() {
        try {
            RepositoryInfo repositoryInfo = getConnector().repositoryInfo();
            assertEquals(testData.get("repositoryId"), repositoryInfo.getId());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    public void testInvalidRepositoryInfo() {
        try {
            testData.put("repositoryId", ConnectorTestUtils.generateRandomShortString());
            getConnector().repositoryInfo();
        } catch (Exception e) {
            final Throwable cause = e.getCause().getCause();
            if (cause instanceof CmisObjectNotFoundException) {
                assertThat(cause.getMessage(), containsString("Unknown repository"));
            } else {
                fail(ConnectorTestUtils.getStackTrace(e));
            }
        }
    }
}
