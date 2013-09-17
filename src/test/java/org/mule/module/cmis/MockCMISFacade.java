/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Policy;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                         final String objectType, Map<String, String> properties, boolean force)
    {
        throw new NotImplementedException();
    }
    
    public ObjectId createDocumentByPathFromContent(final String folderPath,
										            final String filename,
										            final Object content,
										            final String mimeType,
										            final VersioningState versioningState,
										            final String objectType, Map<String, String> properties, boolean force)
	{
    	throw new NotImplementedException();
	}

    public CmisObject getOrCreateFolderByPath(String folderPath) 
    {
    	throw new NotImplementedException();
    }
    
    public ObjectId createDocumentById(final String objectId,
                                       final String filename,
                                       final Object content,
                                       final String mimeType,
                                       final VersioningState versioningState,
                                       final String objectType, Map<String, String> properties)
    {
        throw new NotImplementedException();
    }
    
    public ObjectId createDocumentByIdFromContent(final String objectId,
										          final String filename,
										          final Object content,
										          final String mimeType,
										          final VersioningState versioningState,
										          final String objectType, Map<String, String> properties)
	{
    	throw new NotImplementedException();
	}

    public ObjectId createFolder(final String folderName, final String parentObjectId)
    {
        throw new NotImplementedException();
    }

    public ObjectType getTypeDefinition(String typeId)
    {
        throw new NotImplementedException();
    }

    public ItemIterable<Document> getCheckoutDocs(String filter, String orderBy)
    {
        throw new NotImplementedException();
    }

    public ItemIterable<QueryResult> query(String statement,
            Boolean searchAllVersions, String filter, String orderBy) 
    {
        throw new NotImplementedException();
    }

    public List<Folder> getParentFolders(CmisObject object, String objectId) 
    {
        throw new NotImplementedException();
    }

     public Object folder(Folder folder,
                         String folderId,
                         NavigationOptions get,
                         Integer depth,
                         String filter,
                         String orderBy)
    {
         throw new NotImplementedException();
    }

    public ContentStream getContentStream(CmisObject object, String objectId)
    {
        throw new NotImplementedException();
    }

    public FileableCmisObject moveObject(FileableCmisObject object,
                                         String objectId,
                                         String sourceFolderId,
                                         String targetFolderId)
    {
        throw new NotImplementedException();
    }

    public CmisObject updateObjectProperties(CmisObject object, String objectId, Map<String, String> properties)
    {
        throw new NotImplementedException();
    }

    public List<Relationship> getObjectRelationships(CmisObject object, String objectId)
    {
        throw new NotImplementedException();
    }

    public Acl getAcl(CmisObject object, String objectId)
    {
        throw new NotImplementedException();
    }

    public List<Document> getAllVersions(CmisObject document,
                                         String documentId,
                                         String filter,
                                         String orderBy)
    {
        throw new NotImplementedException();
    }

    public Acl applyAcl(CmisObject cmisObject,
                        String objectId,
                        List<Ace> addAces,
                        List<Ace> removeAces,
                        AclPropagation aclPropagation)
    {
        throw new NotImplementedException();
    }

    public List<Policy> getAppliedPolicies(CmisObject cmisObject, String objectId)
    {
        throw new NotImplementedException();
    }

    public void delete(CmisObject cmisObject, String objectId, boolean allVersions)
    {
    }

    public List<Repository> repositories()
    {
        throw new NotImplementedException();
    }

    public ObjectId checkOut(CmisObject document, String documentId)
    {
        throw new NotImplementedException();
    }

    public void cancelCheckOut(CmisObject document, String documentId)
    {
    }

    public ObjectId checkIn(CmisObject document,
                            String documentId,
                            Object content,
                            String filename,
                            String mimeType,
                            boolean major,
                            String checkinComment, Map<String, String> properties)
    {
        throw new NotImplementedException();
    }

    public List<String> deleteTree(CmisObject folder, String folderId,
            boolean allversions, UnfileObject unfile, boolean continueOnFailure)
    {
        throw new NotImplementedException();
    }

    public void applyPolicy(CmisObject cmisObject, String objectId,
            List<ObjectId> policyIds) 
    {
    }
    
    public void applyAspect(String objectId, String aspectName, Map<String, String> properties)
    {
        throw new NotImplementedException();
    }
    
    public ObjectId createRelationship ( String parentObjectId, 
						             String childObjectId, 
						             String relationshipType )
    {
        throw new NotImplementedException();
    }

}
