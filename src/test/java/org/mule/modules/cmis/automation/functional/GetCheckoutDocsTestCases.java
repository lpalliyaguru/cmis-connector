/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cmis.model.VersioningState;

public class GetCheckoutDocsTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    private List<String> documentObjectIds;
    private List<String> checkoutObjectIds;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getCheckoutDocsTestData");
        documentObjectIds = new ArrayList<String>();
        checkoutObjectIds = new ArrayList<String>();
        folderObjectId = getFolderObjectId();

        List<HashMap<String, Object>> documents = (List<HashMap<String, Object>>) testData.get("docs");

        for (HashMap<String, Object> document : documents) {
            String objectId = (getConnector().createDocumentById(folderObjectId.getId(), (String) document.get("filename"), document.get("contentRef"),
                    (String) document.get("mimeType"), (VersioningState) document.get("versioningState"), (String) document.get("objectType"),
                    (Map<String, Object>) document.get("propertiesRef"))).getId();
            documentObjectIds.add(objectId);

            // Check out the document
            ObjectId pwcObjectId = getConnector().checkOut(null, objectId);
            checkoutObjectIds.add(pwcObjectId.getId());
        }

        // Wait for Alfresco to index the checked out documents
        Thread.sleep(GET_CHECKOUT_DOCS_DELAY);
    }

    @Test
    public void testGetCheckoutDocs() {
        List<String> checkedOutDocumentsIds = new ArrayList<String>();
        try {
            ItemIterable<Document> checkedOutDocuments = getConnector().getCheckoutDocs(null, null);
            for (Document doc : checkedOutDocuments) {
                checkedOutDocumentsIds.add(doc.getId());
            }
            assertTrue(checkedOutDocumentsIds.containsAll(checkoutObjectIds));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        for (String objectId : checkoutObjectIds) {
            getConnector().cancelCheckOut(null, objectId);
        }
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
