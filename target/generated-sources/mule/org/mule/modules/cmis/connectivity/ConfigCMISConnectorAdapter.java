
package org.mule.modules.cmis.connectivity;

import javax.annotation.Generated;
import org.mule.api.ConnectionException;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.TestableConnection;
import org.mule.modules.cmis.Config;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class ConfigCMISConnectorAdapter
    extends Config
    implements ConnectionManagementConnectionAdapter<Config, ConnectionManagementConfigCMISConnectorConnectionKey> , TestableConnection<ConnectionManagementConfigCMISConnectorConnectionKey>
{


    @Override
    public void connect(ConnectionManagementConfigCMISConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getBaseUrl(), connectionKey.getUsername(), connectionKey.getPassword(), connectionKey.getRepositoryId());
    }

    @Override
    public void test(ConnectionManagementConfigCMISConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.testConnect(connectionKey.getBaseUrl(), connectionKey.getUsername(), connectionKey.getPassword(), connectionKey.getRepositoryId());
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public String connectionId() {
        return super.getConnectionIdentifier();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public Config getStrategy() {
        return this;
    }

}
