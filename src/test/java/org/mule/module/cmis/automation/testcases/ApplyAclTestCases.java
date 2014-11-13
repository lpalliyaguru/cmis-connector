/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.Principal;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.AccessControlEntryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApplyAclTestCases extends CMISTestParent {

    private String folderId;
    private String objectId;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("applyAclTestData");
        folderId = createFolderAndUpsertFolderIdOnTestRunMessage();
        objectId = ((ObjectId) runFlowAndGetPayload("create-document-by-id")).getId();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", getObjectById(folderId));
    }

    @After
    public void tearDown() throws Exception {
        deleteTree(folderId, true, true);
    }

    @Category({RegressionTests.class})
    @Test
    public void testApplyAclAdd() {
        List<Ace> addAces = new ArrayList<Ace>();
        upsertOnTestRunMessage("cmisObjectRef", null);
        try {
            Acl acl = getAcl(objectId);

            Principal principal = getPrincipal(acl);

            List<String> permissions = new ArrayList<String>();
            permissions.add("cmis:write");
            AccessControlEntryImpl acei = new AccessControlEntryImpl(principal,
                    permissions);
            addAces.add(acei);
            upsertOnTestRunMessage("addAcesRef", addAces);

            Acl result = runFlowAndGetPayload("apply-acl");

            assertEquals(2, result.getAces().size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void testApplyAclRemove() {
        List<Ace> removeAces = new ArrayList<Ace>();
        List<Ace> addAces = new ArrayList<Ace>();
        Acl result;

        upsertOnTestRunMessage("cmisObjectRef", null);

        try {
            Acl acl = getAcl(folderId);
            Principal principal = getPrincipal(acl);

            List<String> firstPermissions = new ArrayList<String>();
            firstPermissions.add("cmis:write");
            AccessControlEntryImpl firstAcei = new AccessControlEntryImpl(principal,
                    firstPermissions);
            addAces.add(firstAcei);
            upsertOnTestRunMessage("addAcesRef", addAces);

            result = runFlowAndGetPayload("apply-acl");

            removeFromTestRunMessage("addAcesRef");

            assertEquals(2, result.getAces().size());

            List<String> secondPermissions = new ArrayList<String>();
            secondPermissions.add("cmis:write");
            AccessControlEntryImpl secondAcei = new AccessControlEntryImpl(
                    principal, secondPermissions);
            removeAces.add(secondAcei);

            upsertOnTestRunMessage("removeAcesRef", removeAces);
            result = runFlowAndGetPayload("apply-acl");

            assertEquals(1, result.getAces().size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void testApplyAclAdd_With_CmisObjectRef() {
        List<Ace> addAces = new ArrayList<Ace>();
        upsertOnTestRunMessage("objectId", null);

        try {

            Acl acl = getAcl(folderId);
            Principal principal = getPrincipal(acl);

            List<String> permissions1 = new ArrayList<String>();
            permissions1.add("cmis:write");
            AccessControlEntryImpl acei = new AccessControlEntryImpl(principal,
                    permissions1);
            addAces.add(acei);

            upsertOnTestRunMessage("addAcesRef", addAces);
            Acl result = runFlowAndGetPayload("apply-acl");

            assertEquals(2, result.getAces().size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}
