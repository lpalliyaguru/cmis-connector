/**
 * Mule CMIS Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mulesoft.demo.cmis;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

public class CMISFunctionalTestDriver extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    public void testDemoFlow() throws Exception
    {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("fileName", "testFile7");
        params.put("pastieId", "78312");
        params.put("checkInComment", "Update text file with a speech by Obama.");
        final MuleEvent event = getTestEvent(params);
        final SimpleFlowConstruct flow = lookupFlowConstruct("updateDocumentFlow");
        final MuleEvent responseEvent = flow.process(event);
    }
    
    private SimpleFlowConstruct lookupFlowConstruct(final String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
