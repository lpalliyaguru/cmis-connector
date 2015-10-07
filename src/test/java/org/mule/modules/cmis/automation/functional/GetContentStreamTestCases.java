/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.tools.devkit.ctf.configuration.DeploymentProfiles;
import org.mule.tools.devkit.ctf.junit.RunOnlyOn;

public class GetContentStreamTestCases extends AbstractTestCases {

    private ObjectId folderObjectId;
    private ObjectId documentObjectId;

    @Before
    public void setUp() throws Exception {
        testData = TestDataBuilder.getTestData("getContentStreamTestData");
        folderObjectId = getFolderObjectId();
        documentObjectId = getDocumentObjectId(folderObjectId.getId());
    }

    @Test
    @RunOnlyOn(profiles = DeploymentProfiles.embedded)
    public void testGetContentStream() {
        ContentStream result = null;
        try {
            CmisObject cmisObject = getConnector().getObjectById(documentObjectId.getId());
            result = getConnector().getContentStream(cmisObject, documentObjectId.getId());
            StringWriter writer = new StringWriter();
            IOUtils.copy(result.getStream(), writer, Charset.defaultCharset());
            String theString = writer.toString();
            assertEquals(testData.get("contentRef"), theString);
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (result != null) {
                IOUtils.closeQuietly(result.getStream());
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        getConnector().deleteTree(null, folderObjectId.getId(), UnfileObject.DELETE, true, true);
    }
}
