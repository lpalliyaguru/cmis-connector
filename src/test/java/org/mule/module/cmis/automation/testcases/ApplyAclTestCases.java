/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.Principal;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.AccessControlEntryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;
import org.mule.module.cmis.automation.CMISTestParent;
import org.mule.module.cmis.automation.RegressionTests;

public class ApplyAclTestCases extends CMISTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("applyAcl");
			ObjectId result = createDocumentById(rootFolderId(), (String) testObjects.get("filename"), (String) testObjects.get("content"), (String) testObjects.get("mimeType"), 
					(VersioningState) testObjects.get("versioningState"), (String) testObjects.get("objectType"), (Map<String, Object>) testObjects.get("propertiesRef"));
			
			testObjects.put("objectId", result.getId());
			testObjects.put("cmisObjectRef", getObjectById(result.getId()));
			
			Acl acl = getAcl(result.getId());
		    assertEquals(2, acl.getAces().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testApplyAclAdd() {
		try {
			String objectId = (String) testObjects.get("objectId");
			AclPropagation aclPropagation = (AclPropagation) testObjects.get("aclPropagation");
		    Acl acl = getAcl(objectId);
		    
		    List<Ace> removeAces = new ArrayList<Ace>();
		    List<Ace> addAces = new ArrayList<Ace>();
		    
		    Principal principal = getPrincipal(acl);
		    
		    List<String> permissions1 = new ArrayList<String>();
		    permissions1.add("cmis:write");
			AccessControlEntryImpl acei = new AccessControlEntryImpl(principal, permissions1);
		    addAces.add(acei);
			
			Acl result = applyAcl(objectId, aclPropagation, removeAces, addAces);
			
			assertEquals(4, result.getAces().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testApplyAclAdd_With_CmisObjectRef() {
		try {
		    String objectId = (String) testObjects.get("objectId");
		    AclPropagation aclPropagation = (AclPropagation) testObjects.get("aclPropagation");
		    CmisObject cmisObjectRef = (CmisObject) testObjects.get("cmisObjectRef");
		    Acl acl = getAcl(objectId);
		    
		    List<Ace> removeAces = new ArrayList<Ace>();
		    List<Ace> addAces = new ArrayList<Ace>();
		    
		    Principal principal = getPrincipal(acl);
		    
		    List<String> permissions1 = new ArrayList<String>();
		    permissions1.add("cmis:write");
			AccessControlEntryImpl acei = new AccessControlEntryImpl(principal, permissions1);
		    addAces.add(acei);
			
			Acl result = applyAcl(cmisObjectRef, aclPropagation, removeAces, addAces);
			
			assertEquals(4, result.getAces().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testApplyAclRemove() {
		try {
		    String objectId = (String) testObjects.get("objectId");
		    AclPropagation aclPropagation = (AclPropagation) testObjects.get("aclPropagation");
		    Acl acl = getAcl(objectId);
		    
		    List<Ace> removeAces = new ArrayList<Ace>();
		    List<Ace> addAces = new ArrayList<Ace>();
		    
		    Principal principal = getPrincipal(acl);
		    
		    List<String> permissions1 = new ArrayList<String>();
		    permissions1.add("cmis:write");
			AccessControlEntryImpl acei = new AccessControlEntryImpl(principal, permissions1);
		    addAces.add(acei);

			Acl result = applyAcl(objectId, aclPropagation, removeAces, addAces);
			
			assertEquals(4, result.getAces().size());
			
		    List<Ace> removeAces2 = result.getAces();
		    List<Ace> addAces2 = new ArrayList<Ace>();
		   
		    List<String> permissions2 = new ArrayList<String>();
		    permissions2.add("cmis:write");
		    AccessControlEntryImpl acei2 = new AccessControlEntryImpl(principal, permissions2);
		    removeAces.add(acei2);
			
			Acl result2 = applyAcl(objectId, aclPropagation, removeAces2, addAces2);
			
			assertEquals(2, result2.getAces().size());
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
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
