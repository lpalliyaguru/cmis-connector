package org.mule.module.cmis.config;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class CMISNamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "cmis-namespace-config.xml";
    }

    
    public void testCreateDocumentFlow()throws Exception
    {
        final SimpleFlowConstruct flow = lookupFlowConstruct("createDocumentFlow");
        final Map<String, Object>map = new HashMap<String, Object>();
        map.put("content", new ByteArrayInputStream("hola!".getBytes()));
        final MuleEvent event = getTestEvent(map);
        final MuleEvent responseEvent = flow.process(event);
        InputStream is = (InputStream) responseEvent.getMessage().getPayload();
        String s = IOUtils.toString(is);
        assertEquals("hola!", s);
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
