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

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Property;

import org.apache.chemistry.opencmis.client.api.ChangeEvents;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

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
     * URL base for the SOAP connector. For example http://cmis.alfresco.com/cmis/
     */
    @Property
    private String baseUrl;

    /**
     * The type of endpoint
     */
    @Property
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
     */
    @Operation
    public RepositoryInfo repositoryInfo()
    {
        return facade.repositoryInfo();
    }

    /**
     * Gets repository changes.
     *
     * @param changeLogToken    the change log token to start from or <code>null</code>
     * @param includeProperties indicates if changed properties should be included in
     *                          the result
     */
    @Operation
    public ChangeEvents changelog(final String changeLogToken, final boolean includeProperties)
    {
        return facade.changelog(changeLogToken, includeProperties);
    }

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     *
     * @param objectId the object id
     */
    @Operation
    public CmisObject getObjectById(final String objectId)
    {
        return facade.getObjectById(objectId);
    }

    /**
     * Returns a CMIS object from the repository and puts it into the cache.
     *
     * @param path path of the object to retrieve
     */
    @Operation
    public CmisObject getObjectByPath(final String path)
    {
        return facade.getObjectByPath(path);
    }

    /**
     * Creates a new document in the repository.
     *
     * @param folderPath      Folder in the repository that will hold the document
     * @param filename        name of the file
     * @param content         file content (no byte array or input stream for now)
     * @param mimeType        stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.  Valid values are:
     *                        o none:  The document MUST be created as a non-versionable document.
     *                        o checkedout: The document MUST be created in the checked-out state.
     *                        o major (default): The document MUST be created as a major version
     *                        o minor: The document MUST be created as a minor version.
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
     * @param folderId        Folder Object Id
     * @param filename        name of the file
     * @param content         file content (no byte array or input stream for now)
     * @param mimeType        stream content-type
     * @param versioningState An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.  Valid values are:
     *                        o none:  The document MUST be created as a non-versionable document.
     *                        o checkedout: The document MUST be created in the checked-out state.
     *                        o major (default): The document MUST be created as a major version
     *                        o minor: The document MUST be created as a minor version.
     * @return the object id of the created
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
     * @param folderName     folder name (eg: "my documents")
     * @param parentObjectId Parent folder for the folder being created (eg: repository.rootFolder)
     */
    @Operation
    public ObjectId createFolder(String folderName, String parentObjectId)
    {
        return facade.createFolder(folderName, parentObjectId);
    }
}

