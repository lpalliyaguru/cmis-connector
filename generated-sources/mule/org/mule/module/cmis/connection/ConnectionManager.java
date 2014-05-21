
package org.mule.module.cmis.connection;

import javax.annotation.Generated;


/**
 * Wrapper around {@link org.mule.api.annotations.Connector} annotated class that will infuse it with connection management capabilities. <p/> It can receive a {@link org.mule.config.PoolingProfile} which is a configuration object used to define the connection pooling parameters.
 * @param <K> Connection key
 * @param <C> Actual connector object that represents a connection
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public interface ConnectionManager<K, C extends Connection >{

     /**
     * Set the connection pooling profile
     *
     * @param value PoolingProfile representing the pooling parameters
     */
    void setConnectionPoolingProfile(org.mule.config.PoolingProfile value);

    /**
     * Retrieve the connection pooling profile
     *
     * @return PoolingProfile representing the pooling parameters
     */
    org.mule.config.PoolingProfile getConnectionPoolingProfile();

    /**
     * Borrow a connection from the pool
     *
     * @param connectorKey Key used to borrow the connector
     * @return An existing connector, or a newly created one
     * @throws Exception If the connection cannot be created
     */
    C acquireConnection(K connectorKey) throws Exception;

    /**
     * Return a connection to the pool
     *
     * @param connectorKey Key used to borrow the connector
     * @param connector    connector to be returned to the pool
     * @throws Exception If the connection cannot be returned
     */
    void releaseConnection(K connectorKey, C connector) throws Exception;

    /**
     * Destroy a connection
     *
     * @param connectorKey Key used to borrow the connector
     * @param connector    Connector to be destroyed
     * @throws Exception If the connection could not be destroyed.
     */
    void destroyConnection(K connectorKey, C connector) throws Exception;

    /**
     * Retrieve the default connection key
     *
     * @return The default connection key
     */
    K getDefaultConnectionKey();


    /**
     * Retrieve the connection key evaluated with the received mule event
     *
     * @return The default connection key
     */

    K getEvaluatedConnectionKey(org.mule.api.MuleEvent event) throws Exception;

    /**
     * Retrieve the reconnection strategy used by this connection
     * manager.
     *
     * @return The reconnection strategy {@see RetryPolicyTemplate}
     */
    org.mule.api.retry.RetryPolicyTemplate getRetryPolicyTemplate();

}