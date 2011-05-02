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
import java.util.Map;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;

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
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;

@Connector(namespacePrefix = "cmis")
public class CMISCloudConnector implements Initialisable, CMISFacade
{

    /**
     * Username
     */
    @Property
    private String username;
    /**
     * Password
     */
    @Property
    private String password;
    /**
     * The identifier for the Repository that this connector instance works with
     */
    @Property
    private String repositoryId;
    /**
     * URL base for the SOAP connector.
     */
    @Property
    private String baseUrl;

    /**
     * The type of endpoint
     */
    private String endpoint;

    /**
     * Reference to a CMISFacade implementation in case you want to
     * use another implementation or initialize the default in a
     * diferent way. Using this option make useless the other
     * attributes.
     */
    private CMISFacade facade;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public String getRepositoryId()
    {
        return repositoryId;
    }

    public void setRepositoryId(final String repositoryId)
    {
        this.repositoryId = repositoryId;
    }


    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public CMISFacade getFacade()
    {
        return facade;
    }

    public void setFacade(final CMISFacade facade)
    {
        this.facade = facade;
    }

    public void initialise() throws InitialisationException
    {
        if (facade == null)
        {
            boolean useAtomPub = false;
            if (endpoint == null)
            {
                useAtomPub = true;
            }
            else if ("soap".equals(endpoint))
            {
                useAtomPub = false;
            }
            else if ("atompub".equals(endpoint))
            {
                useAtomPub = true;
            }
            else
            {
                throw new IllegalStateException("unknown endpoint type " + endpoint);
            }
            facade = new ChemistryCMISFacade(username, password, repositoryId, baseUrl, useAtomPub);
        }
    }


    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    /**
     * Returns information about the CMIS repository, the optional capabilities it supports and its Access Control information if applicable.
     *
     * {@code <cmis:repository-info/>}
     */
    @Operation
    public RepositoryInfo repositoryInfo()
    {
        return facade.repositoryInfo();
    }

    /**
     * Gets repository changes.
     *
     * {@code <cmis:changelog changeLogToken="#[payload]" includeProperties="false" />}
     *
     * @param changeLogToken    The change log token to start from or <code>null</code>
     * @param includeProperties Indicates if changed properties should be included in
     *                          the result
     */
    @Operation
    public ChangeEvents changelog(@Parameter(optional=true) final String changeLogToken, final boolean includeProperties)
    {
        return facade.changelog(changeLogToken, includeProperties);
    }

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     *
     * {@code <cmis:get-object-by-id objectId="#[bean:objectId]"/>}
     *
     * @param objectId The object id
     */
    @Operation
    public CmisObject getObjectById(final String objectId)
    {
        return facade.getObjectById(objectId);
    }

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     *
     * {@code <cmis:get-object-by-path objectId="#[bean:path]"/>}
     *
     * @param path Path of the object to retrieve
     */
    @Operation
    public CmisObject getObjectByPath(final String path)
    {
        return facade.getObjectByPath(path);
    }

    /**
     * Creates a new document in the repository.
     *
     * {@code
     * <cmis:create-document-by-path folderPath="#[bean:path]"
     *                               filename="myfilename"
     *                               content="#[bean:content]"
     *                               mimeType="text/html"
     *                               versioningState="none"/>
     * }
     *
     * @param folderPath      Folder in the repository that will hold the document
     * @param filename        Name of the file
     * @param content         File content (no byte array or input stream for now)
     * @param mimeType        Stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.
     * @return the object id of the created
     */
    @Operation
    public ObjectId createDocumentByPath(final String folderPath,
                                         final String filename,
                                         final Object content,
                                         final String mimeType,
                                         final VersioningState versioningState,
                                         final String objectType)
    {
        return facade.createDocumentByPath(folderPath, filename, content, mimeType, versioningState,
                                           objectType);
    }

    /**
     * Creates a new document in the repository.
     *
     * {@code
     * <cmis:create-document-by-id folderId="#[bean:folderId]"
     *                             filename="myfilename"
     *                             content="#[bean:content]"
     *                             mimeType="text/html"
     *                             versioningState="none"/>
     * }
     *
     * @param folderId        Folder Object Id
     * @param filename        Name of the file
     * @param content         File content (no byte array or input stream for now)
     * @param mimeType        Stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.
     * @return The object id of the created
     */
    @Operation
    public ObjectId createDocumentById(final String folderId,
                                       final String filename,
                                       final Object content,
                                       final String mimeType,
                                       final VersioningState versioningState,
                                       final String objectType)
    {
        return facade.createDocumentById(folderId, filename, content, mimeType, versioningState,
                                         objectType);
    }

