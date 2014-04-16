
package org.mule.module.cmis.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class CMISCloudConnectorConnectionKey {

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;
    /**
     * 
     */
    private String baseUrl;
    /**
     * 
     */
    private String repositoryId;
    /**
     * 
     */
    private String endpoint;
    /**
     * 
     */
    private String connectionTimeout;
    /**
     * 
     */
    private String useAlfrescoExtension;
    /**
     * 
     */
    private String cxfPortProvider;
    /**
     * 
     */
    private Boolean useCookies;

    public CMISCloudConnectorConnectionKey(String username, String password, String baseUrl, String repositoryId, String endpoint, String connectionTimeout, String useAlfrescoExtension, String cxfPortProvider, Boolean useCookies) {
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.repositoryId = repositoryId;
        this.endpoint = endpoint;
        this.connectionTimeout = connectionTimeout;
        this.useAlfrescoExtension = useAlfrescoExtension;
        this.cxfPortProvider = cxfPortProvider;
        this.useCookies = useCookies;
    }

    /**
     * Sets baseUrl
     * 
     * @param value Value to set
     */
    public void setBaseUrl(String value) {
        this.baseUrl = value;
    }

    /**
     * Retrieves baseUrl
     * 
     */
    public String getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets connectionTimeout
     * 
     * @param value Value to set
     */
    public void setConnectionTimeout(String value) {
        this.connectionTimeout = value;
    }

    /**
     * Retrieves connectionTimeout
     * 
     */
    public String getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * Sets useAlfrescoExtension
     * 
     * @param value Value to set
     */
    public void setUseAlfrescoExtension(String value) {
        this.useAlfrescoExtension = value;
    }

    /**
     * Retrieves useAlfrescoExtension
     * 
     */
    public String getUseAlfrescoExtension() {
        return this.useAlfrescoExtension;
    }

    /**
     * Sets useCookies
     * 
     * @param value Value to set
     */
    public void setUseCookies(Boolean value) {
        this.useCookies = value;
    }

    /**
     * Retrieves useCookies
     * 
     */
    public Boolean getUseCookies() {
        return this.useCookies;
    }

    /**
     * Sets cxfPortProvider
     * 
     * @param value Value to set
     */
    public void setCxfPortProvider(String value) {
        this.cxfPortProvider = value;
    }

    /**
     * Retrieves cxfPortProvider
     * 
     */
    public String getCxfPortProvider() {
        return this.cxfPortProvider;
    }

    /**
     * Sets repositoryId
     * 
     * @param value Value to set
     */
    public void setRepositoryId(String value) {
        this.repositoryId = value;
    }

    /**
     * Retrieves repositoryId
     * 
     */
    public String getRepositoryId() {
        return this.repositoryId;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets endpoint
     * 
     * @param value Value to set
     */
    public void setEndpoint(String value) {
        this.endpoint = value;
    }

    /**
     * Retrieves endpoint
     * 
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public int hashCode() {
        int result = ((this.username!= null)?this.username.hashCode(): 0);
        result = ((31 *result)+((this.baseUrl!= null)?this.baseUrl.hashCode(): 0));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CMISCloudConnectorConnectionKey)) {
            return false;
        }
        CMISCloudConnectorConnectionKey that = ((CMISCloudConnectorConnectionKey) o);
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        if (((this.baseUrl!= null)?(!this.baseUrl.equals(that.baseUrl)):(that.baseUrl!= null))) {
            return false;
        }
        return true;
    }

}
