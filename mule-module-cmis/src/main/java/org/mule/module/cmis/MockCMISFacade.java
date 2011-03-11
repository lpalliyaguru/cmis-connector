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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.commons.lang.NotImplementedException;

/**
 * Mock {@link CMISFacade} useful for testing offline flows.
 */
public class MockCMISFacade implements CMISFacade
{
    private RepositoryInfo repositoryInfo;
    private ChangeEvents changeEvents;
    private Map<String, CmisObject> objects;

    public RepositoryInfo getRepositoryInfo()
    {
        return repositoryInfo;
    }

    public void setRepositoryInfo(final RepositoryInfo repositoryInfo)
    {
        this.repositoryInfo = repositoryInfo;
    }

    public ChangeEvents getChangeEvents()
    {
        return changeEvents;
    }

    public void setChangeEvents(final ChangeEvents changeEvents)
    {
        this.changeEvents = changeEvents;
    }

    public void setObjects(final List<CmisObject> objects)
    {
        this.objects = new HashMap<String, CmisObject>();
        for (final CmisObject object : objects)
        {
            this.objects.put(object.getId(), object);
        }
    }

    // /////////////////////////////////////////////////////////////////////
    public RepositoryInfo repositoryInfo()
    {
        return repositoryInfo;
    }

    public ChangeEvents changelog(final String changeLogToken, final boolean includeProperties)
    {
        return changeEvents;
    }

    public CmisObject getObjectById(final String objectId)
    {
        return objects == null ? null : objects.get(objectId);
    }

    public CmisObject getObjectByPath(final String path)
    {
        CmisObject ret = null;
        for (final CmisObject object : objects.values())
        {
            if (object instanceof FileableCmisObject)
            {
                final FileableCmisObject fileable = (FileableCmisObject) object;
                if (fileable.getPaths() != null && fileable.getPaths().contains(path))
                {
                    ret = object;
                    break;
                }
            }
        }
        return ret;
    }

    public ObjectId createDocumentByPath(final String folderPath,
                                         final String filename,
                                         final Object content,
                                         final String mimeType,
                                         final VersioningState versioningState,
                                         final String objectType)
    {
        throw new NotImplementedException();
    }

    public ObjectId createDocumentById(final String objectId,
                                       final String filename,
                                       final Object content,
                                       final String mimeType,
                                       final VersioningState versioningState,
                                       final String objectType)
    {
        throw new NotImplementedException();
    }

    public ObjectId createFolder(final String folderName, final String parentObjectId)
    {
        throw new NotImplementedException();
    }
}
