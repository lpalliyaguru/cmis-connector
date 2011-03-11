/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
