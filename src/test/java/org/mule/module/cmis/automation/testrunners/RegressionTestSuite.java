/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis.automation.testrunners;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.automation.RegressionTests;
import org.mule.module.cmis.automation.testcases.*;

@RunWith(Categories.class)
@IncludeCategory(RegressionTests.class)
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
public class RegressionTestSuite {

}
