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
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;

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
            facade = CMISFacadeAdaptor.adapt(new ChemistryCMISFacade(username, password, repositoryId, baseUrl, useAtomPub));
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
     * Returns all repositories that are available at the endpoint.
     * 
     * {@code  <cmis:repositories />}
     * 
     * @return a list of {@link Repository}.
     */
    @Operation
    public List<Repository> repositories()
    {
        return facade.repositories();
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
    public ChangeEvents changelog(@Parameter(optional = true) final String changeLogToken, 
                                  final boolean includeProperties)
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
     * @param properties the properties optional document properties to set
     * @param force if should folder structure must be created when there 
     *     are missing intermediate folders
     * @return the object id of the created
     */
    @Operation
    public ObjectId createDocumentByPath(final String folderPath,
                                         final String filename,
                                         final Object content,
                                         final String mimeType,
                                         final VersioningState versioningState,
                                         final String objectType, 
                                         @Parameter(optional=true) final Map<String, Object> properties,
                                         @Parameter(optional=true, defaultValue="false") final boolean force)
    {
        return facade.createDocumentByPath(folderPath, filename, content, mimeType, versioningState,
                                           objectType, properties, force);
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
     * @param properties the properties optional document properties to set
     * @return The object id of the created
     */
    @Operation
    public ObjectId createDocumentById(final String folderId,
                                       final String filename,
                                       final Object content,
                                       final String mimeType,
                                       final VersioningState versioningState,
                                       final String objectType, 
                                       @Parameter(optional=true, defaultValue="false") final Map<String, Object> properties)
    {
        return facade.createDocumentById(folderId, filename, content, mimeType, versioningState,
                                         objectType, properties);
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

    /**
     * Returns the type definition of the given type id.
     * 
     * {@code <cmis:get-type-definition typeId="12345" />}
     * 
     * @param typeId Object type Id
     * @return type of object ({@see ObjectType})  
     */
    @Operation
    public ObjectType getTypeDefinition(String typeId) 
    {
        return facade.getTypeDefinition(typeId);
    }

    /**
     * Retrieve list of checked out documents.
     * 
     * {@code <cmis:get-checkout-docs />}
     * 
     * @param filter comma-separated list of properties to filter
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name
     * @return list of {@link Document}.
     */
    @Operation
    public ItemIterable<Document> getCheckoutDocs(@Parameter(optional = true) String filter,
            @Parameter(optional = true) String orderBy) 
            {
        return facade.getCheckoutDocs(filter, orderBy);
    }

    /**
     * Sends a query to the repository
     * 
     * {@code <cmis:query searchAllVersions="true" statement="SELECT * FROM cmis:document" />}
     * 
     * @param statement the query statement (CMIS query language)
     * @param searchAllVersions specifies if the latest and non-latest versions 
     *                          of document objects should be included
     * @param filter comma-separated list of properties to filter
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name
     * @return an iterable of {@link QueryResult}
     */
    @Operation
    public ItemIterable<QueryResult> query(String statement, Boolean searchAllVersions, 
            @Parameter(optional = true) String filter, @Parameter(optional = true) String orderBy) 
    {
        return facade.query(statement, searchAllVersions, filter, orderBy);
    }

    
    /**
     * Retrieves the parent folders of a fileable cmis object
     * 
     * {@code 
     *    <cmis:get-parent-folders objectId="workspace://SpacesStore/ae87c116-be51-43df-8f79-f8859fb5bb20" />
     *    or
     *    <cmis:get-parent-folders cmisObject="#[payload]" />
     * }
     * 
     * @param cmisObject the object whose parent folders are needed. can be null if "objectId" is set. 
     * @param objectId id of the object whose parent folders are needed. can be null if "object" is set.
     * @return a list of the object's parent folders.
     */
    @Operation
    public List<Folder> getParentFolders(@Parameter(optional = true) CmisObject cmisObject, 
                                         @Parameter(optional = true) String objectId) 
    {
        return facade.getParentFolders(cmisObject, objectId);
    }

    /**
     * Navigates the folder structure.
     * 
     * {@code <cmis:get-object-by-path path="/mule-cloud-connector" />
     *  <cmis:folder get="CHILDREN" folderId="#[payload:id]"/>
     *  
     *  or 
     *  
     *  <cmis:get-object-by-path path="/mule-cloud-connector" />
     *  <cmis:folder get="DESCENDANTS" folderId="#[payload:id]"/>
     *  
     *  }
     * 
     * @param folder Folder Object. Can be null if "folderId" is set. 
     * @param folderId Folder Object id. Can be null if "folder" is set.
     * @param get NavigationOptions that specifies whether to get the parent folder,
     *              the list of immediate children or the whole descendants tree
     * @param depth if "get" value is DESCENDANTS, represents the depth of the
     *              descendants tree
     * @param filter comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)
     * @return the following, depending on the value of "get" parameter:
     *  <ul>
     *          <li>PARENT: returns the parent Folder</li>
     *          <li>CHILDREN: returns a CmisObject ItemIterable with objects contained in the current folder</li>
     *          <li>DESCENDANTS: List<Tree<FileableCmisObject>> representing
     *                         the whole descentants tree of the current folder</li>
     *          <li>TREE: List<Tree<FileableCmisObject>> representing the 
     *                         directory structure under the current folder.
     *                         </li>
     * </ul>                           
     */
    @Operation
    public Object folder(@Parameter(optional = true) Folder folder, 
                         @Parameter(optional = true) String folderId, 
                         NavigationOptions get,
                         @Parameter(optional = true) Integer depth,
                         @Parameter(optional = true) String filter,
                         @Parameter(optional = true) String orderBy)
    {
        return facade.folder(folder, folderId, get, depth, filter, orderBy);
    }

    
    /**
     * Retrieves the content stream of a Document.
     * 
     * {@code <cmis:get-content-stream cmisObject="#[variable:document]" /> }
     * 
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set. 
     * @param objectId Id of the document from which to get the stream. Can be null if "object" is set.
     * @return The content stream of the document.
     */
    @Operation
    public ContentStream getContentStream(@Parameter(optional = true) CmisObject cmisObject,
                                          @Parameter(optional = true) String objectId)
    {
        return facade.getContentStream(cmisObject, objectId);
    }


    /**
     * Moves a fileable cmis object from one location to another. Take into account that a fileable
     * object may be filled in several locations. Thats why you must specify a source folder.
     * 
     * {@code <cmis:move-object sourceFolderId="1111" 
     *   targetFolderId="workspace://SpacesStore/2437b2ff-8804-4426-a268-fcfb3ef34ffc" 
     *         objectId="workspace://SpacesStore/ae87c116-be51-43df-8f79-f8859fb5bb20" />}
     *                        
     * @param cmisObject The object to move. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param sourceFolderId Id of the source folder
     * @param targetFolderId Id of the target folder
     * @return The object moved (FileableCmisObject)
     */
    @Operation
    public FileableCmisObject moveObject(@Parameter(optional = true) FileableCmisObject cmisObject,
                                         @Parameter(optional = true) String objectId,
                                         String sourceFolderId,
                                         String targetFolderId)
    {
        return facade.moveObject(cmisObject, objectId, sourceFolderId, targetFolderId);
    }

    /**
     * Update an object's properties
     * 
     * {@code <cmis:update-object-properties objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28">
     *       <cmis:properties>
     *           <cmis:property key="propkey" value="propValue"/>
     *       </cmis:properties>
     *   </cmis:update-object-properties>}
     * 
     * @param cmisObject Object to be updated. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param properties The properties to update
     * @return The updated object (a repository might have created a new object)
     */
    @Operation
    public CmisObject updateObjectProperties(@Parameter(optional = true) CmisObject cmisObject, 
                                             @Parameter(optional = true) String objectId, 
                                             @Parameter Map<String, Object> properties)
    {
        return facade.updateObjectProperties(cmisObject, objectId, properties);
    }

    /**
     * Returns the relationships if they have been fetched for an object.
     * 
     * {@code <cmis:get-object-relationships objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />}
     * 
     * @param cmisObject the object whose relationships are needed
     * @return list of the object's relationships
     */
    @Operation
    public List<Relationship> getObjectRelationships(@Parameter(optional = true) CmisObject cmisObject,
                                                     @Parameter(optional = true) String objectId)
    {
        return facade.getObjectRelationships(cmisObject, objectId);
    }

    /**
     * Returns the ACL if it has been fetched for an object.
     * 
     * {@code <cmis:get-acl objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28"  />}
     * 
     * @param cmisObject the object whose Acl is needed
     * @return the object's Acl
     */
    @Operation
    public Acl getAcl(@Parameter(optional = true) CmisObject cmisObject, @Parameter(optional = true) String objectId)
    {
        return facade.getAcl(cmisObject, objectId);
    }

    /**
     * Retrieve an object's version history
     * 
     * {@code <cmis:get-all-versions document="#[payload]" />}
     * 
     * @param document the document whose versions are to be retrieved
     * @param objectId Id of the document whose versions are to be retrieved
     * @param filter comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)
     * @return versions of the document.
     */
    @Operation
    public List<Document> getAllVersions(@Parameter(optional = true) CmisObject document,
                                         @Parameter(optional = true) String documentId, 
                                         @Parameter(optional = true) String filter,
                                         @Parameter(optional = true) String orderBy)
    {
        return facade.getAllVersions(document, documentId, filter, orderBy);
    }
    
    /**
     * Checks out the document and returns the object id of the PWC (private working copy).
     * 
     * {@code <cmis:check-out documentId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />}
     * 
     * @param document The document to be checked out. Can be null if "documentId" is set.
     * @param objectId Id of the document to be checked out. Can be null if "document" is set.
     * @return PWC ObjectId
     */
    @Operation
    public ObjectId checkOut(@Parameter(optional = true) final CmisObject document,
                             @Parameter(optional = true) final String documentId)
    {
        return facade.checkOut(document, documentId);
    }

    
    /**
     * If applied to a PWC (private working copy) of the document, the check out
     * will be reversed. Otherwise, an exception will be thrown.
     * 
     * {@code <cmis:cancel-check-out documentId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />}
     * 
     * @param document The checked out document. Can be null if "documentId" is set.
     * @param objectId Id of the checked out document. Can be null if "document" is set.
     */
    @Operation
    public void cancelCheckOut(@Parameter(optional = true) CmisObject document, 
                               @Parameter(optional = true) String documentId)
    {
        facade.cancelCheckOut(document, documentId);
    }

    /**
     * If applied to a PWC (private working copy) it performs a check in.
     * Otherwise, an exception will be thrown.
     * 
     * {@code <cmis:check-in content="modified content" filename="#[payload:name]"
     *                   checkinComment="change on file" major="true"
     *                  mimeType="application/octet-stream;charset=UTF-8" />}
     * 
     * @param document The document to check-in. Can be null if "documentId" is set.
     * @param documentId Id of the document to check-in. Can be null if "document" is set.
     * @param content           File content (no byte array or input stream for now)
     * @param filename          Name of the file
     * @param mimeType          Stream content-type
     * @param major
     * @param checkinComment Check-in comment
     * @param properties custom properties
     * @return the {@link ObjectId} of the checkedin document
     */
    @Operation
    public ObjectId checkIn(@Parameter(optional = true) final CmisObject document,
                            @Parameter(optional = true) final String documentId,
                            final Object content,
                            final String filename,
                            final String mimeType,
                            final boolean major,
                            final String checkinComment,
                            @Parameter(optional = true) final Map<String, String> properties)
    {
        return facade.checkIn(document, documentId, content, filename, mimeType, major, checkinComment, properties);
    }

    
    /**
     * Set the permissions associated with an object.
     * 
     * {@code <cmis:get-acl objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />}
     * 
     * @param cmisObject the object whose Acl is intended to change.
     * @param addAces added access control entities
     * @param removeAces removed access control entities
     * @param aclPropagation wheter to propagate changes or not. can be  REPOSITORYDETERMINED | OBJECTONLY | PROPAGATE
     * @return the new access control list
     */
    @Operation
    public Acl applyAcl(@Parameter(optional = true) CmisObject cmisObject,
                        @Parameter(optional = true) String objectId,
                        List<Ace> addAces,
                        List<Ace> removeAces,
                        AclPropagation aclPropagation)
    {
        return facade.applyAcl(cmisObject, objectId, addAces, removeAces, aclPropagation);
    }

    
    /**
     * Get the policies that are applied to an object.
     * 
     * {@code <cmis:get-applied-policies objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28"/>}
     * 
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set. 
     * @param objectId Id of the document from which to get the stream. Can be null if "object" is set.
     * @return List of applied policies
     */
    @Operation
    public List<Policy> getAppliedPolicies(@Parameter(optional = true) CmisObject cmisObject, 
                                           @Parameter(optional = true)String objectId)
    {
        return facade.getAppliedPolicies(cmisObject, objectId);
    }

    /**
     * Applies policies to this object.
     * 
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set. 
     * @param objectId Id of the document from which to get the stream. Can be null if "object" is set.
     * @param policyIds Policy ID's to apply
     */
    @Operation
    public void applyPolicy(@Parameter(optional = true)CmisObject cmisObject, 
                            @Parameter(optional = true)String objectId,
                            List<ObjectId> policyIds) 
    {
        facade.applyPolicy(cmisObject, objectId, policyIds);
    }

    /**
     * Remove an object
     * 
     * {@code <cmis:delete object="#[payload]" allVersions="true" />}
     * 
     * @param cmisObject The object to be deleted. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param allVersions If true, deletes all version history of the object. Defaults to "false".
     */
    @Operation
    public void delete(@Parameter(optional = true) CmisObject cmisObject, 
                       @Parameter(optional = true) String objectId,
                       @Parameter(optional = true, defaultValue = "false") boolean allVersions)
    {
        facade.delete(cmisObject, objectId, allVersions);
    }
    
    /**
     * Deletes a folder and all subfolders.
     * 
     * @param folder Folder Object. Can be null if "folderId" is set. 
     * @param folderId Folder Object id. Can be null if "folder" is set.
     * @param allversions If true, then delete all versions of the document. 
     *                    If false, delete only the document object specified.
     * @param unfile Specifies how the repository must process file-able child- 
     *               or descendant-objects.
     * @param continueOnFailure Specified whether to continue attempting to perform 
     *  this operation even if deletion of a child- or descendant-object 
     *  in the specified folder cannot be deleted or not. 
     * @return a list of object ids which failed to be deleted.
     */
    @Operation
    public List<String> deleteTree(@Parameter(optional = true) CmisObject folder, 
                                   @Parameter(optional = true) String folderId,
                                   boolean allversions, 
                                   @Parameter(optional = true) UnfileObject unfile, 
                                   boolean continueOnFailure) 
    {
        return facade.deleteTree(folder, folderId, allversions, unfile, continueOnFailure);
    }
    
}

