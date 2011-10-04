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
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.annotations.param.Payload;

import java.util.List;
import java.util.Map;

@Module(name = "cmis", version = "1.1")
public class CMISCloudConnector implements CMISFacade {
    /**
     * Username
     */
    @Configurable
    private String username;
    /**
     * Password
     */
    @Configurable
    private String password;
    /**
     * The identifier for the Repository that this connector instance works with
     */
    @Configurable
    private String repositoryId;
    /**
     * URL base for the SOAP connector.
     */
    @Configurable
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

    @Start
    public void initialise() {
        if (facade == null) {
            boolean useAtomPub = false;
            if (endpoint == null) {
                useAtomPub = true;
            } else if ("soap".equals(endpoint)) {
                useAtomPub = false;
            } else if ("atompub".equals(endpoint)) {
                useAtomPub = true;
            } else {
                throw new IllegalStateException("unknown endpoint type " + endpoint);
            }
            facade = CMISFacadeAdaptor.adapt(new ChemistryCMISFacade(username, password, repositoryId, baseUrl, useAtomPub));
        }
    }

    /**
     * Returns all repositories that are available at the endpoint.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:repositories}
     *
     * @return a list of {@link Repository}.
     */
    @Processor
    public List<Repository> repositories() {
        return facade.repositories();
    }

    /**
     * Returns information about the CMIS repository, the optional capabilities it supports and its Access Control information if applicable.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:repositoryInfo}
     */
    @Processor
    public RepositoryInfo repositoryInfo() {
        return facade.repositoryInfo();
    }

