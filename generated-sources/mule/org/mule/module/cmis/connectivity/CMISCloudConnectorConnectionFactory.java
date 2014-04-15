
package org.mule.module.cmis.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.module.cmis.adapters.CMISCloudConnectorConnectionIdentifierAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T03:23:24-05:00", comments = "Build master.1915.dd1962d")
public class CMISCloudConnectorConnectionFactory implements KeyedPoolableObjectFactory
{

    private static Logger logger = LoggerFactory.getLogger(CMISCloudConnectorConnectionFactory.class);
    private CMISCloudConnectorConnectionManager connectionManager;

    public CMISCloudConnectorConnectionFactory(CMISCloudConnectorConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Object makeObject(Object key)
        throws Exception
    {
        if (!(key instanceof CMISCloudConnectorConnectionKey)) {
            if (key == null) {
                logger.warn("Connection key is null");
            } else {
                logger.warn("Cannot cast key of type ".concat(key.getClass().getName().concat(" to ").concat("org.mule.module.cmis.connectivity.CMISCloudConnectorConnectionKey")));
            }
            throw new RuntimeException("Invalid key type ".concat(key.getClass().getName()));
        }
        CMISCloudConnectorConnectionIdentifierAdapter connector = new CMISCloudConnectorConnectionIdentifierAdapter();
        if (connector instanceof MuleContextAware) {
            ((MuleContextAware) connector).setMuleContext(connectionManager.getMuleContext());
        }
        if (connector instanceof Initialisable) {
            ((Initialisable) connector).initialise();
        }
        if (connector instanceof Startable) {
            ((Startable) connector).start();
        }
        if (!connector.isConnected()) {
            connector.connect(((CMISCloudConnectorConnectionKey) key).getUsername(), ((CMISCloudConnectorConnectionKey) key).getPassword(), ((CMISCloudConnectorConnectionKey) key).getBaseUrl(), ((CMISCloudConnectorConnectionKey) key).getRepositoryId(), ((CMISCloudConnectorConnectionKey) key).getEndpoint(), ((CMISCloudConnectorConnectionKey) key).getConnectionTimeout(), ((CMISCloudConnectorConnectionKey) key).getUseAlfrescoExtension(), ((CMISCloudConnectorConnectionKey) key).getCxfPortProvider(), ((CMISCloudConnectorConnectionKey) key).getUseCookies());
        }
        return connector;
    }

    public void destroyObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof CMISCloudConnectorConnectionKey)) {
            if (key == null) {
                logger.warn("Connection key is null");
            } else {
                logger.warn("Cannot cast key of type ".concat(key.getClass().getName().concat(" to ").concat("org.mule.module.cmis.connectivity.CMISCloudConnectorConnectionKey")));
            }
            throw new RuntimeException("Invalid key type ".concat(key.getClass().getName()));
        }
        if (!(obj instanceof CMISCloudConnectorConnectionIdentifierAdapter)) {
            if (obj == null) {
                logger.warn("Connector is null");
            } else {
                logger.warn("Cannot cast connector of type ".concat(obj.getClass().getName().concat(" to ").concat("org.mule.module.cmis.adapters.CMISCloudConnectorConnectionIdentifierAdapter")));
            }
            throw new RuntimeException("Invalid connector type ".concat(obj.getClass().getName()));
        }
        try {
            ((CMISCloudConnectorConnectionIdentifierAdapter) obj).disconnect();
        } catch (Exception e) {
            throw e;
        } finally {
            if (((CMISCloudConnectorConnectionIdentifierAdapter) obj) instanceof Stoppable) {
                ((Stoppable) obj).stop();
            }
            if (((CMISCloudConnectorConnectionIdentifierAdapter) obj) instanceof Disposable) {
                ((Disposable) obj).dispose();
            }
        }
    }

    public boolean validateObject(Object key, Object obj) {
        if (!(obj instanceof CMISCloudConnectorConnectionIdentifierAdapter)) {
            if (obj == null) {
                logger.warn("Connector is null");
            } else {
                logger.warn("Cannot cast connector of type ".concat(obj.getClass().getName().concat(" to ").concat("org.mule.module.cmis.adapters.CMISCloudConnectorConnectionIdentifierAdapter")));
            }
            throw new RuntimeException("Invalid connector type ".concat(obj.getClass().getName()));
        }
        try {
            return ((CMISCloudConnectorConnectionIdentifierAdapter) obj).isConnected();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void activateObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof CMISCloudConnectorConnectionKey)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof CMISCloudConnectorConnectionIdentifierAdapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
            if (!((CMISCloudConnectorConnectionIdentifierAdapter) obj).isConnected()) {
                ((CMISCloudConnectorConnectionIdentifierAdapter) obj).connect(((CMISCloudConnectorConnectionKey) key).getUsername(), ((CMISCloudConnectorConnectionKey) key).getPassword(), ((CMISCloudConnectorConnectionKey) key).getBaseUrl(), ((CMISCloudConnectorConnectionKey) key).getRepositoryId(), ((CMISCloudConnectorConnectionKey) key).getEndpoint(), ((CMISCloudConnectorConnectionKey) key).getConnectionTimeout(), ((CMISCloudConnectorConnectionKey) key).getUseAlfrescoExtension(), ((CMISCloudConnectorConnectionKey) key).getCxfPortProvider(), ((CMISCloudConnectorConnectionKey) key).getUseCookies());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void passivateObject(Object key, Object obj)
        throws Exception
    {
    }

}
