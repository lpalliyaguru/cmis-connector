/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testrunners;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.automation.RegressionTests;
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
