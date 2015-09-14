/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CancelCheckOutTestCases extends AbstractTestCases {

    private ObjectId documentObjectId;
    private ObjectId checkOutId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("cancelCheckOutTestData");
        documentObjectId = getDocumentObjectId(getRootFolderId());
        checkOutId = getConnector().checkOut(null, documentObjectId.getId());
    }

    @Test
    public void testCancelCheckOut() {
        try {
            getConnector().cancelCheckOut(null, checkOutId.getId());
            ItemIterable<Document> checkedOutDocs = getConnector().getCheckoutDocs(null, null);
            for (Document doc : checkedOutDocs) {
                assertFalse(doc.getId().equals(checkOutId.getId()));
            }
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().delete(null, documentObjectId.getId(), true);
    }
}
