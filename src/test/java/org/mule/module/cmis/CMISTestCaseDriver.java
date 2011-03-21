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

import org.mule.api.lifecycle.InitialisationException;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CMISTestCaseDriver
{
    private final CMISCloudConnector cmis = new CMISCloudConnector();
    {
        cmis.setUsername("admin");
        cmis.setPassword("admin");
        cmis.setRepositoryId("371554cd-ac06-40ba-98b8-e6b60275cca7");
        cmis.setBaseUrl("http://cmis.alfresco.com/cmis/");
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
    @Ignore
    public void changeLog() throws InitialisationException
    {
        final ChangeEvents events =  cmis.changelog("42215", false);
        Assert.assertFalse(events.getHasMoreItems());
        Assert.assertTrue(events.getTotalNumItems() > 0);
    }
    
    @Test
    @Ignore
    public void createFolder() throws InitialisationException
    {
        final RepositoryInfo info = cmis.repositoryInfo();
        ObjectId id = cmis.createFolder("mule-cloud-connector", info.getRootFolderId());
        System.out.println(id);
    }
    
    
}
