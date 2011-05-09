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
import org.mule.tools.cloudconnect.annotations.Operation;

public interface CMISFacade
{

    List<Repository> repositories();
    
    /**
     * Returns information about the CMIS repository, the optional capabilities it supports and its 
     * Access Control information if applicable. 
     * @return
     */
    RepositoryInfo repositoryInfo();

    /**
     * Gets repository changes.
     * 
     * @param changeLogToken the change log token to start from or <code>null</code>
     * @param includeProperties indicates if changed properties should be included in
     *            the result
     * @return the changelog events
     */
    @Operation
    ChangeEvents changelog(final String changeLogToken, final boolean includeProperties);

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     * 
     * @param objectId the object id
     */
    @Operation
    CmisObject getObjectById(final String objectId);

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     *
     * @param path path of the object to retrieve
     */
    @Operation
    CmisObject getObjectByPath(final String path);

    /**
     * Creates a new document in the repository.
     * 
     * @param folderPath        Folder in the repository that will hold the document
     * @param filename          name of the file
     * @param content           file content (no byte array or input stream for now)
     * @param mimeType          stream content-type
     * @param versioningState   An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.  Valid values are:
     *     o none:  The document MUST be created as a non-versionable document.
     *     o checkedout: The document MUST be created in the checked-out state.
     *     o major (default): The document MUST be created as a major version
     *     o minor: The document MUST be created as a minor version.
     *
     *  @return the object id of the created 
     */
    @Operation
    ObjectId createDocumentByPath(final String folderPath,
                                  final String filename,
                                  final Object content,
                                  final String mimeType,
                                  final VersioningState versioningState,
                                  final String objectType);

    /**
     * Creates a folder. Note that this is not recusive creation. You just create
     * one folder
     * 
     * @param folderName  folder name (eg: "my documents")
     * @param parentObjectId  Parent folder for the folder being created (eg: repository.rootFolder) 
     */
    ObjectId createFolder(final String folderName, final String parentObjectId); 
                                
    /**
     * Creates a new document in the repository.
     * 
     * @param folderId          Folder Object Id
     * @param filename          name of the file
     * @param content           file content (no byte array or input stream for now)
     * @param mimeType          stream content-type
     * @param versioningState   An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.  Valid values are:
     *     o none:  The document MUST be created as a non-versionable document.
     *     o checkedout: The document MUST be created in the checked-out state.
     *     o major (default): The document MUST be created as a major version
     *     o minor: The document MUST be created as a minor version.
     *
     *  @return the object id of the created 
     */
    @Operation
    ObjectId createDocumentById(final String folderId,
                                  final String filename,
                                  final Object content,
                                  final String mimeType,
                                  final VersioningState versioningState,
                                  final String objectType);
    
    /**
     * Returns the type definition of the given type id.
     * 
     * @param typeId Object type Id
     * @return type of object
     */
    @Operation
    ObjectType getTypeDefinition(final String typeId);
    
    /**
     * Retrieve list of checked out documents.
     * 
     * @param filter comma-separated list of properties to filter
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name
     * @return list of documents
     */
    ItemIterable<Document> getCheckoutDocs(final String filter, final String orderBy);
    
    /**
     * Sends a query to the repository
     * @param statement the query statement (CMIS query language)
     * @param searchAllVersions specifies if the latest and non-latest versions 
     *                          of document objects should be included
     * @param filter comma-separated list of properties to filter
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name
     * @return
     */
    ItemIterable<QueryResult> query(final String statement, final Boolean searchAllVersions, 
                                    final String filter, final String orderBy);
    
    /**
     * Retrieves the parent folders of a fileable cmis object
     * @param cmisObject the object whose parent folders are needed. can be null if "objectId" is set. 
     * @param objectId id of the object whose parent folders are needed. can be null if "object" is set.
     * @return a list of the object's parent folders.
     */
    List<Folder> getParentFolders(final CmisObject cmisObject, final String objectId);
    
    /**
     * Navigates the folder structure.
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
     *          * PARENT: returns the parent Folder
     *          * CHILDREN: returns a CmisObject ItemIterable with
     *                         objects contained in the current folder
     *          * DESCENDANTS: List<Tree<FileableCmisObject>> representing
     *                         the whole descentants tree of the current folder
     *          * TREE: List<Tree<FileableCmisObject>> representing the 
     *                         directory structure under the current folder.                           
     */
    Object folder(final Folder folder, final String folderId, final NavigationOptions get,
                  final Integer depth, final String filter, final String orderBy);

    /**
     * Retrieves the content stream of a Document.
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set. 
     * @param objectId Id of the document from which to get the stream. Can be null if "object" is set.
     * @return The content stream of the document.
     */
    ContentStream getContentStream(final CmisObject cmisObject, final String objectId);
    /**
     * Moves a fileable cmis object from one location to another.
     * @param cmisObject The object to move. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param sourceFolderId Id of the source folder
     * @param targetFolderId Id of the target folder
     * @return The object moved
     */
    FileableCmisObject moveObject(final FileableCmisObject cmisObject,
                            final String objectId,
                            final String sourceFolderId,
                            final String targetFolderId);
    
