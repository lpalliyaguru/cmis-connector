/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.cmis.automation.testcases;

import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;
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
	
	protected String rootFolderId() throws Exception {
		MessageProcessor flow = lookupFlowConstruct("repository-info");
		MuleEvent response = flow.process(getTestEvent(testObjects));

		RepositoryInfo result = (RepositoryInfo) response.getMessage().getPayload();
		return result.getRootFolderId();
	}
}
