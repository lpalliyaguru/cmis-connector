
package org.mule.module.cmis.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.module.cmis.processors.FolderMessageProcessor;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class FolderDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(FolderDefinitionParser.class);

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(FolderMessageProcessor.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the @Processor [folder] within the connector [cmis] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the @Processor [folder] within the connector [cmis] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.addConstructorArgValue("folder");
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        if (hasAttribute(element, "folder-ref")) {
            if (element.getAttribute("folder-ref").startsWith("#")) {
                builder.addPropertyValue("folder", element.getAttribute("folder-ref"));
            } else {
                builder.addPropertyValue("folder", (("#[registry:"+ element.getAttribute("folder-ref"))+"]"));
            }
        }
        parseProperty(builder, element, "folderId", "folderId");
        parseProperty(builder, element, "get", "get");
        parseProperty(builder, element, "depth", "depth");
        parseProperty(builder, element, "filter", "filter");
        parseProperty(builder, element, "orderBy", "orderBy");
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        parseProperty(builder, element, "baseUrl", "baseUrl");
        parseProperty(builder, element, "repositoryId", "repositoryId");
        parseProperty(builder, element, "endpoint", "endpoint");
        parseProperty(builder, element, "connectionTimeout", "connectionTimeout");
        parseProperty(builder, element, "useAlfrescoExtension", "useAlfrescoExtension");
        parseProperty(builder, element, "cxfPortProvider", "cxfPortProvider");
        parseProperty(builder, element, "useCookies", "useCookies");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
