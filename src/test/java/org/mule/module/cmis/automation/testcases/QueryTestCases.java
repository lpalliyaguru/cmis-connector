/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.streaming.ConsumerIterator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QueryTestCases extends CMISTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("queryTestData");
    }

    @Category({RegressionTests.class})
    @Test
    public void testQuery() {
        try {
            List<QueryResult> results = new ArrayList<QueryResult>();
            final ConsumerIterator<QueryResult> list = runFlowAndGetPayload("query");
            while (list.hasNext()) {
                results.add(list.next());
            }
            assertEquals(list.size(), results.size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
