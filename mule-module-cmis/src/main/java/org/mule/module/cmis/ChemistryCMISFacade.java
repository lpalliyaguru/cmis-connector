/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.cmis;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ChangeEvent;
import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.ChangeEventsImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisInvalidArgumentException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.lang.Validate;

/**
 * Implementation of {@link CMISFacade} that use Apache Chemistry Project.
 */
public class ChemistryCMISFacade implements CMISFacade
{
    private final Session session;

    public ChemistryCMISFacade(final String username,
                               final String password,
                               final String repositoryId,
                               final String baseURL,
                               final boolean useAtomPub)
    {
        this(createSession(username, password, repositoryId, baseURL, useAtomPub));
    }

    public ChemistryCMISFacade(final Session session)
    {
        Validate.notNull(session, "session is null");
        this.session = session;
    }

    public RepositoryInfo repositoryInfo()
    {
        return session.getRepositoryInfo();
    }

    public ChangeEvents changelog(final String changeLogToken, final boolean includeProperties)
    {
        boolean hasMore = false;
        String token = changeLogToken;

        final List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
        long totalNumItems = 0;
        // follow the pages
        do
        {
            final ChangeEvents events = session.getContentChanges(token, includeProperties, 50);
            totalNumItems += events.getTotalNumItems();

            changeEvents.addAll(events.getChangeEvents());
            if (events.getHasMoreItems())
            {
                final String t = events.getLatestChangeLogToken();
                if (t != null && !t.equals(token))
                {
                    hasMore = true;
                    token = t;
                }
            }
        }
        while (hasMore);

        return new ChangeEventsImpl(token, changeEvents, false, totalNumItems);
    }

    public CmisObject getObjectById(final String objectId)
    {
        try
        {
            return session.getObject(session.createObjectId(objectId));
        }
        catch (final CmisObjectNotFoundException e)
        {
            return null;
        }
    }

    public CmisObject getObjectByPath(final String path)
    {
        try
        {
            return session.getObjectByPath(path);
        }
        catch (final CmisObjectNotFoundException e)
        {
            return null;
        }
        catch (final CmisInvalidArgumentException e) 
        {
            return null;
        }
    }

    public ObjectId createDocumentById(final String objectId,
                                             final String filename,
                                             final Object content,
                                             final String mimeType,
                                             final String versioningState,
                                             final String objectType)
    {
        Validate.notEmpty(objectId, "objectId is empty");
        return createDocument(
            session.getObject(session.createObjectId(objectId)), 
            filename, content, mimeType, versioningState, objectType);
    }

    public ObjectId createDocumentByPath(final String folderPath,
                                         final String filename,
                                         final Object content,
                                         final String mimeType,
                                         final String versioningState,
                                         final String objectType)
    {
        Validate.notEmpty(folderPath, "folderPath is empty");
        
        return createDocument(session.getObjectByPath(folderPath), 
            filename, content, mimeType, versioningState, objectType);
    }

    /** create a document */
    protected ObjectId createDocument(
                                   final CmisObject folder,
                                   final String filename,
                                   final Object content,
                                   final String mimeType,
                                   final String versioningState,
                                   final String objectType)
    {
        Validate.notNull(folder,    "folder is null");
        Validate.notEmpty(filename, "filename is empty");
        Validate.notNull(content,   "content is null");
        Validate.notEmpty(mimeType, "did you mean application/octet-stream?");
        Validate.notNull(versioningState, "versionState is null");
        VersioningState vs = null; 
        try
        {
            vs = VersioningState.valueOf(versioningState.toUpperCase());
        }
        catch (final IllegalArgumentException e) 
        {
            throw new IllegalArgumentException(String.format(
                "Illegal value for versioningState. Given `%s' could be: ",
                versioningState, Arrays.toString(VersioningState.values())), e);
        }
        
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, objectType);
        properties.put(PropertyIds.NAME, filename);
        return session.createDocument(properties, 
                session.createObjectId(folder.getId()),
                createContentStream(filename, mimeType, content), vs);   
    }

    public static ContentStream createContentStream(final String filename,
                                                    final String mimeType,
                                                    final Object content)
    {
        final ContentStreamImpl ret;

        if (content instanceof String)
        {
            ret = new ContentStreamImpl(filename, mimeType, (String) content);
        }
        else
        {
            ret = new ContentStreamImpl();
            ret.setFileName(filename);
            ret.setMimeType(mimeType);
            if (content instanceof InputStream)
            {
                ret.setStream((InputStream) content);
            }
            else if (content instanceof byte[])
            {
                ret.setStream(new ByteArrayInputStream((byte[]) content));
            }
            else
            {
                throw new IllegalArgumentException("Don't know how to handle content of type"
                                                   + content.getClass());
            }
        }

        return ret;
    }

    public ObjectId createFolder(final String folderName, final String parentObjectId)
    {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.NAME, folderName);
        properties.put(PropertyIds.OBJECT_TYPE_ID,   "cmis:folder");
        try 
        {
            return session.createFolder(properties, session.getObject(
                session.createObjectId(parentObjectId)));
        }
        catch (final CmisContentAlreadyExistsException e)
        {
            final CmisObject object = session.getObject(session.createObjectId(parentObjectId));
            if (!(object instanceof Folder)) 
            {
                throw new IllegalArgumentException(parentObjectId + " is not a folder");
            }
            final Folder folder = (Folder) object;
            for (final CmisObject o : folder.getChildren())
            {
                if (o.getName().equals(folderName)) 
                {
                    return session.createObjectId(o.getId());
                }
            }
            
            return null;
        }
    }
    
    private static Session createSession(final String username,
                                         final String password,
                                         final String repositoryId,
                                         final String baseURL,
                                         final boolean useAtomPub)
    {
        Validate.notEmpty(username, "username is empty");
        Validate.notEmpty(password, "password is empty");
        Validate.notEmpty(repositoryId, "repository-id is empty");
        Validate.notEmpty(baseURL, "base-url is empty");

        final Map<String, String> parameters = new HashMap<String, String>();

        // user credentials
        parameters.put(SessionParameter.USER, username);
        parameters.put(SessionParameter.PASSWORD, password);

        // connection settings... we prefer SOAP over ATOMPUB because some rare
        // behaviurs with the ChangeEvents.getLatestChangeLogToken().
        if (!useAtomPub)
        {
            parameters.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value());
            parameters.put(SessionParameter.WEBSERVICES_ACL_SERVICE, baseURL + "ACLService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, baseURL + "DiscoveryService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, baseURL + "MultiFilingService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, baseURL + "NavigationService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, baseURL + "ObjectService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, baseURL + "PolicyService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, baseURL
                                                                              + "RelationshipService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, baseURL + "RepositoryService?wsdl");
            parameters.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, baseURL + "VersioningService?wsdl");
        } 
        else 
        {
            parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            parameters.put(SessionParameter.ATOMPUB_URL, baseURL);
        }

        parameters.put(SessionParameter.REPOSITORY_ID, repositoryId);

        // session locale
        parameters.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
        parameters.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "en");

        return SessionFactoryImpl.newInstance().createSession(parameters);
    }

}
