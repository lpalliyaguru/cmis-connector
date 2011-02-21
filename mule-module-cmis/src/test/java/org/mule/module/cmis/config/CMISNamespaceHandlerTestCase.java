/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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

    
    public void testEmpty() 
    {
        
    }
    public void ignoretestCreateDocumentFlow()throws Exception
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
