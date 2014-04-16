
package org.mule.module.cmis.adapters;

import javax.annotation.Generated;
import org.mule.module.cmis.CMISCloudConnector;
import org.mule.module.cmis.connection.Connection;


/**
 * A <code>CMISCloudConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link CMISCloudConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class CMISCloudConnectorConnectionIdentifierAdapter
    extends CMISCloudConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.getConnectionIdentifier();
    }

}
