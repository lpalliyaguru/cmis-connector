/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.fail;

public class DeleteTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("deleteTestData");
        folderObjectId = getFolderObjectId();
    }

    @Test
    public void testDelete_with_objectId() {
        try {
            getConnector().delete(null, folderObjectId.getId(), true);
            getConnector().getObjectById(folderObjectId.getId());

            fail("Object should not have been found");
        } catch (Exception e) {
            if (!(e instanceof CmisObjectNotFoundException)) {
                fail(ConnectorTestUtils.getStackTrace(e));
            }
        }
    }

    @Test
    public void testDelete_with_cmisObjectRef() {
        try {
            CmisObject cmisObject = getConnector().getObjectById(folderObjectId.getId());

            getConnector().delete(cmisObject, null, true);
            getConnector().getObjectById(folderObjectId.getId());
            fail("Object should not have been found");
        } catch (Exception e) {
            if (!(e instanceof CmisObjectNotFoundException)) {
                fail(ConnectorTestUtils.getStackTrace(e));
            }
        }
    }
}
