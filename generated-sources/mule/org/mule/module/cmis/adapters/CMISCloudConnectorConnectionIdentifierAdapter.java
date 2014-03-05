
package org.mule.module.cmis.adapters;

import javax.annotation.Generated;
import org.mule.module.cmis.CMISCloudConnector;
import org.mule.module.cmis.connection.Connection;


/**
 * A <code>CMISCloudConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link CMISCloudConnector } that implements {@link org.mule.devkit.dynamic.api.helper.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-05T04:27:34-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class CMISCloudConnectorConnectionIdentifierAdapter
    extends CMISCloudConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.getConnectionIdentifier();
    }

}
