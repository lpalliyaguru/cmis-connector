/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.Principal;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.modules.tests.ConnectorTestCase;
import org.mule.transport.NullPayload;

import java.util.HashMap;
import java.util.List;

public class CMISTestParent extends ConnectorTestCase {

    protected final int QUERY_DELAY = 20000;
    protected final int GET_CHECKOUT_DOCS_DELAY = 20000;
    // Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);
    private HashMap<String, Object> latestTestRunMessage;


    private void backUpTestRunMessage() {
        latestTestRunMessage = new HashMap<String, Object>();
        for (String key : getTestRunMessageKeySet()) {
            latestTestRunMessage.put(key, getTestRunMessageValue(key));
        }
    }

    private void restoreTestRunMessage() {
        initializeTestRunMessage(latestTestRunMessage);
    }

	/*
     * Helper methods below
	 */

    protected RepositoryInfo getRepositoryInfo() throws Exception {
        return runFlowAndGetPayload("repository-info");
    }

    protected String getRootFolderId() throws Exception {
        return getRepositoryInfo().getRootFolderId();
    }

    protected CmisObject getObjectById(String objectId) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        CmisObject cmisObject = runFlowAndGetPayload("get-object-by-id");
        restoreTestRunMessage();
        return cmisObject;
    }

    protected String createFolderAndUpsertFolderIdOnTestRunMessage()
            throws Exception {
        upsertOnTestRunMessage("parentObjectId", getRootFolderId());
        String folderId = ((ObjectId) runFlowAndGetPayload("create-folder")).getId();
        upsertOnTestRunMessage("folderId", folderId);
        return folderId;
    }


    protected Acl getAcl(String objectId) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        Acl acl = runFlowAndGetPayload("get-acl");
        restoreTestRunMessage();
        return acl;
    }

    protected Acl getAcl(Object cmisObjectRef) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("cmisObjectRef", cmisObjectRef);
        Acl acl = runFlowAndGetPayload("get-acl");
        restoreTestRunMessage();
        return acl;
    }

    protected Principal getPrincipal(Acl acl) {
        Principal principal = null;
        if (acl != null) {
            List<Ace> aces = acl.getAces();
            if (aces != null && aces.size() > 0) {
                principal = aces.get(0).getPrincipal();
            }
        }
        return principal;
    }

    protected void deleteObject(String objectId, boolean allVersions) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("allVersions", allVersions);
        runFlowAndGetPayload("delete");
        restoreTestRunMessage();
    }

    protected ObjectId checkOut(String documentId) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("documentId", documentId);
        ObjectId objectId = runFlowAndGetPayload("check-out");
        restoreTestRunMessage();
        return objectId;
    }

    protected ItemIterable<Document> getCheckedOutDocuments() throws Exception {
        return runFlowAndGetPayload("get-checkout-docs");
    }

    protected List<Folder> getParentFolders(String objectId) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        List<Folder> folderList = runFlowAndGetPayload("get-parent-folders");
        restoreTestRunMessage();
        return folderList;
    }

    protected List<String> deleteTree(String folderId, Boolean allversions, Boolean continueOnFailure) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("folderId", folderId);
        upsertOnTestRunMessage("allversions", allversions);
        upsertOnTestRunMessage("continueOnFailure", continueOnFailure);
        upsertOnTestRunMessage("folderRef", null);
        List<String> stringList = runFlowAndGetPayload("delete-tree");
        restoreTestRunMessage();
        return stringList;
    }

    protected void applyPolicy(String objectId, List<ObjectId> policies) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("policyIdsRef", policies);
        runFlowAndGetPayload("apply-policy");
        restoreTestRunMessage();
    }

    protected void cancelCheckOut(String documentId, CmisObject cmisObjectRef) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("documentId", documentId);
        upsertOnTestRunMessage("cmisObjectRef", cmisObjectRef);
        runFlowAndGetPayload("cancel-check-out");
        restoreTestRunMessage();
    }

    protected ObjectId createRelationship(String parentObjectId, String childObjectId, String relationshipType) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("parentObjectId", parentObjectId);
        upsertOnTestRunMessage("childObjectId", childObjectId);
        upsertOnTestRunMessage("relationshipType", relationshipType);
        ObjectId objectId = runFlowAndGetPayload("create-relationship");
        restoreTestRunMessage();
        return objectId;
    }


    @SuppressWarnings("unchecked")
    protected List<Relationship> getObjectRelationships(CmisObject cmisObjectRef) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", null);
        upsertOnTestRunMessage("cmisObjectRef", cmisObjectRef);
        Object resultPayload = runFlowAndGetPayload("get-object-relationships");
        restoreTestRunMessage();
        if (resultPayload == null || resultPayload instanceof NullPayload) {
            return null;
        } else {
            return (List<Relationship>) resultPayload;
        }
    }

    @SuppressWarnings("unchecked")
    protected List<Relationship> getObjectRelationships(String objectId, CmisObject cmisObjectRef) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", cmisObjectRef);
        Object resultPayload = runFlowAndGetPayload("get-object-relationships");
        restoreTestRunMessage();
        if (resultPayload == null || resultPayload instanceof NullPayload) {
            return null;
        } else {
            return (List<Relationship>) resultPayload;
        }
    }

    protected List<Folder> getParentFolders(String objectId, Object cmisObjectRef) throws Exception {
        backUpTestRunMessage();
        upsertOnTestRunMessage("objectId", objectId);
        upsertOnTestRunMessage("cmisObjectRef", cmisObjectRef);
        List<Folder> folderList = runFlowAndGetPayload("get-parent-folders-with-cmis-object-ref");
        restoreTestRunMessage();
        return folderList;
    }

}
