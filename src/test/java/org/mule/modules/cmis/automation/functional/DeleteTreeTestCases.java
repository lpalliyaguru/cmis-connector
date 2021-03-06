/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class DeleteTreeTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("deleteTreeTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());
    }

    @Test
    public void testDeleteTree() {
        List<String> objectsFailedToDelete;
        try {
            objectsFailedToDelete = getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, (Boolean) testData.get("allVersions"),
                    (Boolean) testData.get("continueOnFailure"));
            assertNotNull(objectsFailedToDelete);
            assertEquals(0, objectsFailedToDelete.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            getConnector().getObjectById(documentObjectId.getId());
            fail("Object should not have been found");
        } catch (Exception e) {
            if (!(e instanceof CmisObjectNotFoundException)) {
                fail(e.getMessage());
            }
        }

    }
}
