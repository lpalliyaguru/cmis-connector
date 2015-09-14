/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.ChangeEvent;
import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.ChangeType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChangeLogTestCases extends AbstractTestCases {

    private String changeLogToken;
    private ObjectId folderObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("changelogTestData");
        RepositoryInfo repositoryInfo = getConnector().repositoryInfo();
        folderObjectId = getFolderObjectId();
        changeLogToken = repositoryInfo.getLatestChangeLogToken();
    }

    @Test
    public void testChangeLog() {
        try {
            ChangeEvents changeEvents = getConnector().changelog(changeLogToken, (Boolean) testData.get("includeProperties"));
            List<ChangeEvent> events = changeEvents.getChangeEvents();

            boolean foundEvent = false;
            for (ChangeEvent event : events) {
                if (event.getChangeType().equals(ChangeType.CREATED) && folderObjectId.getId().contains(event.getObjectId())) {
                    foundEvent = true;
                    break;
                }
            }
            assertTrue(foundEvent);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }

}
