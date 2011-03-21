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

import static org.mockito.Mockito.*;
import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

import junit.framework.Assert;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

public class CMISFunctionalTestDriver extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    public void testWholeFlow() throws Exception {
        final MuleEvent event = getTestEvent("");
        final SimpleFlowConstruct flow = lookupFlowConstruct("cmisDemo");
        final MuleEvent responseEvent = flow.process(event);
//        System.out.println(responseEvent.getMessage().getPayload());
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
    public void testCreateMessage() throws Exception
    {
        final Document doc = mock(Document.class);
        when(doc.getId()).thenReturn("workspace://SpacesStore/96db7144-29b6-4a9e-a658-ea2b3fa1f269");
        when(doc.getName()).thenReturn("README.txt");
        when(doc.getName()).thenReturn("README.txt");
        when(doc.getContentStreamMimeType()).thenReturn("text/plain");
        when(doc.getContentStream()).thenReturn(
            new ContentStreamImpl("README.txt", "text/plain", "hello world"));

        final MuleEvent event = getTestEvent(doc);
        final SimpleFlowConstruct flow = lookupFlowConstruct("renderCreationMessage");
        final MuleEvent responseEvent = flow.process(event);
        System.out.println(responseEvent.getMessage().getPayload());
    }

    public void testCreateMessageContentTextPlain() throws Exception
    {
        final Document doc = mock(Document.class);
        when(doc.getName()).thenReturn("README.txt");
        when(doc.getContentStreamMimeType()).thenReturn("text/plain");
        when(doc.getContentStream()).thenReturn(
            new ContentStreamImpl("README.txt", "text/plain", "hello world"));

        final MuleEvent event = getTestEvent(doc);
        final SimpleFlowConstruct flow = lookupFlowConstruct("abstractDocumentCreationFlow");
        final MuleEvent responseEvent = flow.process(event);
        Assert.assertEquals("`hello world'", responseEvent.getMessage().getPayload());
    }
    
    public void testCreateMessageContentUnsupported() throws Exception
    {
        final Document doc = mock(Document.class);
        when(doc.getName()).thenReturn("README.xml");
        when(doc.getContentStreamMimeType()).thenReturn("application/xml");
        when(doc.getContentStream()).thenReturn(
            new ContentStreamImpl("README.xml", "application/xml", "hello world"));

        final MuleEvent event = getTestEvent(doc);
        final SimpleFlowConstruct flow = lookupFlowConstruct("abstractDocumentCreationFlow");
        final MuleEvent responseEvent = flow.process(event);
        Assert.assertEquals("", responseEvent.getMessage().getPayload());
    }

    public void ignoreTestFoo() throws Exception
    {
        final MuleEvent event = getTestEvent("http://google.com");
        final SimpleFlowConstruct flow = lookupFlowConstruct("shortLinkFlow");
        final MuleEvent responseEvent = flow.process(event);
        System.out.println(responseEvent.getMessage().getPayload());

    }

    private SimpleFlowConstruct lookupFlowConstruct(final String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
