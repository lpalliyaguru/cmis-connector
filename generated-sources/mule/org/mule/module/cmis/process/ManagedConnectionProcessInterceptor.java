
package org.mule.module.cmis.process;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessInterceptor;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.module.cmis.adapters.CMISCloudConnectorConnectionIdentifierAdapter;
import org.mule.module.cmis.connection.ConnectionManager;
import org.mule.module.cmis.connection.UnableToAcquireConnectionException;
import org.mule.module.cmis.connection.UnableToReleaseConnectionException;
import org.mule.module.cmis.connectivity.CMISCloudConnectorConnectionKey;
import org.mule.module.cmis.processors.AbstractConnectedProcessor;
import org.mule.security.oauth.callback.ProcessCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:47-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class ManagedConnectionProcessInterceptor<T >
    extends ExpressionEvaluatorSupport
    implements ProcessInterceptor<T, CMISCloudConnectorConnectionIdentifierAdapter>
{

    private static Logger logger = LoggerFactory.getLogger(ManagedConnectionProcessInterceptor.class);
    private final ConnectionManager<CMISCloudConnectorConnectionKey, CMISCloudConnectorConnectionIdentifierAdapter> connectionManager;
    private final MuleContext muleContext;
    private final ProcessInterceptor<T, CMISCloudConnectorConnectionIdentifierAdapter> next;

    public ManagedConnectionProcessInterceptor(ProcessInterceptor<T, CMISCloudConnectorConnectionIdentifierAdapter> next, ConnectionManager<CMISCloudConnectorConnectionKey, CMISCloudConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        this.next = next;
        this.connectionManager = connectionManager;
        this.muleContext = muleContext;
    }

    @Override
    public T execute(ProcessCallback<T, CMISCloudConnectorConnectionIdentifierAdapter> processCallback, CMISCloudConnectorConnectionIdentifierAdapter object, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        CMISCloudConnectorConnectionIdentifierAdapter connection = null;
        CMISCloudConnectorConnectionKey key = null;
        if (hasConnectionKeysOverride(messageProcessor)) {
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_usernameType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method connect can't be null because is not @Optional");
            }
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_passwordType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getPassword()));
            if (_transformedPassword == null) {
                throw new UnableToAcquireConnectionException("Parameter password in method connect can't be null because is not @Optional");
            }
            final String _transformedBaseUrl = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_baseUrlType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getBaseUrl()));
            if (_transformedBaseUrl == null) {
                throw new UnableToAcquireConnectionException("Parameter baseUrl in method connect can't be null because is not @Optional");
            }
            final String _transformedRepositoryId = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_repositoryIdType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getRepositoryId()));
            if (_transformedRepositoryId == null) {
                throw new UnableToAcquireConnectionException("Parameter repositoryId in method connect can't be null because is not @Optional");
            }
            final String _transformedEndpoint = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_endpointType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getEndpoint()));
            final String _transformedConnectionTimeout = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_connectionTimeoutType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getConnectionTimeout()));
            final String _transformedUseAlfrescoExtension = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_useAlfrescoExtensionType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getUseAlfrescoExtension()));
            final String _transformedCxfPortProvider = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_cxfPortProviderType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getCxfPortProvider()));
            key = new CMISCloudConnectorConnectionKey(_transformedUsername, _transformedPassword, _transformedBaseUrl, _transformedRepositoryId, _transformedEndpoint, _transformedConnectionTimeout, _transformedUseAlfrescoExtension, _transformedCxfPortProvider);
        } else {
            key = connectionManager.getEvaluatedConnectionKey(event);
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(("Attempting to acquire connection using "+ key.toString()));
            }
            connection = connectionManager.acquireConnection(key);
            if (connection == null) {
                throw new UnableToAcquireConnectionException();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug((("Connection has been acquired with [id="+ connection.getConnectionIdentifier())+"]"));
                }
            }
            return next.execute(processCallback, connection, messageProcessor, event);
        } catch (Exception e) {
            if (processCallback.getManagedExceptions()!= null) {
                for (Class exceptionClass: ((List<Class<? extends Exception>> ) processCallback.getManagedExceptions())) {
                    if (exceptionClass.isInstance(e)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug((((("An exception ( "+ exceptionClass.getName())+") has been thrown. Destroying the connection with [id=")+ connection.getConnectionIdentifier())+"]"));
                        }
                        try {
                            if (connection!= null) {
                                connectionManager.destroyConnection(key, connection);
                                connection = null;
                            }
                        } catch (Exception innerException) {
                            logger.error(innerException.getMessage(), innerException);
                        }
                    }
                }
            }
            throw e;
        } finally {
            try {
                if (connection!= null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug((("Releasing the connection back into the pool [id="+ connection.getConnectionIdentifier())+"]"));
                    }
                    connectionManager.releaseConnection(key, connection);
                }
            } catch (Exception e) {
                throw new UnableToReleaseConnectionException(e);
            }
        }
    }

    /**
     * Validates that the current message processor has changed any of its connection parameters at processor level. If so, a new CMISCloudConnectorConnectionKey must be generated
     * 
     * @param messageProcessor
     *     the message processor to test against the keys
     * @return
     */
    private Boolean hasConnectionKeysOverride(MessageProcessor messageProcessor) {
        if ((messageProcessor == null)||(!(messageProcessor instanceof AbstractConnectedProcessor))) {
            return false;
        }
        AbstractConnectedProcessor abstractConnectedProcessor = ((AbstractConnectedProcessor) messageProcessor);
        if (abstractConnectedProcessor.getUsername()!= null) {
            return true;
        }
        if (abstractConnectedProcessor.getBaseUrl()!= null) {
            return true;
        }
        return false;
    }

    public T execute(ProcessCallback<T, CMISCloudConnectorConnectionIdentifierAdapter> processCallback, CMISCloudConnectorConnectionIdentifierAdapter object, Filter filter, MuleMessage message)
        throws Exception
    {
        throw new UnsupportedOperationException();
    }

}
