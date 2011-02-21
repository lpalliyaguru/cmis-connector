
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

@Connector(namespacePrefix = "cmis", namespaceUri = "http://www.mulesoft.org/schema/mule/cmis")
public class CMISCloudConnector implements Initialisable, CMISFacade
{
    /** credentials: username */
    @Property
    private String username;
    /** credentials: password */
    @Property
    private String password;
    /** The identifier for the Repository that this connector instance works with */
    @Property
    private String repositoryId;
    /** URL base for the SOAP connector. For example http://cmis.alfresco.com/cmis/ */
    @Property
    private String baseUrl;

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
            facade = new ChemistryCMISFacade(username, password, repositoryId, baseUrl);
        }
    }

    @Operation
    public RepositoryInfo repositoryInfo()
    {
        return facade.repositoryInfo();
    }

    @Operation
    public ChangeEvents changelog(final String changeLogToken, final boolean includeProperties)
    {
        return facade.changelog(changeLogToken, includeProperties);
    }

    @Operation
    public CmisObject getObjectById(final String objectId)
    {
        return facade.getObjectById(objectId);
    }

    @Operation
    public CmisObject getObjectByPath(final String path)
    {
        return facade.getObjectByPath(path);
    }

    @Operation
    public ObjectId createDocumentByPath(final String folderPath,
                                         final String filename,
                                         final Object content,
                                         final String mimeType,
                                         final String versioningState,
                                         final String objectType)
    {
        return facade.createDocumentByPath(folderPath, filename, content, mimeType, versioningState,
            objectType);
    }

    @Operation
    public ObjectId createDocumentById(final String folderId,
                                       final String filename,
                                       final Object content,
                                       final String mimeType,
                                       final String versioningState,
                                       final String objectType)
    {
        return facade.createDocumentById(folderId, filename, content, mimeType, versioningState, 
                                         objectType);
    }

    public ObjectId createFolder(String folderName, String parentObjectId)
    {
        return facade.createFolder(folderName, parentObjectId);
    }
}

