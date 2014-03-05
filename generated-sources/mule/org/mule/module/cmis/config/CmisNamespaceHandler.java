
package org.mule.module.cmis.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/cmis</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-03-05T04:27:34-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class CmisNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(CmisNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cmis] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cmis] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new CMISCloudConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("repositories", new RepositoriesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("repositories", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("repository-info", new RepositoryInfoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("repository-info", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("changelog", new ChangelogDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("changelog", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object-by-id", new GetObjectByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object-by-path", new GetObjectByPathDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object-by-path", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-document-by-path", new CreateDocumentByPathDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-document-by-path", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-document-by-path-from-content", new CreateDocumentByPathFromContentDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-document-by-path-from-content", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-or-create-folder-by-path", new GetOrCreateFolderByPathDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-or-create-folder-by-path", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-document-by-id", new CreateDocumentByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-document-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-document-by-id-from-content", new CreateDocumentByIdFromContentDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-document-by-id-from-content", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-folder", new CreateFolderDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-folder", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-type-definition", new GetTypeDefinitionDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-type-definition", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-checkout-docs", new GetCheckoutDocsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-checkout-docs", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("query", new QueryDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("query", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-parent-folders", new GetParentFoldersDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-parent-folders", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("folder", new FolderDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("folder", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-content-stream", new GetContentStreamDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-content-stream", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("move-object", new MoveObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("move-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-object-properties", new UpdateObjectPropertiesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-object-properties", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-object-relationships", new GetObjectRelationshipsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-object-relationships", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-acl", new GetAclDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-acl", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-all-versions", new GetAllVersionsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-all-versions", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("check-out", new CheckOutDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("check-out", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("cancel-check-out", new CancelCheckOutDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("cancel-check-out", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("check-in", new CheckInDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("check-in", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("apply-acl", new ApplyAclDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("apply-acl", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-applied-policies", new GetAppliedPoliciesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-applied-policies", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("apply-policy", new ApplyPolicyDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("apply-policy", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete", new DeleteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-tree", new DeleteTreeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-tree", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("apply-aspect", new ApplyAspectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("apply-aspect", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-relationship", new CreateRelationshipDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-relationship", "@Processor", ex);
        }
    }

}
