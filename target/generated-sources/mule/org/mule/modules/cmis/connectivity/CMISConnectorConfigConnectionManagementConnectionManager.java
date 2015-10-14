
package org.mule.modules.cmis.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedObjectPool;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultTestResult;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.config.PoolingProfile;
import org.mule.devkit.api.lifecycle.LifeCycleManager;
import org.mule.devkit.api.lifecycle.MuleContextAwareManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorFactory;
import org.mule.devkit.internal.connection.management.ConnectionManagementProcessTemplate;
import org.mule.devkit.internal.connection.management.UnableToAcquireConnectionException;
import org.mule.devkit.internal.connectivity.ConnectivityTestingErrorHandler;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.modules.cmis.CMISConnector;
import org.mule.modules.cmis.Config;
import org.mule.modules.cmis.adapters.CMISConnectorConnectionManagementAdapter;
import org.mule.modules.cmis.model.Authentication;
import org.mule.modules.cmis.model.CMISConnectionType;
import org.mule.modules.cmis.pooling.DevkitGenericKeyedObjectPool;


/**
 * A {@code CMISConnectorConfigConnectionManagementConnectionManager} is a wrapper around {@link CMISConnector } that adds connection management capabilities to the pojo.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class CMISConnectorConfigConnectionManagementConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<CMISConnectorConnectionManagementAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectionManagementConnectionManager<ConnectionManagementConfigCMISConnectorConnectionKey, CMISConnectorConnectionManagementAdapter, Config>
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
    private CMISConnectionType endpoint;
    private Authentication authentication;
    private String cxfPortProvider;
    private String connectionTimeout;
    private boolean useAlfrescoExtension;
    private boolean useCookies;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Connector Pool
     * 
     */
    private KeyedObjectPool connectionPool;
    protected PoolingProfile poolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "CMIS";
    private final static String MODULE_VERSION = "2.1.0";
    private final static String DEVKIT_VERSION = "3.7.1";
    private final static String DEVKIT_BUILD = "UNNAMED.2613.77421cc";
    private final static String MIN_MULE_VERSION = "3.5.0";

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

    /**
     * Sets endpoint
     * 
     * @param value Value to set
     */
    public void setEndpoint(CMISConnectionType value) {
        this.endpoint = value;
    }

    /**
     * Retrieves endpoint
     * 
     */
    public CMISConnectionType getEndpoint() {
        return this.endpoint;
    }

    /**
     * Sets authentication
     * 
     * @param value Value to set
     */
    public void setAuthentication(Authentication value) {
        this.authentication = value;
    }

    /**
     * Retrieves authentication
     * 
     */
    public Authentication getAuthentication() {
        return this.authentication;
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
    public void setUseAlfrescoExtension(boolean value) {
        this.useAlfrescoExtension = value;
    }

    /**
     * Retrieves useAlfrescoExtension
     * 
     */
    public boolean getUseAlfrescoExtension() {
        return this.useAlfrescoExtension;
    }

    /**
     * Sets useCookies
     * 
     * @param value Value to set
     */
    public void setUseCookies(boolean value) {
        this.useCookies = value;
    }

    /**
     * Retrieves useCookies
     * 
     */
    public boolean getUseCookies() {
        return this.useCookies;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets poolingProfile
     * 
     * @param value Value to set
     */
    public void setPoolingProfile(PoolingProfile value) {
        this.poolingProfile = value;
    }

    /**
     * Retrieves poolingProfile
     * 
     */
    public PoolingProfile getPoolingProfile() {
        return this.poolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    public void initialise() {
        connectionPool = new DevkitGenericKeyedObjectPool(new ConnectionManagementConnectorFactory(this), poolingProfile);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public CMISConnectorConnectionManagementAdapter acquireConnection(ConnectionManagementConfigCMISConnectorConnectionKey key)
        throws Exception
    {
        return ((CMISConnectorConnectionManagementAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(ConnectionManagementConfigCMISConnectorConnectionKey key, CMISConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(ConnectionManagementConfigCMISConnectorConnectionKey key, CMISConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, CMISConnectorConnectionManagementAdapter> getProcessTemplate() {
        return new ConnectionManagementProcessTemplate(this, muleContext);
    }

    @Override
    public ConnectionManagementConfigCMISConnectorConnectionKey getDefaultConnectionKey() {
        return new ConnectionManagementConfigCMISConnectorConnectionKey(getBaseUrl(), getUsername(), getPassword(), getRepositoryId());
    }

    @Override
    public ConnectionManagementConfigCMISConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedBaseUrl = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("baseUrl").getGenericType(), null, getBaseUrl()));
            if (_transformedBaseUrl == null) {
                throw new UnableToAcquireConnectionException("Parameter baseUrl in method connect can't be null because is not @Optional");
            }
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("username").getGenericType(), null, getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method connect can't be null because is not @Optional");
            }
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("password").getGenericType(), null, getPassword()));
            if (_transformedPassword == null) {
                throw new UnableToAcquireConnectionException("Parameter password in method connect can't be null because is not @Optional");
            }
            final String _transformedRepositoryId = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("repositoryId").getGenericType(), null, getRepositoryId()));
            return new ConnectionManagementConfigCMISConnectorConnectionKey(_transformedBaseUrl, _transformedUsername, _transformedPassword, _transformedRepositoryId);
        }
        return getDefaultConnectionKey();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

    @Override
    public ConnectionManagementConfigCMISConnectorConnectionKey getConnectionKey(MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return getEvaluatedConnectionKey(event);
    }

    @Override
    public ConnectionManagementConnectionAdapter newConnection() {
        ConfigCMISConnectorAdapter connection = new ConfigCMISConnectorAdapter();
        connection.setEndpoint(getEndpoint());
        connection.setAuthentication(getAuthentication());
        connection.setCxfPortProvider(getCxfPortProvider());
        connection.setConnectionTimeout(getConnectionTimeout());
        connection.setUseAlfrescoExtension(getUseAlfrescoExtension());
        connection.setUseCookies(getUseCookies());
        return connection;
    }

    @Override
    public ConnectionManagementConnectorAdapter newConnector(ConnectionManagementConnectionAdapter<Config, ConnectionManagementConfigCMISConnectorConnectionKey> connection) {
        CMISConnectorConnectionManagementAdapter connector = new CMISConnectorConnectionManagementAdapter();
        connector.setConfig(connection.getStrategy());
        return connector;
    }

    public ConnectionManagementConnectionAdapter getConnectionAdapter(ConnectionManagementConnectorAdapter adapter) {
        CMISConnectorConnectionManagementAdapter connector = ((CMISConnectorConnectionManagementAdapter) adapter);
        ConnectionManagementConnectionAdapter strategy = ((ConnectionManagementConnectionAdapter) connector.getConfig());
        return strategy;
    }

    public TestResult test() {
        try {
            ConfigCMISConnectorAdapter strategy = ((ConfigCMISConnectorAdapter) newConnection());
            ConnectionManagementConnectorAdapter connectorAdapter = newConnector(strategy);
            MuleContextAwareManager.setMuleContext(connectorAdapter, this.muleContext);
            LifeCycleManager.executeInitialiseAndStart(connectorAdapter);
            strategy.test(getDefaultConnectionKey());
            return new DefaultTestResult(org.mule.common.Result.Status.SUCCESS);
        } catch (Exception e) {
            return ((DefaultTestResult) ConnectivityTestingErrorHandler.buildFailureTestResult(e));
        }
    }

}
