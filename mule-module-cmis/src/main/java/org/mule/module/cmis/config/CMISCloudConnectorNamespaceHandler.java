//
// THIS FILE WAS AUTO-GENERATED. DO NOT MANUALLY EDIT!
//

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
