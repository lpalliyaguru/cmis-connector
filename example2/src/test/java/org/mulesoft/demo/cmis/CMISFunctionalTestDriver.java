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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
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

    public void testAlfrescoURI() throws Exception 
    {
        final Document doc = mock(Document.class);
        when(doc.getId()).thenReturn("workspace://SpacesStore/96db7144-29b6-4a9e-a658-ea2b3fa1f269");
        when(doc.getName()).thenReturn("README.txt");
        when(doc.getContentStreamMimeType()).thenReturn("text/plain");
        when(doc.getContentStream()).thenReturn(
            new ContentStreamImpl("README.txt", "text/plain", "hello world"));
        
        final MuleEvent event = getTestEvent(doc);
        final SimpleFlowConstruct flow = lookupFlowConstruct("documentURIFlow");
        final MuleEvent responseEvent = flow.process(event);
        Assert.assertEquals(
            "http://cmis.alfresco.com/service/cmis/s/workspace:SpacesStore/i/96db7144-29b6-4a9e-a658-ea2b3fa1f269/content.txt",
            responseEvent.getMessage().getPayload());
    }

    public void testDemoFlow() throws Exception
    {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("fileName", "testFile3");
        params.put("fileUrl", "http://www.librarian.net/dnc/speeches/obama.txt");
        params.put("pastieId", "1872943");
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
