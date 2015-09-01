/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.CMISConnector;
import org.mule.module.cmis.automation.functional.*;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;
import org.mule.tools.devkit.ctf.platform.PlatformManager;

@RunWith(Suite.class)
@SuiteClasses({
        ApplyAclTestCases.class,
        ApplyAspectTestCases.class,
        ApplyPolicyTestCases.class,
        CancelCheckOutTestCases.class,
        ChangeLogTestCases.class,
        CheckInTestCases.class,
        CheckOutTestCases.class,
        CreateDocumentByIdTestCases.class,
        CreateDocumentByPathTestCases.class,
        CreateFolderTestCases.class,
        CreateRelationshipTestCases.class,
        DeleteTestCases.class,
        DeleteTreeTestCases.class,
        FolderTestCases.class,
        GetAclTestCases.class,
        GetAllVersionsTestCases.class,
        GetAppliedPoliciesTestCases.class,
        GetCheckoutDocsTestCases.class,
        GetContentStreamTestCases.class,
        GetObjectByIdTestCases.class,
        GetObjectByPathTestCases.class,
        GetObjectRelationshipsTestCases.class,
        GetOrCreateFolderByPathTestCases.class,
        GetParentFoldersTestCases.class,
        GetTypeDefinitionTestCases.class,
        MoveObjectTestCases.class,
        QueryTestCases.class,
        RepositoriesTestCases.class,
        RepositoryInfoTestCases.class,
        UpdateObjectPropertiesTestCases.class
})
public class FunctionalTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(CMISConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext<CMISConnector> context = ConnectorTestContext.getInstance(CMISConnector.class);
        PlatformManager platform = context.getPlatformManager();
        platform.shutdown();
    }

}
