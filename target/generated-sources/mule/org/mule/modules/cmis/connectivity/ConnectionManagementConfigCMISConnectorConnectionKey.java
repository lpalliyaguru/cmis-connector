
package org.mule.modules.cmis.connectivity;

import javax.annotation.Generated;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionKey;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class ConnectionManagementConfigCMISConnectorConnectionKey implements ConnectionManagementConnectionKey
{

    /**
     * 
     */
    private String baseUrl;
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
    private String repositoryId;

    public ConnectionManagementConfigCMISConnectorConnectionKey(String baseUrl, String username, String password, String repositoryId) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.repositoryId = repositoryId;
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

    @Override
    public int hashCode() {
        int result = ((this.baseUrl!= null)?this.baseUrl.hashCode(): 0);
        result = ((31 *result)+((this.username!= null)?this.username.hashCode(): 0));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionManagementConfigCMISConnectorConnectionKey)) {
            return false;
        }
        ConnectionManagementConfigCMISConnectorConnectionKey that = ((ConnectionManagementConfigCMISConnectorConnectionKey) o);
        if (((this.baseUrl!= null)?(!this.baseUrl.equals(that.baseUrl)):(that.baseUrl!= null))) {
            return false;
        }
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        return true;
    }

}
