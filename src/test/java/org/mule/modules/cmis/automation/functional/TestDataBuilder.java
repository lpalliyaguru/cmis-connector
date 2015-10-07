/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.functional;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDataBuilder {

    private static Map<String, Object> getSpringBean(String beanName) {
        ApplicationContext context = new ClassPathXmlApplicationContext("AutomationSpringBeans.xml");
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) context.getBean(beanName);
        ((ConfigurableApplicationContext) context).close();
        return testData;
    }

    public static Map<String, Object> getTestData(String testDataName) {
        return getSpringBean(testDataName);
    }

    public static InputStream generateRandomInputStream() throws Exception {
        return IOUtils.toInputStream(UUID.randomUUID().toString());
    }

    public static String generateRandomShortString() {
        return String.format("qaTest%s", new Object[]{UUID.randomUUID().toString().substring(0, 7)});
    }
}