    /**
     * Creates a folder. Note that this is not recusive creation. You just create
     * one folder
     *
     * {@code <cmis:create-folder folderName="hello" parentObjectId="repository.rootFolder" />}
     *
     * @param folderName     Folder name (eg: "my documents")
     * @param parentObjectId Parent folder for the folder being created (eg: repository.rootFolder)
     */
    @Operation
    public ObjectId createFolder(String folderName, String parentObjectId)
    {
        return facade.createFolder(folderName, parentObjectId);
    }

    @Operation
    public ObjectType getTypeDefinition(String typeId) {
        return facade.getTypeDefinition(typeId);
    }

    @Operation
    public ItemIterable<Document> getCheckoutDocs(@Parameter(optional=true) String filter,
            @Parameter(optional = true) String orderBy, @Parameter(optional = true) Boolean includeACLs) {
        return facade.getCheckoutDocs(filter, orderBy, includeACLs);
    }

    @Operation
    public ItemIterable<QueryResult> query(String statement, Boolean searchAllVersions, 
            @Parameter(optional=true) String filter, @Parameter(optional=true) String orderBy,
            @Parameter(optional=true) Boolean includeACLs) 
    {
        return facade.query(statement, searchAllVersions, filter, orderBy, includeACLs);
    }

    @Operation
    public List<Folder> getParentFolders(@Parameter(optional=true) CmisObject cmisObject, 
                                         @Parameter(optional=true) String objectId) 
    {
        return facade.getParentFolders(cmisObject, objectId);
    }

    @Operation
    public Object folder(@Parameter(optional=true) Folder folder, 
                         @Parameter(optional=true) String folderId, 
                         NavigationOptions get,
                         @Parameter(optional=true) Integer depth,
                         @Parameter(optional=true) String filter,
                         @Parameter(optional=true) String orderBy,
                         @Parameter(optional=true) Boolean includeACLs)
    {
        return facade.folder(folder, folderId, get, depth, filter, orderBy, includeACLs);
    }

    @Operation
    public ContentStream getContentStream(@Parameter(optional = true) CmisObject cmisObject,
                                          @Parameter(optional = true) String objectId)
    {
        return facade.getContentStream(cmisObject, objectId);
    }

    @Operation
    public FileableCmisObject moveObject(@Parameter(optional = true) FileableCmisObject cmisObject,
                                         @Parameter(optional = true) String objectId,
                                         String sourceFolderId,
                                         String targetFolderId)
    {
        return facade.moveObject(cmisObject, objectId, sourceFolderId, targetFolderId);
    }

    @Operation
    public CmisObject updateObjectProperties(@Parameter(optional = true) CmisObject cmisObject, 
                                             @Parameter(optional = true) String objectId, 
                                             Map<String, Object> properties)
    {
        return facade.updateObjectProperties(cmisObject, objectId, properties);
    }

    @Operation
    public List<Relationship> getObjectRelationships(@Parameter(optional = true) CmisObject cmisObject,
                                                     @Parameter(optional = true) String objectId)
    {
        return facade.getObjectRelationships(cmisObject, objectId);
    }

    @Operation
    public Acl getAcl(@Parameter(optional = true) CmisObject cmisObject,@Parameter(optional = true) String objectId)
    {
        return facade.getAcl(cmisObject, objectId);
    }

    @Operation
    public List<Document> getAllVersions(@Parameter(optional = true) CmisObject document,
                                         @Parameter(optional = true) String objectId, 
                                         @Parameter(optional = true) String filter,
                                         @Parameter(optional = true) String orderBy,
                                         @Parameter(optional = true) Boolean includeACLs)
    {
        return facade.getAllVersions(document, objectId, filter, orderBy, includeACLs);
    }

    @Operation
    public Acl applyAcl(@Parameter(optional = true) CmisObject cmisObject,
                        @Parameter(optional = true) String objectId,
                        List<Ace> addAces,
                        List<Ace> removeAces,
                        AclPropagation aclPropagation)
    {
        return facade.applyAcl(cmisObject, objectId, addAces, removeAces, aclPropagation);
    }

    public List<Policy> getAppliedPolicies(@Parameter(optional = true) CmisObject cmisObject, 
                                           @Parameter(optional = true)String objectIc)
    {
        return facade.getAppliedPolicies(cmisObject, objectIc);
    }


    public void delete(@Parameter(optional = true) CmisObject cmisObject, 
                       @Parameter(optional = true) String objectId,
                       @Parameter(optional = true, defaultValue="false") boolean allVersions)
    {
        facade.delete(cmisObject, objectId, allVersions);
    }

}

