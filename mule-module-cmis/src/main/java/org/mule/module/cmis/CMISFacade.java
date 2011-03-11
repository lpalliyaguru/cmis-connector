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

import org.mule.tools.cloudconnect.annotations.Operation;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

public interface CMISFacade
{

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
}
