/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.*;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;

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
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidRepositoryInfo() {
        try {
            testData.put("repositoryId", TestDataBuilder.generateRandomShortString());
            getConnector().repositoryInfo();
        } catch (Exception e) {
            final Throwable cause = e.getCause().getCause();
            if (cause instanceof CmisObjectNotFoundException) {
                assertTrue(cause.getMessage().contains("Unknown repository"));
            } else {
                fail(e.getMessage());
            }
        }
    }
}
