/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis;

import org.apache.chemistry.opencmis.commons.exceptions.CmisPermissionDeniedException;
import org.apache.commons.lang.StringUtils;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.*;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.module.cmis.model.Authentication;
import org.mule.module.cmis.model.CMISConnectionType;
import org.mule.module.cmis.runtime.CMISFacade;
import org.mule.module.cmis.runtime.CMISFacadeAdaptor;
import org.mule.module.cmis.runtime.ChemistryCMISFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectionManagement(friendlyName = "Configuration")
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    // This object will be used to hold the concurrency for the connection manager features
    private final Object threadSafeLock;

    private CMISFacade facade;
    private String connectionIdentifier;

    /**
     * The type of endpoint. Values allowed: SOAP or ATOM
     */
    @Configurable
    @Placement(order = 1)
    @Default("ATOM")
    private CMISConnectionType endpoint;

    /**
     * Specifies authentication provider, supports HTTP basic authentication and NTLM.
     */
    @Configurable
    @Placement(order = 2)
    @Default("STANDARD")
    Authentication authentication;

    /**
     * Specifies CXF port provider, the CMIS connector includes a default implementation
     */
    @Configurable
    @Placement(order = 3)
    @FriendlyName(value = "Port Provider")
    @Default("org.apache.chemistry.opencmis.client.bindings.spi.webservices.CXFPortProvider")
    String cxfPortProvider;

    /**
     * The connection time-out specification.
     */
    @Configurable
    @Placement(order = 4)
    @Default("10000")
    String connectionTimeout;

    /**
     * Specifies whether the Alfresco Object Factory implementation should be utilized.
     */
    @Configurable
    @Placement(order = 5)
    @Default("false")
    boolean useAlfrescoExtension;

    /**
     * Turn on-off cookies support.
     */
    @Configurable
    @Placement(order = 6)
    @Default("false")
    boolean useCookies;

    public Config() {
        threadSafeLock = new Object();
    }

    /**
     * Connects to CMIS
     *
     * @param baseUrl
     *            CMIS repository address
     * @param username
     *            CMIS repository username
     * @param password
     *            CMIS repository password
     * @param repositoryId
     *            CMIS repository identifier
     */
    @Connect
    public void connect(@ConnectionKey String baseUrl, @ConnectionKey String username, @Password String password, @Optional String repositoryId) throws ConnectionException {

        if (StringUtils.isBlank(username)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"username\" attribute of the \"config\" element for the repository connector configuration is "
                            + "empty or missing. This configuration is required in order to provide repository connection "
                            + "parameters to the connector. The connector is currently non-functional.");
        } else if (StringUtils.isBlank(password)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"password\" attribute of the \"config\" element for the repository connector configuration is "
                            + "empty or missing. This configuration is required in order to provide repository connection "
                            + "parameters to the connector. The connector is currently non-functional.");
        } else if (StringUtils.isBlank(baseUrl)) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                    "The \"baseUrl\" attribute of the \"config\" element for the repository connector configuration is "
                            + "empty or missing. This configuration is required in order to provide repository connection "
                            + "parameters to the connector. The connector is currently non-functional.");
        }

        synchronized (threadSafeLock) {
            // Prevent re-initialization
            if (facade == null) {
                setConnectionIdentifier(username + "@" + baseUrl);
                try {
                    this.facade = CMISFacadeAdaptor.adapt(new ChemistryCMISFacade(username, password, baseUrl.trim(), repositoryId, getEndpoint(), getConnectionTimeout(),
                            getCxfPortProvider(), getUseAlfrescoExtension(), getUseCookies(), getAuthentication()));
                } catch (Exception e) {
                    if (StringUtils.isEmpty(e.getMessage()) || e.getCause() instanceof CmisPermissionDeniedException) {
                        String msg = "Access to the specified resource (Failed to authenticate) has been forbidden. Please verify \"baseUrl\", \"username\", "
                                + "and \"password\" are valid.  The connector is currently non-functional.";
                        logger.error(msg, e);
                        throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null, msg);
                    } else {
                        throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null, e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Test Connectivity to CMIS instance.
     *
     * @param baseUrl
     *            CMIS repository address
     * @param username
     *            CMIS repository username
     * @param password
     *            CMIS repository password
     * @param repositoryId
     *            CMIS repository identifier
     */
    @TestConnectivity
    public void testConnect(@ConnectionKey String baseUrl, @ConnectionKey String username, @Password String password, @Optional String repositoryId) throws ConnectionException {
        this.connect(baseUrl, username, password, repositoryId);

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

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
