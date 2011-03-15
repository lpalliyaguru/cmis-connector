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
