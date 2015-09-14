/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.streaming.PagingConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class QueryTestCases extends AbstractTestCases {

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("queryTestData");
    }

    @Test
    public void testQuery() {
        try {
            List<QueryResult> results = new ArrayList<QueryResult>();
            Object[] args = new Object[]{(String) testData.get("statement"), (Boolean) testData.get("searchAllVersions"), null, null, new PagingConfiguration(Integer.parseInt((String) testData.get("pageSize")))};
            final Collection<QueryResult> resultCollection = (Collection<QueryResult>) getDispatcher().runPaginatedMethod("query", args);
            final Iterator<QueryResult> resultIterator = resultCollection.iterator();
            while (resultIterator.hasNext()) {
                results.add(resultIterator.next());
            }
            assertEquals(resultCollection.size(), results.size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        } catch (Throwable throwable) {
        }
    }
}
