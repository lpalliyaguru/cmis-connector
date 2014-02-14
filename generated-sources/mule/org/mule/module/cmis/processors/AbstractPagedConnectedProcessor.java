
package org.mule.module.cmis.processors;

import javax.annotation.Generated;
import org.mule.streaming.processor.AbstractDevkitBasedPageableMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:47-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public abstract class AbstractPagedConnectedProcessor
    extends AbstractDevkitBasedPageableMessageProcessor
{

    protected Object username;
    protected String _usernameType;
    protected Object password;
    protected String _passwordType;
    protected Object baseUrl;
    protected String _baseUrlType;
    protected Object repositoryId;
    protected String _repositoryIdType;
    protected Object endpoint;
    protected String _endpointType;
    protected Object connectionTimeout;
    protected String _connectionTimeoutType;
    protected Object useAlfrescoExtension;
    protected String _useAlfrescoExtensionType;
    protected Object cxfPortProvider;
    protected String _cxfPortProviderType;

    public AbstractPagedConnectedProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Sets baseUrl
     * 
     * @param value Value to set
     */
    public void setBaseUrl(Object value) {
        this.baseUrl = value;
    }

    /**
     * Retrieves baseUrl
     * 
     */
    public Object getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(Object value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public Object getUsername() {
        return this.username;
    }

    /**
     * Sets connectionTimeout
     * 
     * @param value Value to set
     */
    public void setConnectionTimeout(Object value) {
        this.connectionTimeout = value;
    }

    /**
     * Retrieves connectionTimeout
     * 
     */
    public Object getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * Sets useAlfrescoExtension
     * 
     * @param value Value to set
     */
    public void setUseAlfrescoExtension(Object value) {
        this.useAlfrescoExtension = value;
    }

    /**
     * Retrieves useAlfrescoExtension
     * 
     */
    public Object getUseAlfrescoExtension() {
        return this.useAlfrescoExtension;
    }

    /**
     * Sets cxfPortProvider
     * 
     * @param value Value to set
     */
    public void setCxfPortProvider(Object value) {
        this.cxfPortProvider = value;
    }

    /**
     * Retrieves cxfPortProvider
     * 
     */
    public Object getCxfPortProvider() {
        return this.cxfPortProvider;
    }

    /**
     * Sets repositoryId
     * 
     * @param value Value to set
     */
    public void setRepositoryId(Object value) {
        this.repositoryId = value;
    }

    /**
     * Retrieves repositoryId
     * 
     */
    public Object getRepositoryId() {
        return this.repositoryId;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(Object value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public Object getPassword() {
        return this.password;
    }

    /**
     * Sets endpoint
     * 
     * @param value Value to set
     */
    public void setEndpoint(Object value) {
        this.endpoint = value;
    }

    /**
     * Retrieves endpoint
     * 
     */
    public Object getEndpoint() {
        return this.endpoint;
    }

}
