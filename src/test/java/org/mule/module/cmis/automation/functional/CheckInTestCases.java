/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Map;

import static org.junit.Assert.*;

public class CheckInTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId checkOutId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("checkInTestData");
        folderObjectId = getFolderObjectId();
        ObjectId documentObjectId = getDocumentObjectId(folderObjectId.getId());
        checkOutId = getConnector().checkOut(null, documentObjectId.getId());
        // Wait for Alfresco to index the checked out documents
        Thread.sleep(GET_CHECKOUT_DOCS_DELAY);
    }

    @Test
    public void testCheckIn() {
        boolean found = false;
        try {
            ObjectId checkedInId = getConnector().checkIn(null, checkOutId.getId(), testData.get("contentRef"), (String) testData.get("filename"),
                    (String) testData.get("mimeType"), (Boolean) testData.get("major"), (String) testData.get("checkinComment"),
                    (Map<String, Object>) testData.get("propertiesRef"));
            assertTrue(checkedInId != null && !checkedInId.getId().isEmpty() && !checkedInId.getId().trim().isEmpty());
            // Wait for Alfresco to index the checked in documents
            Thread.sleep(GET_CHECKOUT_DOCS_DELAY);

            ItemIterable<Document> checkedOutDocuments = getConnector().getCheckoutDocs(null, null);
            for (Document doc : checkedOutDocuments) {
                try {
                    if (doc != null && doc.isPrivateWorkingCopy()) {
                        found = true;
                    }
                } catch (NullPointerException npe) {
                    // If no checked out document found instead of false, isPrivateWorkingCopy throws a NullPointerException
                }
            }
            assertFalse(found);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }

}
