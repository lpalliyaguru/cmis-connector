/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.functional;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.Principal;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.module.cmis.CMISConnector;
import org.mule.module.cmis.model.VersioningState;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import java.util.List;
import java.util.Map;

public class AbstractTestCases extends AbstractTestCase<CMISConnector> {

    @Rule
    public Timeout globalTimeout = new Timeout(600000);

    protected final int GET_CHECKOUT_DOCS_DELAY = 20000;
    Map<String, Object> testData;

    public AbstractTestCases() {
        super(CMISConnector.class);
    }

    protected String getRootFolderId() {
        return getConnector().repositoryInfo().getRootFolderId();
    }

    protected ObjectId getFolderObjectId() {
        return getConnector().createFolder((String) testData.get("folderName"), getRootFolderId());
    }

    protected ObjectId getDocumentObjectId(String folderId) {
        return getConnector().createDocumentById(folderId, (String) testData.get("filename"), testData.get("contentRef"), (String) testData.get("mimeType"),
                (VersioningState) testData.get("versioningState"), (String) testData.get("objectType"), (Map<String, Object>) testData.get("propertiesRef"));
    }

    protected Principal getPrincipal(Acl acl) {
        Principal principal = null;
        if (acl != null) {
            List<Ace> aces = acl.getAces();
            if (aces != null && aces.size() > 0) {
                principal = aces.get(0).getPrincipal();
            }
        }
        return principal;
    }
}
