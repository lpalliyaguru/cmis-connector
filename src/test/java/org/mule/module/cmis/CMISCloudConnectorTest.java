/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mule.module.cmis.ChemistryCMISFacade.createContentStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test {@link ChemistryCMISFacade} internals
 */
public class CMISCloudConnectorTest {

    private static final String FILENAME = "foo.txt";
    private static final String MEDIA_TYPE = "plain/text";
    private static final String CONTENT = "hello world";

    @Test
    public void createStringContent() throws Exception {
        assertContent(createContentStream(FILENAME, MEDIA_TYPE, CONTENT));
    }

    @Test
    public void createInputStreamContent() throws Exception {
        InputStream is = new ByteArrayInputStream(CONTENT.getBytes());
        assertContent(createContentStream(FILENAME, MEDIA_TYPE, is));
    }

    @Test
    public void createByteArrayContent() throws Exception {
        assertContent(createContentStream(FILENAME, MEDIA_TYPE, CONTENT.getBytes()));
    }
    
    @Test
    public void createDocumentContent() throws Exception {
    	Document doc = mock(Document.class);
    	when(doc.getContentStream()).thenReturn(createContentStream(FILENAME, MEDIA_TYPE, CONTENT));
    	assertContent(createContentStream(FILENAME, MEDIA_TYPE, doc));
	}

    private void assertContent(ContentStream content) throws IOException {
        assertEquals(FILENAME, content.getFileName());
        assertEquals(MEDIA_TYPE, content.getMimeType());
        assertEquals(CONTENT, IOUtils.toString(content.getStream()));
    }
}