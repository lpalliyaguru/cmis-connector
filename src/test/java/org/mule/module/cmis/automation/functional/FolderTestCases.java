/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.runtime.util.AbstractIterator;
import org.apache.chemistry.opencmis.client.runtime.util.CollectionIterable;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.module.cmis.model.NavigationOptions;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FolderTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;

    private List<String> subFoldersIds = new ArrayList<String>();
    private List<String> subFoldersNames;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("folderTestData");
        subFoldersNames = (List<String>) testData.get("subfolders");
        folderObjectId = getFolderObjectId();

        for (String subFolder : subFoldersNames) {
            ObjectId subFolderId = getConnector().createFolder(subFolder, folderObjectId.getId());
            subFoldersIds.add(subFolderId.getId());
        }
    }

    @Test
    public void testFolder() {
        List<String> cmisObjsNames = new ArrayList<String>();
        List<String> cmisObjsIds = new ArrayList<String>();

        try {
            CollectionIterable<CmisObject> cmisObjs = (CollectionIterable<CmisObject>) getConnector().folder(null, folderObjectId.getId(), NavigationOptions.CHILDREN, 100, null,
                    null);
            AbstractIterator<CmisObject> ai = cmisObjs.iterator();

            while (ai.hasNext()) {
                CmisObject cmisObj = ai.next();
                cmisObjsNames.add(cmisObj.getName());
                cmisObjsIds.add(cmisObj.getId());
            }

            assertTrue(cmisObjsNames.containsAll(subFoldersNames));
            assertTrue(cmisObjsIds.containsAll(subFoldersIds));
            assertEquals(cmisObjsNames.size(), subFoldersNames.size());

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
