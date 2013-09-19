/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Policy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;

public class GetAppliedPoliciesTestCases extends CMISTestParent {


	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getAppliedPolicies");
			
			String rootFolderId = rootFolderId();
			String filename = testObjects.get("filename").toString();
			String mimeType = testObjects.get("mimeType").toString();
			String content = testObjects.get("content").toString();
			String objectType = testObjects.get("objectType").toString();
			Map<String, Object> propertiesRef = (Map<String, Object>) testObjects.get("propertiesRef");
			VersioningState versioningState = (VersioningState) testObjects.get("versioningState");
			
			ObjectId documentObjectId = createDocumentById(rootFolderId, filename, content, mimeType, versioningState, objectType, propertiesRef);
			testObjects.put("objectId", documentObjectId.getId());
			
			List<ObjectId> policyIds = (List<ObjectId>) testObjects.get("policyIdsRef");
			applyPolicy(documentObjectId.getId(), policyIds);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetAppliedPolicies() {
		try {
			List<ObjectId> policyIds = (List<ObjectId>) testObjects.get("policyIdsRef");
			
			MessageProcessor flow = lookupMessageProcessor("get-applied-policies");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Policy> policies = (List<Policy>) response.getMessage().getPayload();
			assertTrue(policies.size() == policyIds.size());
			
			for (Policy policy : policies) {
				boolean contains = false;
				for (ObjectId policyId : policyIds) {
					if (policyId.getId().equals(policy.getId())) {
						contains = true; 
						break;
					}
				}
				assertTrue(contains);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String objectId = (String) testObjects.get("objectId");
			delete(objectId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
