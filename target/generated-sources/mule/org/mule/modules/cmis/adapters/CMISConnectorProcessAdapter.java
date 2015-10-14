
package org.mule.modules.cmis.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.cmis.CMISConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>CMISConnectorProcessAdapter</code> is a wrapper around {@link CMISConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class CMISConnectorProcessAdapter
    extends CMISConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<CMISConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, CMISConnectorCapabilitiesAdapter> getProcessTemplate() {
        final CMISConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,CMISConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, CMISConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, CMISConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
