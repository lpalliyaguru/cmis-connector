/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis;

import org.apache.commons.lang.StringUtils;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.module.cmis.facade.CMISFacade;
import org.mule.module.cmis.facade.CMISFacadeAdaptor;
import org.mule.module.cmis.facade.ChemistryCMISFacade;
import org.mule.module.cmis.model.CMISConnectionType;

@ConnectionManagement(friendlyName = "Configuration")
public class Config {

    // This object will be used to hold the concurrency for the connection manager features
    private final Object threadSafeLock;

    private CMISFacade facade;
    private String connectionIdentifier;

    /**
     * The identifier for the Repository this connector instance works with.
     */
    @Placement(group = "Repository Information")
    @Configurable
    @Optional
    String repositoryId;

    /**
     * The connection time-out specification.
     */
    @Configurable
    @Default("10000")
    String connectionTimeout;

    /**
     * Specifies whether the Alfresco Object Factory implementation should be utilized.
     */
    @Configurable
    @Default("false")
    Boolean useAlfrescoExtension;

    /**
     * Specifies CXF port provider, the CMIS connector includes a default implementation
     */
    @Configurable
    @Default("org.apache.chemistry.opencmis.client.bindings.spi.webservices.CXFPortProvider")
    String cxfPortProvider;

    /**
     * Turn on-off cookies support, allows to set a custom implementation by extending
     * org.apache.chemistry.opencmis.client.bindings.spi.webservices.AbstractPortProvider
     */
    @Configurable
    @Default("false")
    Boolean useCookies;

    /**
     * The type of endpoint.
     * Values allowed: SOAP or ATOM
     */
    @Placement(group = "Repository Information")
    @Configurable
    @Default("ATOM")
    private CMISConnectionType endpoint;

    public Config() {
        threadSafeLock = new Object();
    }


    /**
     * Connects to CMIS
     *
     * @param baseUrl  CMIS repository address
     * @param username CMIS repository username
     * @param password CMIS repository password
     */
    @Connect
    public void connect(@ConnectionKey String baseUrl, @ConnectionKey String username, @Password String password) throws ConnectionException {

        if (StringUtils.isBlank(username)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"username\" attribute of the \"config\" element for the repository connector configuration is " +
                            "empty or missing. This configuration is required in order to provide repository connection " +
                            "parameters to the connector. The connector is currently non-functional.");
        } else if (StringUtils.isBlank(password)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"password\" attribute of the \"config\" element for the repository connector configuration is " +
                            "empty or missing. This configuration is required in order to provide repository connection " +
                            "parameters to the connector. The connector is currently non-functional.");
        } else if (StringUtils.isBlank(baseUrl)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"baseUrl\" attribute of the \"config\" element for the repository connector configuration is " +
                            "empty or missing. This configuration is required in order to provide repository connection " +
                            "parameters to the connector. The connector is currently non-functional.");
        }

        synchronized (threadSafeLock) {
            // Prevent re-initialization
            if (facade == null) {
                setConnectionIdentifier(username + "@" + baseUrl);

                this.facade =
                        CMISFacadeAdaptor.adapt(
                                new ChemistryCMISFacade(
                                        username,
                                        password,
                                        baseUrl,
                                        getRepositoryId(),
                                        getEndpoint(),
                                        getConnectionTimeout(),
                                        getCxfPortProvider(),
                                        getUseAlfrescoExtension(),
                                        getUseCookies()));

                // Force a call to an operation in order to create the client and force authentication
                facade.repositoryInfo();
            }
        }
    }

    /**
     * Test Connectivity to CMIS instance.
     *
     * @param baseUrl  CMIS repository address
     * @param username CMIS repository username
     * @param password CMIS repository password
     */
    @TestConnectivity
    public void testConnect(@ConnectionKey String baseUrl, @ConnectionKey String username, @Password String password) throws ConnectionException {
        this.connect(baseUrl, username, password);

        // Force a call to an operation in order to create the client and force authentication
        facade.repositoryInfo();
    }

    @Disconnect
    public void disconnect() {
        synchronized (threadSafeLock) {
            facade = null;
        }
    }

    @ValidateConnection
    public boolean isConnected() {
        synchronized (threadSafeLock) {
            return facade != null;
        }
    }

    @ConnectionIdentifier
    public String getConnectionIdentifier() {
        return this.connectionIdentifier;
    }

    public void setConnectionIdentifier(String connectionIdentifier) {
        this.connectionIdentifier = connectionIdentifier;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getCxfPortProvider() {
        return cxfPortProvider;
    }

    public void setCxfPortProvider(String cxfPortProvider) {
        this.cxfPortProvider = cxfPortProvider;
    }

    public CMISConnectionType getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(CMISConnectionType endpoint) {
        this.endpoint = endpoint;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Boolean getUseAlfrescoExtension() {
        return useAlfrescoExtension;
    }

    public void setUseAlfrescoExtension(Boolean useAlfrescoExtension) {
        this.useAlfrescoExtension = useAlfrescoExtension;
    }

    public Boolean getUseCookies() {
        return useCookies;
    }

    public void setUseCookies(Boolean useCookies) {
        this.useCookies = useCookies;
    }

    public CMISFacade getFacade() {
        return facade;
    }

    public void setFacade(CMISFacade facade) {
        this.facade = facade;
    }
}
