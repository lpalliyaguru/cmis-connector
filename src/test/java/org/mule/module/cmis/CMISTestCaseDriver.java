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

import java.util.List;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.lifecycle.InitialisationException;

public class CMISTestCaseDriver
{
    private final CMISCloudConnector cmis = new CMISCloudConnector();
    {
        cmis.setUsername("admin");
        cmis.setPassword("admin");
        cmis.setRepositoryId("371554cd-ac06-40ba-98b8-e6b60275cca7");
        cmis.setBaseUrl("http://cmis.alfresco.com/service/cmis");
        cmis.setEndpoint("atompub");
        try
        {
            cmis.initialise();
        }
        catch (InitialisationException e)
        {
            e.printStackTrace();
        }
        
        
    }
    @Test
    public void changeLog() throws InitialisationException
    {
        final ChangeEvents events =  cmis.changelog("42215", false);
        Assert.assertFalse(events.getHasMoreItems());
        Assert.assertTrue(events.getTotalNumItems() > 0);
    }
    
    @Test
    public void repositories()
    {
        Assert.assertNotNull(cmis.repositories());
    }
    
    @Test
    public void folderParent()
    {
        final String subFolderId = getObjectId("/mule-cloud-connector/test-folder");
        Folder parent = (Folder) cmis.folder(null, subFolderId, NavigationOptions.PARENT, 
            null, null, null, null);
        
        Assert.assertEquals("/mule-cloud-connector", parent.getPath());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void folderTree()
    {
        final String folderId = getObjectId("/mule-cloud-connector");
        final List<Tree<FileableCmisObject>> tree = (List<Tree<FileableCmisObject>>) cmis.folder(
                                 null, folderId, NavigationOptions.TREE, 1, null, null, null);
        Assert.assertNotNull(tree);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void folderDescendants()
    {
        final String folderId = getObjectId("/mule-cloud-connector");
        final List<Tree<FileableCmisObject>> tree = (List<Tree<FileableCmisObject>>) cmis.folder(
            null, folderId, NavigationOptions.DESCENDANTS, 1, null, null, null);
        Assert.assertNotNull(tree);
    }
    
    @Test
    public void objectParents()
    {
        final String parent = "/mule-cloud-connector";
        final CmisObject obj = cmis.getObjectByPath(parent + "/test");
        final List<Folder> parents = cmis.getParentFolders(obj, null);
        
        boolean present = false;
        
        for (Folder folder : parents)
        {
            if (folder.getPath().equals(parent))
            {
                present = true;
            }
        }
        
        Assert.assertTrue(present);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void folderContent()
    {
        final String folderId = getObjectId("/mule-cloud-connector");
        ItemIterable<CmisObject> it = (ItemIterable<CmisObject>) cmis.folder(
            null, folderId, NavigationOptions.CHILDREN, 
            null, null, null, null);
        Assert.assertNotNull(it);
    }
    
    @Test
    public void checkedOutDocs()
    {
        final ItemIterable<Document> docs = cmis.getCheckoutDocs(null, null, null);
        Assert.assertNotNull(docs);
    }
    
    @Test
    public void query()
    {
        ItemIterable<QueryResult> results = cmis.query("SELECT * from cmis:folder " +
                                                       "where cmis:path =  '/mule-cloud-connector'",
                                                       false, null, null, null);
        for (QueryResult queryResult : results)
        {
            queryResult.getPropertyValueByQueryName("cmis:path");
        }
    }
    
    @Test
    public void getContentStream()
    {
        final CmisObject object = cmis.getObjectByPath("/mule-cloud-connector/test");
        final ContentStream contentStream = cmis.getContentStream(object, null);
        Assert.assertEquals("application/octet-stream;charset=UTF-8", contentStream.getMimeType());
    }

    @Test
    public void moveObject()
    {
        final String f1 = getObjectId("/mule-cloud-connector");
        final String f2 = getObjectId("/mule-cloud-connector/test-folder");
        final FileableCmisObject obj = (FileableCmisObject) cmis.getObjectByPath("/mule-cloud-connector/move-this");
        cmis.moveObject(obj, null, f1, f2);
        Assert.assertNotNull(cmis.getObjectByPath("/mule-cloud-connector/test-folder/move-this"));
        cmis.moveObject(obj, null, f2, f1);
    }

    @Test
    public void getAllVersions()
    {
        final String id = getObjectId("/mule-cloud-connector/test");
        Assert.assertNotNull(cmis.getAllVersions(null, id, null, null, null));
    }
    
    @Test
    public void checkout()
    {
        final String id = getObjectId("/mule-cloud-connector/test");
        final ObjectId pwc = cmis.checkout(null, id);
        cmis.cancelCheckout(null, pwc.getId());
    }
    
    @Test
    public void checkIn()
    {
        final String id = getObjectId("/mule-cloud-connector/test");
        cmis.checkIn(null, id, "modified content", "test", 
            "application/octet-stream;charset=UTF-8", true, "modified test file");
    }
    
    @Test
    public void relationShips()
    {
        final String id = getObjectId("/mule-cloud-connector/test");
        Assert.assertNotNull(cmis.getObjectRelationships(null, id));
    }
    
    @Test
    public void getAcl()
    {
        Acl acl = cmis.getAcl(null, getObjectId("/mule-cloud-connector/test"));
        Assert.assertNotNull(acl);
    }
    
    @Test
    public void getAppliedPolicies()
    {
        Assert.assertNotNull(cmis.getAppliedPolicies(null, getObjectId("/mule-cloud-connector/test")));
    }
    
    @Test
    public void createAndDeleteFolder()
    {
        final ObjectId id = doCreateFolder("/mule-cloud-connector/delete-me");
        cmis.delete(null, id.getId(), false);
    }
    
    private String getObjectId(final String path)
    {
        return cmis.getObjectByPath(path).getId();
    }
    
    private ObjectId doCreateFolder(String path)
    {
        if (path.length() > 1 && path.charAt(0) == '/')
        {
            path = path.substring(1);
        }
        final RepositoryInfo info = cmis.repositoryInfo();
        return cmis.createFolder(path, info.getRootFolderId());
    }
}
