/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.cmis.config;

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
import org.mule.module.cmis.CMISCloudConnector;

public class CMISCloudConnectorNamespaceHandler extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        registerPojo("config", CMISCloudConnector.class);
        InvokerMessageProcessorDefinitionParser parser = null;

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "repositoryInfo", new String[] {});
        registerMuleBeanDefinitionParser("repository-info", parser);

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "changelog", new String[] { 
            "changeLogToken", "includeProperties" });
        registerMuleBeanDefinitionParser("changelog", parser);

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "getObjectById", new String[] {"objectId"});
        registerMuleBeanDefinitionParser("get-object-by-id", parser);

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "getObjectByPath", new String[] {"path"});
        registerMuleBeanDefinitionParser("get-object-by-path", parser);

        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "createDocumentByPath", 
            new String[] {"folderPath", "filename", "content", "mimeType", "versioningState", 
                          "objectType" });
        registerMuleBeanDefinitionParser("create-document-by-path", parser);
        
        parser = new InvokerMessageProcessorDefinitionParser("messageProcessor",
            CMISCloudConnector.class, "createDocumentById", 
            new String[] {"folderId", "filename", "content", "mimeType", "versioningState", 
                          "objectType" });
        registerMuleBeanDefinitionParser("create-document-by-id", parser);
        
    }
}
