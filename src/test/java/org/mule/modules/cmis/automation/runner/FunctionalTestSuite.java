/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.cmis.CMISConnector;
import org.mule.modules.cmis.automation.functional.ApplyAclTestCases;
import org.mule.modules.cmis.automation.functional.ApplyAspectTestCases;
import org.mule.modules.cmis.automation.functional.ApplyPolicyTestCases;
import org.mule.modules.cmis.automation.functional.CancelCheckOutTestCases;
import org.mule.modules.cmis.automation.functional.ChangeLogTestCases;
import org.mule.modules.cmis.automation.functional.CheckInTestCases;
import org.mule.modules.cmis.automation.functional.CheckOutTestCases;
import org.mule.modules.cmis.automation.functional.CreateDocumentByIdTestCases;
import org.mule.modules.cmis.automation.functional.CreateDocumentByPathTestCases;
import org.mule.modules.cmis.automation.functional.CreateFolderTestCases;
import org.mule.modules.cmis.automation.functional.CreateRelationshipTestCases;
import org.mule.modules.cmis.automation.functional.DeleteTestCases;
import org.mule.modules.cmis.automation.functional.DeleteTreeTestCases;
import org.mule.modules.cmis.automation.functional.FolderTestCases;
import org.mule.modules.cmis.automation.functional.GetAclTestCases;
import org.mule.modules.cmis.automation.functional.GetAllVersionsTestCases;
import org.mule.modules.cmis.automation.functional.GetAppliedPoliciesTestCases;
import org.mule.modules.cmis.automation.functional.GetCheckoutDocsTestCases;
import org.mule.modules.cmis.automation.functional.GetContentStreamTestCases;
import org.mule.modules.cmis.automation.functional.GetObjectByIdTestCases;
import org.mule.modules.cmis.automation.functional.GetObjectByPathTestCases;
import org.mule.modules.cmis.automation.functional.GetObjectRelationshipsTestCases;
import org.mule.modules.cmis.automation.functional.GetOrCreateFolderByPathTestCases;
import org.mule.modules.cmis.automation.functional.GetParentFoldersTestCases;
import org.mule.modules.cmis.automation.functional.GetTypeDefinitionTestCases;
import org.mule.modules.cmis.automation.functional.MoveObjectTestCases;
import org.mule.modules.cmis.automation.functional.QueryTestCases;
import org.mule.modules.cmis.automation.functional.RepositoriesTestCases;
import org.mule.modules.cmis.automation.functional.RepositoryInfoTestCases;
import org.mule.modules.cmis.automation.functional.UpdateObjectPropertiesTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

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
        UpdateObjectPropertiesTestCases.class })
public class FunctionalTestSuite {

    @BeforeClass
    public static void initialiseSuite() {
        ConnectorTestContext.initialize(CMISConnector.class);
    }

    @AfterClass
    public static void shutdownSuite() throws Exception {
        ConnectorTestContext.shutDown();
    }

}