    /**
     * Gets repository changes.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:changelog}
     *
     * @param changeLogToken    The change log token to start from or <code>null</code>
     * @param includeProperties Indicates if changed properties should be included in
     *                          the result
     */
    @Processor
    public ChangeEvents changelog(@Optional String changeLogToken,
                                  boolean includeProperties) {
        return facade.changelog(changeLogToken, includeProperties);
    }


    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getObjectById}
     *
     * @param objectId The object id
     */
    @Processor
    public CmisObject getObjectById(String objectId) {
        return facade.getObjectById(objectId);
    }

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getObjectByPath}
     *
     * @param path Path of the object to retrieve
     */
    @Processor
    public CmisObject getObjectByPath(String path) {
        return facade.getObjectByPath(path);
    }

    /**
     * Creates a new document in the repository.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:createDocumentByPath}
     *
     * @param folderPath      Folder in the repository that will hold the document
     * @param filename        Name of the file
     * @param content         File content (no byte array or input stream for now)
     * @param mimeType        Stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.
     * @param properties      the properties optional document properties to set
     * @param force           if should folder structure must be created when there
     *                        are missing intermediate folders
     * @return the object id of the created
     */
    @Processor
    public ObjectId createDocumentByPath(String folderPath,
                                         String filename,
                                         @Payload Object content,
                                         String mimeType,
                                         VersioningState versioningState,
                                         String objectType,
                                         @Optional Map<String, String> properties,
                                         @Optional @Default("false") boolean force) {
        return facade.createDocumentByPath(folderPath, filename, content, mimeType, versioningState,
                objectType, properties, force);
    }

    /**
     * Creates a new document in the repository.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:createDocumentById}
     *
     * @param folderId        Folder Object Id
     * @param filename        Name of the file
     * @param content         File content (no byte array or input stream for now)
     * @param mimeType        Stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.
     * @param properties      the properties optional document properties to set
     * @return The object id of the created
     */
    @Processor
    public ObjectId createDocumentById(String folderId,
                                       String filename,
                                       @Payload Object content,
                                       String mimeType,
                                       VersioningState versioningState,
                                       String objectType,
                                       @Optional @Default("false") Map<String, String> properties) {
        return facade.createDocumentById(folderId, filename, content, mimeType, versioningState,
                objectType, properties);
    }

    /**
     * Creates a folder. Note that this is not recusive creation. You just create
     * one folder
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:createFolder}
     *
     * @param folderName     Folder name (eg: "my documents")
     * @param parentObjectId Parent folder for the folder being created (eg: repository.rootFolder)
     */
    @Processor
    public ObjectId createFolder(String folderName, String parentObjectId) {
        return facade.createFolder(folderName, parentObjectId);
    }

    /**
     * Returns the type definition of the given type id.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getTypeDefinition}
     *
     * @param typeId Object type Id
     * @return type of object {@link ObjectType}
     */
    @Processor
    public ObjectType getTypeDefinition(String typeId) {
        return facade.getTypeDefinition(typeId);
    }

    /**
     * Retrieve list of checked out documents.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getCheckoutDocs}
     *
     * @param filter  comma-separated list of properties to filter
     * @param orderBy comma-separated list of query names and the ascending modifier
     *                "ASC" or the descending modifier "DESC" for each query name
     * @return list of {@link Document}.
     */
    @Processor
    public ItemIterable<Document> getCheckoutDocs(@Optional String filter, @Optional String orderBy) {
        return facade.getCheckoutDocs(filter, orderBy);
    }

    /**
     * Sends a query to the repository
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:query}
     *
     * @param statement         the query statement (CMIS query language)
     * @param searchAllVersions specifies if the latest and non-latest versions
     *                          of document objects should be included
     * @param filter            comma-separated list of properties to filter
     * @param orderBy           comma-separated list of query names and the ascending modifier
     *                          "ASC" or the descending modifier "DESC" for each query name
     * @return an iterable of {@link QueryResult}
     */
    @Processor
    public ItemIterable<QueryResult> query(String statement, Boolean searchAllVersions, @Optional String filter, @Optional String orderBy) {
        return facade.query(statement, searchAllVersions, filter, orderBy);
    }

    /**
     * Retrieves the parent folders of a fileable cmis object
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getParentFolders}
     *
     * @param cmisObject the object whose parent folders are needed. can be null if "objectId" is set.
     * @param objectId   id of the object whose parent folders are needed. can be null if "object" is set.
     * @return a list of the object's parent folders.
     */
    @Processor
    public List<Folder> getParentFolders(@Optional CmisObject cmisObject, @Optional String objectId) {
        return facade.getParentFolders(cmisObject, objectId);
    }

    /**
     * Navigates the folder structure.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:folder}
     *
     * @param folder   Folder Object. Can be null if "folderId" is set.
     * @param folderId Folder Object id. Can be null if "folder" is set.
     * @param get      NavigationOptions that specifies whether to get the parent folder,
     *                 the list of immediate children or the whole descendants tree
     * @param depth    if "get" value is DESCENDANTS, represents the depth of the
     *                 descendants tree
     * @param filter   comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)
     * @param orderBy  comma-separated list of query names and the ascending modifier
     *                 "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)
     * @return the following, depending on the value of "get" parameter:
     *         <ul>
     *         <li>PARENT: returns the parent Folder</li>
     *         <li>CHILDREN: returns a CmisObject ItemIterable with objects contained in the current folder</li>
     *         <li>DESCENDANTS: List<Tree<FileableCmisObject>> representing
     *         the whole descentants tree of the current folder</li>
     *         <li>TREE: List<Tree<FileableCmisObject>> representing the
     *         directory structure under the current folder.
     *         </li>
     *         </ul>
     */
    @Processor
    public Object folder(@Optional Folder folder,
                         @Optional String folderId,
                         NavigationOptions get,
                         @Optional Integer depth,
                         @Optional String filter,
                         @Optional String orderBy) {
        return facade.folder(folder, folderId, get, depth, filter, orderBy);
    }


    /**
     * Retrieves the content stream of a Document.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getContentStream}
     *
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set.
     * @param objectId   Id of the document from which to get the stream. Can be null if "object" is set.
     * @return The content stream of the document.
     */
    @Processor
    public ContentStream getContentStream(@Optional CmisObject cmisObject,
                                          @Optional String objectId) {
        return facade.getContentStream(cmisObject, objectId);
    }

    /**
     * Moves a fileable cmis object from one location to another. Take into account that a fileable
     * object may be filled in several locations. Thats why you must specify a source folder.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:moveObject}
     *
     * @param cmisObject     The object to move. Can be null if "objectId" is set.
     * @param objectId       The object's id. Can be null if "cmisObject" is set.
     * @param sourceFolderId Id of the source folder
     * @param targetFolderId Id of the target folder
     * @return The object moved (FileableCmisObject)
     */
    @Processor
    public FileableCmisObject moveObject(@Optional FileableCmisObject cmisObject,
                                         @Optional String objectId,
                                         String sourceFolderId,
                                         String targetFolderId) {
        return facade.moveObject(cmisObject, objectId, sourceFolderId, targetFolderId);
    }


    /**
     * Update an object's properties
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:updateObjectProperties}
     *
     * @param cmisObject Object to be updated. Can be null if "objectId" is set.
     * @param objectId   The object's id. Can be null if "cmisObject" is set.
     * @param properties The properties to update
     * @return The updated object (a repository might have created a new object)
     */
    @Processor
    public CmisObject updateObjectProperties(@Optional CmisObject cmisObject,
                                             @Optional String objectId,
                                             Map<String, String> properties) {
        return facade.updateObjectProperties(cmisObject, objectId, properties);
    }


    /**
     * Returns the relationships if they have been fetched for an object.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getObjectRelationships}
     *
     * @param cmisObject the object whose relationships are needed
     * @return list of the object's relationships
     */
    @Processor
    public List<Relationship> getObjectRelationships(@Optional CmisObject cmisObject,
                                                     @Optional String objectId) {
        return facade.getObjectRelationships(cmisObject, objectId);
    }

    /**
     * Returns the ACL if it has been fetched for an object.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getAcl}
     *
     * @param cmisObject the object whose Acl is needed
     * @return the object's Acl
     */
    @Processor
    public Acl getAcl(@Optional CmisObject cmisObject, @Optional String objectId) {
        return facade.getAcl(cmisObject, objectId);
    }

    /**
     * Retrieve an object's version history
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getAllVersions}
     *
     * @param document   the document whose versions are to be retrieved
     * @param documentId Id of the document whose versions are to be retrieved
     * @param filter     comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)
     * @param orderBy    comma-separated list of query names and the ascending modifier
     *                   "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)
     * @return versions of the document.
     */
    @Processor
    public List<Document> getAllVersions(@Optional CmisObject document,
                                         @Optional String documentId,
                                         @Optional String filter,
                                         @Optional String orderBy) {
        return facade.getAllVersions(document, documentId, filter, orderBy);
    }

    /**
     * Checks out the document and returns the object id of the PWC (private working copy).
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:checkOut}
     *
     * @param document   The document to be checked out. Can be null if "documentId" is set.
     * @param documentId Id of the document to be checked out. Can be null if "document" is set.
     * @return PWC ObjectId
     */
    @Processor
    public ObjectId checkOut(@Optional CmisObject document,
                             @Optional String documentId) {
        return facade.checkOut(document, documentId);
    }

    /**
     * If applied to a PWC (private working copy) of the document, the check out
     * will be reversed. Otherwise, an exception will be thrown.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:cancelCheckOut}
     *
     * @param document   The checked out document. Can be null if "documentId" is set.
     * @param documentId Id of the checked out document. Can be null if "document" is set.
     */
    @Processor
    public void cancelCheckOut(@Optional CmisObject document,
                               @Optional String documentId) {
        facade.cancelCheckOut(document, documentId);
    }

    /**
     * If applied to a PWC (private working copy) it performs a check in.
     * Otherwise, an exception will be thrown.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:checkIn}
     *
     * @param document       The document to check-in. Can be null if "documentId" is set.
     * @param documentId     Id of the document to check-in. Can be null if "document" is set.
     * @param content        File content (no byte array or input stream for now)
     * @param filename       Name of the file
     * @param mimeType       Stream content-type
     * @param major          whether it is major
     * @param checkinComment Check-in comment
     * @param properties     custom properties
     * @return the {@link ObjectId} of the checkedin document
     */
    @Processor
    public ObjectId checkIn(@Optional CmisObject document,
                            @Optional String documentId,
                            @Payload Object content,
                            String filename,
                            String mimeType,
                            boolean major,
                            String checkinComment,
                            @Optional Map<String, String> properties) {
        return facade.checkIn(document, documentId, content, filename, mimeType, major, checkinComment, properties);
    }


    /**
     * Set the permissions associated with an object.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:applyAcl}
     *
     * @param cmisObject     the object whose Acl is intended to change.
     * @param addAces        added access control entities
     * @param removeAces     removed access control entities
     * @param aclPropagation wheter to propagate changes or not. can be  REPOSITORYDETERMINED | OBJECTONLY | PROPAGATE
     * @return the new access control list
     */
    @Processor
    public Acl applyAcl(@Optional CmisObject cmisObject,
                        @Optional String objectId,
                        List<Ace> addAces,
                        List<Ace> removeAces,
                        AclPropagation aclPropagation) {
        return facade.applyAcl(cmisObject, objectId, addAces, removeAces, aclPropagation);
    }

    /**
     * Get the policies that are applied to an object.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:getAppliedPolicies}
     *
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set.
     * @param objectId   Id of the document from which to get the stream. Can be null if "object" is set.
     * @return List of applied policies
     */
    @Processor
    public List<Policy> getAppliedPolicies(@Optional CmisObject cmisObject,
                                           @Optional String objectId) {
        return facade.getAppliedPolicies(cmisObject, objectId);
    }


    /**
     * Applies policies to this object.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:applyPolicy}
     *
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set.
     * @param objectId   Id of the document from which to get the stream. Can be null if "object" is set.
     * @param policyIds  Policy ID's to apply
     */
    @Processor
    public void applyPolicy(@Optional CmisObject cmisObject,
                            @Optional String objectId,
                            List<ObjectId> policyIds) {
        facade.applyPolicy(cmisObject, objectId, policyIds);
    }


    /**
     * Remove an object
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:delete}
     *
     * @param cmisObject  The object to be deleted. Can be null if "objectId" is set.
     * @param objectId    The object's id. Can be null if "cmisObject" is set.
     * @param allVersions If true, deletes all version history of the object. Defaults to "false".
     */
    @Processor
    public void delete(@Optional CmisObject cmisObject,
                       @Optional String objectId,
                       @Optional @Default("false") boolean allVersions) {
        facade.delete(cmisObject, objectId, allVersions);
    }

    /**
     * Deletes a folder and all subfolders.
     * <p/>
     * {@sample.xml ../../../doc/cmis-connector.xml.sample cmis:deleteTree}
     *
     * @param folder            Folder Object. Can be null if "folderId" is set.
     * @param folderId          Folder Object id. Can be null if "folder" is set.
     * @param allversions       If true, then delete all versions of the document.
     *                          If false, delete only the document object specified.
     * @param unfile            Specifies how the repository must process file-able child-
     *                          or descendant-objects.
     * @param continueOnFailure Specified whether to continue attempting to perform
     *                          this operation even if deletion of a child- or descendant-object
     *                          in the specified folder cannot be deleted or not.
     * @return a list of object ids which failed to be deleted.
     */
    @Processor
    public List<String> deleteTree(@Optional CmisObject folder,
                                   @Optional String folderId,
                                   boolean allversions,
                                   @Optional UnfileObject unfile,
                                   boolean continueOnFailure) {
        return facade.deleteTree(folder, folderId, allversions, unfile, continueOnFailure);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public CMISFacade getFacade() {
        return facade;
    }

    public void setFacade(CMISFacade facade) {
        this.facade = facade;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}