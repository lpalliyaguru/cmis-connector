/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.junit.Test;

public class RepositoriesTestCases extends AbstractTestCases {

    @Test
    public void testRepositories() {
        try {
            List<Repository> repositories = getConnector().repositories();
            assertTrue(repositories.size() > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
