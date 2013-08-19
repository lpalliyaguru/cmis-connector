/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.Principal;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.cmis.VersioningState;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CMISTestParent extends FunctionalTestCase {

	// Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);

	protected static final String[] SPRING_CONFIG_FILES = new String[] { "AutomationSpringBeans.xml" };
	protected static ApplicationContext context;
	protected Map<String, Object> testObjects;
	
	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	protected MessageProcessor lookupFlowConstruct(String name) {
		return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
	}
	
	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
	}
	
	/*
	 * Helper methods below
	 */
	
	protected ObjectId createFolder(String folderName, String parentObjectId) throws Exception {
		testObjects.put("folderName", folderName);
		testObjects.put("parentObjectId", parentObjectId);
		MessageProcessor flow = lookupFlowConstruct("create-folder");
		MuleEvent response = flow.process(getTestEvent(testObjects));

		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected CmisObject getObjectById(String objectId) throws Exception {
		testObjects.put("objectId", objectId);
		MessageProcessor flow = lookupFlowConstruct("get-object-by-id");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (CmisObject) response.getMessage().getPayload();
		
	}
	
	protected Object delete(CmisObject payload, String objectId, boolean allVersions) throws Exception {
		MessageProcessor flow = lookupFlowConstruct("delete");
		MuleEvent event = getTestEvent(payload);
	
		// putting in testObjects as a precaution. objectId and allVersions need to be in event's session scope.
		testObjects.put("objectId", objectId);
		testObjects.put("allVersions", allVersions);
		
		// objectId and allVersions need to be session variables as that is how they are being accessed from automation-test-flows.xml
		// This is being done because a HashMap cannot be the payload (the payload must be a CmisObject)
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MuleEvent response = flow.process(event);
		return response.getMessage().getPayload();
	}
	
	@SuppressWarnings("unchecked")
	protected List<Relationship> getObjectRelationships(CmisObject payload, String objectId) throws Exception {
		testObjects.put("objectId", objectId);
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MessageProcessor flow = lookupFlowConstruct("get-object-relationships");
		MuleEvent response = flow.process(event);
		MuleMessage msg = response.getMessage();
		Object resultPayload = msg.getPayload();
		if(resultPayload == null || resultPayload instanceof NullPayload) {
			return null;
		} else {
			return (List<Relationship>) resultPayload;
		}
	}
	
	protected String rootFolderId() throws Exception {
		MessageProcessor flow = lookupFlowConstruct("repository-info");
		MuleEvent response = flow.process(getTestEvent(testObjects));

		RepositoryInfo result = (RepositoryInfo) response.getMessage().getPayload();
		return result.getRootFolderId();
	}
	
	protected Acl getAcl(CmisObject payload, String objectId) throws Exception {
		testObjects.put("objectId", objectId);
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MessageProcessor flow = lookupFlowConstruct("get-acl");
		MuleEvent response = flow.process(event);
		return (Acl) response.getMessage().getPayload();
	}
	
	@SuppressWarnings("unchecked")
	protected List<Folder> getParentFolders(CmisObject payload, String objectId) throws Exception {
		testObjects.put("objectId", objectId);
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MessageProcessor flow = lookupFlowConstruct("get-parent-folders");
		MuleEvent response = flow.process(event);
		return (List<Folder>) response.getMessage().getPayload();
	}
	
	protected ObjectId createDocumentById(String folderId, String filename, String payload, String mimeType, 
			VersioningState versioningState, String objectType, Map<String, Object> propertiesRef) throws Exception {
		testObjects.put("folderId", folderId);
		testObjects.put("filename", filename);
		testObjects.put("mimeType", mimeType);
		testObjects.put("versioningState", versioningState);
		testObjects.put("objectType", objectType);
		testObjects.put("propertiesRef", propertiesRef);
		
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MessageProcessor flow = lookupFlowConstruct("create-document-by-id");
		MuleEvent response = flow.process(event);
		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected ObjectId createDocumentByIdFromContent(String folderId, String filename, String contentRef, String mimeType, 
			VersioningState versioningState, String objectType, Map<String, Object> propertiesRef) throws Exception {
		testObjects.put("folderId", folderId);
		testObjects.put("filename", filename);
		testObjects.put("mimeType", mimeType);
		testObjects.put("versioningState", versioningState);
		testObjects.put("objectType", objectType);
		testObjects.put("propertiesRef", propertiesRef);
		testObjects.put("contentRef", contentRef);
		
		MessageProcessor flow = lookupFlowConstruct("create-document-by-id-from-content");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected ObjectId createDocumentByPath(String folderPath, String filename, String payload, String mimeType,
			VersioningState versioningState, String objectType, Map<String, Object> propertiesRef, Boolean force) throws Exception {
		testObjects.put("folderPath", folderPath);
		testObjects.put("filename", filename);
		testObjects.put("mimeType", mimeType);
		testObjects.put("versioningState", versioningState);
		testObjects.put("objectType", objectType);
		testObjects.put("propertiesRef", propertiesRef);
		testObjects.put("force", force);
		
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		
		MessageProcessor flow = lookupFlowConstruct("create-document-by-path");
		MuleEvent response = flow.process(event);
		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected ObjectId createDocumentByPathFromContent(String folderPath, String filename, String contentRef, String mimeType,
			VersioningState versioningState, String objectType, Map<String, Object> propertiesRef, Boolean force) throws Exception {
		testObjects.put("folderPath", folderPath);
		testObjects.put("filename", filename);
		testObjects.put("mimeType", mimeType);
		testObjects.put("versioningState", versioningState);
		testObjects.put("objectType", objectType);
		testObjects.put("propertiesRef", propertiesRef);
		testObjects.put("force", force);
		testObjects.put("contentRef", contentRef);
		
		MessageProcessor flow = lookupFlowConstruct("create-document-by-path-from-content");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected Object createRelationship(String parentObjectId, String childObjectId, String relationshipType) throws Exception {
		testObjects.put("parentObjectId", parentObjectId);
		testObjects.put("childObjectId", childObjectId);
		testObjects.put("relationshipType", relationshipType);
		
		MessageProcessor flow = lookupFlowConstruct("create-relationship");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return response.getMessage().getPayload();
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> deleteTree(CmisObject payload, String folderId, Boolean allversions, Boolean continueOnFailure) throws Exception {
		testObjects.put("folderId", folderId);
		testObjects.put("allversions", allversions);
		testObjects.put("continueOnFailure", continueOnFailure);
		
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		MessageProcessor flow = lookupFlowConstruct("delete-tree");
		MuleEvent response = flow.process(event);
		return (List<String>) response.getMessage().getPayload();
	}
	
	protected ContentStream getContentStream(CmisObject payload, String objectId) throws Exception {
		testObjects.put("objectId", objectId);
		
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		MessageProcessor flow = lookupFlowConstruct("get-content-stream");
		MuleEvent response = flow.process(event);
		return (ContentStream) response.getMessage().getPayload();
	}
	
	protected Acl applyAcl(CmisObject payload, String objectId, AclPropagation aclPropagation, List<Ace> removeAces, List<Ace> addAces) throws Exception {
		testObjects.put("objectId", objectId);
		testObjects.put("aclPropagation", aclPropagation);
		testObjects.put("removeAces", removeAces);
		testObjects.put("addAces", addAces);
		
		MuleEvent event = getTestEvent(payload);
		
		for(String key : testObjects.keySet()) {
			event.setSessionVariable(key, testObjects.get(key));
		}
		MessageProcessor flow = lookupFlowConstruct("apply-acl");
		MuleEvent response = flow.process(event);
		return (Acl) response.getMessage().getPayload();
	}
	
	protected Principal getPrincipal(Acl acl) {
		Principal principal = null;
		if(acl != null) {
			List<Ace> aces = acl.getAces();
			if(aces != null && aces.size() > 0) {
				principal = aces.get(0).getPrincipal();
			}
		}
		return principal;
	}
	
	protected ObjectId checkIn(String checkinComment, String documentId, String filename, String content, String mimeType, boolean major, 
			Map<String, Object> properties) throws Exception {

		testObjects.put("checkinComment", checkinComment);
		testObjects.put("documentId", documentId);
		testObjects.put("filename", filename);
		testObjects.put("mimeType", mimeType);
		testObjects.put("contentRef", content);
		testObjects.put("major", major);
		testObjects.put("propertiesRef", properties);
		
		MessageProcessor flow = lookupFlowConstruct("check-in");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (ObjectId) response.getMessage().getPayload();
	}
	
	protected void checkOut(String documentId) throws Exception {
		testObjects.put("documentId", documentId);
		
		MessageProcessor flow = lookupFlowConstruct("check-out");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
}