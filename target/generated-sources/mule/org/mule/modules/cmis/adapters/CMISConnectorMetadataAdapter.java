
package org.mule.modules.cmis.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.modules.cmis.CMISConnector;


/**
 * A <code>CMISConnectorMetadataAdapter</code> is a wrapper around {@link CMISConnector } that adds support for querying metadata about the extension.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class CMISConnectorMetadataAdapter
    extends CMISConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "CMIS";
    private final static String MODULE_VERSION = "2.1.0";
    private final static String DEVKIT_VERSION = "3.7.1";
    private final static String DEVKIT_BUILD = "UNNAMED.2613.77421cc";
    private final static String MIN_MULE_VERSION = "3.5.0";

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

}
