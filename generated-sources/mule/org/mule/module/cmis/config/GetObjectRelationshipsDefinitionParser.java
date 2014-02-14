
package org.mule.module.cmis.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.module.cmis.processors.GetObjectRelationshipsMessageProcessor;
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

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:47-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GetObjectRelationshipsDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(GetObjectRelationshipsDefinitionParser.class);

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(GetObjectRelationshipsMessageProcessor.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the @Processor [get-object-relationships] within the connector [cmis] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the @Processor [get-object-relationships] within the connector [cmis] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.addConstructorArgValue("getObjectRelationships");
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        if (hasAttribute(element, "cmisObject-ref")) {
            if (element.getAttribute("cmisObject-ref").startsWith("#")) {
                builder.addPropertyValue("cmisObject", element.getAttribute("cmisObject-ref"));
            } else {
                builder.addPropertyValue("cmisObject", (("#[registry:"+ element.getAttribute("cmisObject-ref"))+"]"));
            }
        }
        parseProperty(builder, element, "objectId", "objectId");
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        parseProperty(builder, element, "baseUrl", "baseUrl");
        parseProperty(builder, element, "repositoryId", "repositoryId");
        parseProperty(builder, element, "endpoint", "endpoint");
        parseProperty(builder, element, "connectionTimeout", "connectionTimeout");
        parseProperty(builder, element, "useAlfrescoExtension", "useAlfrescoExtension");
        parseProperty(builder, element, "cxfPortProvider", "cxfPortProvider");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
