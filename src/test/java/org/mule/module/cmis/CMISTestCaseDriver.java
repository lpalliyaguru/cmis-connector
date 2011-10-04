/**
 * Mule CMIS Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.cmis;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.lifecycle.InitialisationException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CMISTestCaseDriver {

    private CMISCloudConnector cmis = new CMISCloudConnector();

    @Before
    public void setUpTests() {
        cmis.setUsername("admin");
        cmis.setPassword("admin");
        cmis.setRepositoryId("371554cd-ac06-40ba-98b8-e6b60275cca7");
        cmis.setBaseUrl("http://cmis.alfresco.com/service/cmis");
        cmis.setEndpoint("atompub");
        cmis.initialise();

    }

    @Test(expected = IllegalArgumentException.class)
    public void failWrongId() {
        CmisObject obj = cmis.getObjectByPath("mule-cloud-connector/test");
        String wrongId = "1";
        cmis.getContentStream(obj, wrongId);
    }

    @Test
    public void changeLog() throws InitialisationException {
        ChangeEvents events = cmis.changelog("42215", false);
        assertFalse(events.getHasMoreItems());
        assertTrue(events.getTotalNumItems() > 0);
    }

    @Test
    public void repositories() {
        assertNotNull(cmis.repositories());
    }

    @Test
    public void folderParent() {
        String subFolderId = getObjectId("/mule-cloud-connector/test-folder");
        Folder parent = (Folder) cmis.folder(null, subFolderId, NavigationOptions.PARENT, null, null, null);

        assertEquals("/mule-cloud-connector", parent.getPath());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void folderTree() {
        String folderId = getObjectId("/mule-cloud-connector");
        List<Tree<FileableCmisObject>> tree = (List<Tree<FileableCmisObject>>) cmis.folder(null,
                folderId, NavigationOptions.TREE, 1, null, null);
        assertNotNull(tree);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void folderDescendants() {
        String folderId = getObjectId("/mule-cloud-connector");
        List<Tree<FileableCmisObject>> tree = (List<Tree<FileableCmisObject>>) cmis.folder(null,
                folderId, NavigationOptions.DESCENDANTS, 1, null, null);
        assertNotNull(tree);
    }

    @Test
    public void objectParents() {
        String parent = "/mule-cloud-connector";
        CmisObject obj = cmis.getObjectByPath(parent + "/test");
        List<Folder> parents = cmis.getParentFolders(obj, null);

        boolean present = false;

        for (Folder folder : parents) {
            if (folder.getPath().equals(parent)) {
                present = true;
            }
        }

        assertTrue(present);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void folderContent() {
        String folderId = getObjectId("/mule-cloud-connector");
        ItemIterable<CmisObject> it = (ItemIterable<CmisObject>) cmis.folder(null, folderId,
                NavigationOptions.CHILDREN, null, null, null);
        Assert.assertNotNull(it);
    }

    @Test
    public void checkedOutDocs() {
        ItemIterable<Document> docs = cmis.getCheckoutDocs(null, null);
        assertNotNull(docs);
    }

    @Test
    @Ignore
    public void checkOut() {
        ObjectId id = cmis.checkOut(null, getObjectId("/mule-cloud-connector/test"));
        cmis.cancelCheckOut(null, id.getId());
    }

    @Test
    public void query() {
        ItemIterable<QueryResult> results = cmis.query("SELECT * from cmis:folder ", false, null, null);
        assertNotNull(results);
    }

    @Test
    public void getContentStream() {
        CmisObject object = cmis.getObjectByPath("/mule-cloud-connector/test");
        ContentStream contentStream = cmis.getContentStream(object, null);
        assertEquals("application/octet-stream;charset=UTF-8", contentStream.getMimeType());
    }

    @Test
    public void moveObject() {
        String f1 = getObjectId("/mule-cloud-connector");
        String f2 = getObjectId("/mule-cloud-connector/test-folder");
        FileableCmisObject obj = (FileableCmisObject) cmis.getObjectByPath("/mule-cloud-connector/move-this");
        cmis.moveObject(obj, null, f1, f2);
        assertNotNull(cmis.getObjectByPath("/mule-cloud-connector/test-folder/move-this"));
        cmis.moveObject(obj, null, f2, f1);
    }

    @Test
    public void getAllVersions() {
        String id = getObjectId("/mule-cloud-connector/test");
        assertNotNull(cmis.getAllVersions(null, id, null, null));
    }

    @Test
    @Ignore
    public void checkout() {
        String id = getObjectId("/mule-cloud-connector/test");
        ObjectId pwc = cmis.checkOut(null, id);
        cmis.cancelCheckOut(null, pwc.getId());
    }

    @Test
    @Ignore
    public void checkIn() {
        String id = getObjectId("/mule-cloud-connector/test");
        cmis.checkIn(null, id, "modified content", "test", "application/octet-stream;charset=UTF-8", true,
                "modified test file", null);
    }

    @Test
    public void relationShips() {
        String id = getObjectId("/mule-cloud-connector/test");
        assertNotNull(cmis.getObjectRelationships(null, id));
    }

    @Test
    public void getAcl() {
        Acl acl = cmis.getAcl(null, getObjectId("/mule-cloud-connector/test"));
        assertNotNull(acl);
    }

    @Test
    public void getAppliedPolicies() {
        cmis.getAppliedPolicies(null, getObjectId("/mule-cloud-connector/test"));
    }

    @Test
    public void createAndDeleteFolder() {
        String parentId = getObjectId("/mule-cloud-connector");
        ObjectId toDelete = cmis.createFolder("delete me", parentId);
        cmis.delete(null, toDelete.getId(), true);
    }

    @Test
    public void createAndDeleteTree() {
        String parentId = getObjectId("/mule-cloud-connector");
        ObjectId toDelete = cmis.createFolder("delete me", parentId);
        cmis.delete(null, toDelete.getId(), true);
    }

    @Test
    public void createAndDeleteDocument() {
        ObjectId id = cmis.createDocumentByPath("/mule-cloud-connector", "foo.txt", "txttxttxt",
                "text/plain", VersioningState.NONE, "cmis:document", null, false);
        cmis.delete(null, id.getId(), true);
    }

    private String getObjectId(String path) {
        return cmis.getObjectByPath(path).getId();
    }

    /**
     * Tests that a document can be created under an existent path
     */
    @Test
    public void createDocumentExistentPath() throws Exception {
        assertCanCreateInFolder("/mule-cloud-connector/");
    }

    /**
     * Tests that a document can be created under an inexistent path
     * that contains existent folders
     */
    @Test
    public void createDocumentPartialInexistentPath() throws Exception {
        try {
            assertCanCreateInFolder("/mule-cloud-connector/bar/baz/");
        } finally {
            deleteTree("/mule-cloud-connector/bar/");
        }
    }

    /**
     * Tests that a document can be created under an inexistent path
     */
    @Test
    public void createDocumentInexistentPath() throws Exception {
        try {
            assertCanCreateInFolder("/foobar/bar/baz/");
        } finally {
            deleteTree("/foobar/");
        }

    }

    private void deleteTree(String folder) {
        CmisObject objectByPath = cmis.getObjectByPath(folder);
        if (objectByPath != null) {
            cmis.deleteTree(null, objectByPath.getId(), true, null, false);
        }
    }

    private void assertCanCreateInFolder(String folderPath) {
        ObjectId document = null;
        try {
            document = cmis.createDocumentByPath(folderPath, "File.txt", "Hello!", "text/plain",
                    VersioningState.NONE, "cmis:document", null, true);
            assertNotNull(document);
        } finally {
            if (document != null) {
                cmis.delete(null, document.getId(), true);
            }
        }
    }
}
