/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.cmis;

import static org.junit.Assert.*;
import static org.mule.module.cmis.ChemistryCMISFacade.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/** Test {@link ChemistryCMISFacade} internals */
public class CMISCloudConnectorTest
{
    private final String filename = "foo.txt";
    private final String mediaType = "plain/text";
    private final String content = "hello world";

    @Test
    public void createStringContent() throws Exception
    {
        assertContent(createContentStream(filename, mediaType, content));
    }

    @Test
    public void createInputStreamContent() throws Exception
    {
        final InputStream is = new ByteArrayInputStream(content.getBytes());
        assertContent(createContentStream(filename, mediaType, is));
    }

    @Test
    public void createByteArrayContent() throws Exception
    {
        assertContent(createContentStream(filename, mediaType, content.getBytes()));
    }
    
    private void assertContent(final ContentStream content) throws IOException
    {
        assertEquals(filename, content.getFileName());
        assertEquals(mediaType, content.getMimeType());
        assertEquals(this.content, IOUtils.toString(content.getStream()));
    }
}
