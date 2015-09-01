/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CheckOutTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("checkOutTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());
    }

    @Test
    public void testCheckOut() {
        try {
            boolean found = false;
            CmisObject cmisObject = getConnector().getObjectById(documentObjectId.getId());

            ObjectId pwcObjectId = getConnector().checkOut(cmisObject, documentObjectId.getId());
            assertTrue(StringUtils.isNotEmpty(pwcObjectId.getId()));
            ItemIterable<Document> checkedOutDocs = getConnector().getCheckoutDocs(null, null);
            for (Document doc : checkedOutDocs) {
                if (doc.getId().equals(pwcObjectId.getId())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
            documentObjectId = pwcObjectId;
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().cancelCheckOut(null, documentObjectId.getId());
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
