/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
