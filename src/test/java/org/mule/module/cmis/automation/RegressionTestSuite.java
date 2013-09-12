/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.automation.testcases.ApplyAclTestCases;
import org.mule.module.cmis.automation.testcases.ApplyAspectTestCases;
import org.mule.module.cmis.automation.testcases.ApplyPolicyTestCases;
import org.mule.module.cmis.automation.testcases.CancelCheckOutTestCases;
import org.mule.module.cmis.automation.testcases.ChangeLogTestCases;
import org.mule.module.cmis.automation.testcases.CheckInTestCases;
import org.mule.module.cmis.automation.testcases.CheckOutTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByIdFromContentTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByIdTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByPathFromContentTestCases;
import org.mule.module.cmis.automation.testcases.CreateDocumentByPathTestCases;
import org.mule.module.cmis.automation.testcases.CreateFolderTestCases;
import org.mule.module.cmis.automation.testcases.CreateRelationshipTestCases;
import org.mule.module.cmis.automation.testcases.DeleteTestCases;
import org.mule.module.cmis.automation.testcases.DeleteTreeTestCases;
import org.mule.module.cmis.automation.testcases.FolderTestCases;
import org.mule.module.cmis.automation.testcases.GetAclTestCases;
import org.mule.module.cmis.automation.testcases.GetAllVersionsTestCases;
import org.mule.module.cmis.automation.testcases.GetAppliedPoliciesTestCases;
import org.mule.module.cmis.automation.testcases.GetCheckoutDocsTestCases;
import org.mule.module.cmis.automation.testcases.GetContentStreamTestCases;
import org.mule.module.cmis.automation.testcases.GetObjectByIdTestCases;
import org.mule.module.cmis.automation.testcases.GetObjectByPathTestCases;
import org.mule.module.cmis.automation.testcases.GetObjectRelationshipsTestCases;
import org.mule.module.cmis.automation.testcases.GetOrCreateFolderByPathTestCases;
import org.mule.module.cmis.automation.testcases.GetParentFoldersTestCases;
import org.mule.module.cmis.automation.testcases.GetTypeDefinitionTestCases;
import org.mule.module.cmis.automation.testcases.MoveObjectTestCases;
import org.mule.module.cmis.automation.testcases.QueryTestCases;
import org.mule.module.cmis.automation.testcases.RegressionTests;
import org.mule.module.cmis.automation.testcases.RepositoriesTestCases;
import org.mule.module.cmis.automation.testcases.RepositoryInfoTestCases;
import org.mule.module.cmis.automation.testcases.UpdateObjectPropertiesTestCases;

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
	CreateDocumentByIdFromContentTestCases.class,
	CreateDocumentByIdTestCases.class,
	CreateDocumentByPathFromContentTestCases.class,
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
