/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class RepositoryInfoTestCases extends CMISTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("repositoryInfoTestData");
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testRepositoryInfo() {
        try {
            RepositoryInfo repositoryInfo = runFlowAndGetPayload("repository-info");
            assertEquals(repositoryInfo.getId(), getTestRunMessageValue("repositoryId"));
            assertEquals(repositoryInfo.getName(), getTestRunMessageValue("repositoryName"));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testInvalidRepositoryInfo() {
        try {
            upsertOnTestRunMessage("repositoryId", ConnectorTestUtils.generateRandomShortString());
            runFlowAndGetPayload("repository-info");
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