    /**
     * Update an object's properties
     * @param cmisObject Object to be updated. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param properties The properties to update
     * @return The updated object (a repository might have created a new object)
     */
    CmisObject updateObjectProperties(final CmisObject cmisObject,
                                      final String objectId,
                                      final Map<String, Object> properties);

    /**
     * Remove an object
     * @param cmisObject The object to be deleted. Can be null if "objectId" is set.
     * @param objectId The object's id. Can be null if "cmisObject" is set.
     * @param allVersions If true, deletes all version history of the object. Defaults to "false".
     */
    void delete(final CmisObject cmisObject, final String objectId, final boolean allVersions);
    
    /**
     * Deletes a folder and all subfolders.
     * @param folder Folder Object. Can be null if "folderId" is set. 
     * @param folderId Folder Object id. Can be null if "folder" is set.
     * @param allversions If true, then delete all versions of the document. 
     *                    If false, delete only the document object specified.
     * @param unfile Specifies how the repository must process file-able child- 
     *               or descendant-objects.
     * @param continueOnFailure Specified whether to continue attempting to perform 
     *               this operation even if deletion of a child- or descendant-object 
     *               in the specified folder cannot be deleted or not. 
     * @return a list of object ids which failed to be deleted.
     */
    List<String> deleteTree(final CmisObject folder, final String folderId, final boolean allversions, 
                            final UnfileObject unfile, final boolean continueOnFailure);
    
    /**
     * Returns the relationships if they have been fetched for an object.
     * @param cmisObject the object whose relationships are needed
     * @return list of the object's relationships
     */
    List<Relationship> getObjectRelationships(final CmisObject cmisObject, final String objectId);
    
    /**
     * Returns the ACL if it has been fetched for an object.
     * @param cmisObject the object whose Acl is needed
     * @return the object's Acl
     */
    Acl getAcl(final CmisObject cmisObject, final String objectId);
    
    /**
     * Set the permissions associated with an object.
     * @param cmisObject the object whose Acl is intended to change.
     * @param addAces added access control entities
     * @param removeAces removed access control entities
     * @param aclPropagation wheter to propagate changes or not. can be
     *          (a) REPOSITORYDETERMINED
     *          (b) OBJECTONLY
     *          (c) PROPAGATE
     * @return the new access control list
     */
    Acl applyAcl(final CmisObject cmisObject, final String objectId, final List<Ace> addAces, 
                 final List<Ace> removeAces, final AclPropagation aclPropagation);
    
    /**
     * Retrieve an object's version history
     * @param document the document whose versions are to be retrieved
     * @param objectId Id of the document whose versions are to be retrieved
     * @param filter comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)
     * @param orderBy comma-separated list of query names and the ascending modifier 
     *      "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)
     * @return versions of the document.
     */
    List<Document> getAllVersions(final CmisObject document, final String documentId, 
                                  final String filter, final String orderBy);


    /**
     * Checks out the document and returns the object id of the PWC (private working copy).
     * @param document The document to be checked out. Can be null if "documentId" is set.
     * @param objectId Id of the document to be checked out. Can be null if "document" is set.
     * @return PWC ObjectId
     */
    ObjectId checkOut(final CmisObject document, final String documentId);
    
    /**
     * If applied to a PWC (private working copy) of the document, the check out
     * will be reversed. Otherwise, an exception will be thrown.
     * @param document The checked out document. Can be null if "documentId" is set.
     * @param objectId Id of the checked out document. Can be null if "document" is set.
     */
    void cancelCheckOut(final CmisObject document, final String documentId);
    
    /**
     * If applied to a PWC (private working copy) it performs a check in.
     * Otherwise, an exception will be thrown.
     * @param document The document to check-in. Can be null if "documentId" is set.
     * @param documentId Id of the document to check-in. Can be null if "document" is set.
     * @param content           File content (no byte array or input stream for now)
     * @param filename          Name of the file
     * @param mimeType          Stream content-type
     * 
     * @param major
     * @param checkinComment Check-in comment
     * @return
     */
    ObjectId checkIn(final CmisObject document, final String documentId,
                     final Object content, final String filename, 
                     final String mimeType, boolean major, String checkinComment);
    
    /**
     * Get the policies that are applied to an object.
     * @param cmisObject The document from which to get the stream. Can be null if "objectId" is set. 
     * @param objectId Id of the document from which to get the stream. Can be null if "object" is set.
     * @return List of applied policies
     */
    List<Policy> getAppliedPolicies(final CmisObject cmisObject, final String objectId);
    
}
