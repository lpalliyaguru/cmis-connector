
package org.mule.module.cmis.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.module.cmis.CMISCloudConnector;


/**
 * A <code>CMISCloudConnectorCapabilitiesAdapter</code> is a wrapper around {@link CMISCloudConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class CMISCloudConnectorCapabilitiesAdapter
    extends CMISCloudConnector
    implements Capabilities
{


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

}
