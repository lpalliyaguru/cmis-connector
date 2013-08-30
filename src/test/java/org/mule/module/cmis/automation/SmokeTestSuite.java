package org.mule.module.cmis.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.automation.testcases.ApplyPolicyTestCases;
import org.mule.module.cmis.automation.testcases.CheckInTestCases;
import org.mule.module.cmis.automation.testcases.CheckOutTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByIdFromContentTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByIdTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByPathFromContentTestCases;
import org.mule.module.cmis.automation.testcases.CreateFolderTestCases;
import org.mule.module.cmis.automation.testcases.CreateRelationshipTestCases;
import org.mule.module.cmis.automation.testcases.DeleteTestCases;
import org.mule.module.cmis.automation.testcases.DeleteTreeTestCases;
import org.mule.module.cmis.automation.testcases.GetAclTestCases;
import org.mule.module.cmis.automation.testcases.GetCheckoutDocsTestCases;
import org.mule.module.cmis.automation.testcases.GetObjectByIdTestCases;
import org.mule.module.cmis.automation.testcases.GetParentFoldersTestCases;
import org.mule.module.cmis.automation.testcases.RegressionTests;
import org.mule.module.cmis.automation.testcases.RepositoryInfoTestCases;

@RunWith(Categories.class)
@IncludeCategory(RegressionTests.class)
@SuiteClasses({
	ApplyPolicyTestCases.class,
	CheckInTestCases.class,
	CheckOutTestCases.class,
	CreateDocumentByIdFromContentTestCases.class,
	CreateDocumentByIdTestCases.class,
	CreateDocumentByPathFromContentTestCases.class,
	CreateFolderTestCases.class,
	CreateRelationshipTestCases.class,
	DeleteTestCases.class,
	DeleteTreeTestCases.class,
	GetAclTestCases.class,
	GetCheckoutDocsTestCases.class,
	GetObjectByIdTestCases.class,
	GetParentFoldersTestCases.class,
	RepositoryInfoTestCases.class
})
public class SmokeTestSuite {

}
