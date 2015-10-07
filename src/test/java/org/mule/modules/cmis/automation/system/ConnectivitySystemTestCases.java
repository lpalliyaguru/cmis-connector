/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cmis.automation.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.cmis.Config;
import org.mule.modules.cmis.model.Authentication;
import org.mule.modules.cmis.model.CMISConnectionType;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

public class ConnectivitySystemTestCases {

    private Properties validCredentials;
    private String baseUrl;
    private String username;
    private String password;
    private String repositoryId;
    private Config config;

    @Before
    public void setUp() throws Exception {
        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();
        baseUrl = validCredentials.getProperty("config.baseUrl");
        username = validCredentials.getProperty("config.username");
        password = validCredentials.getProperty("config.password");
        repositoryId = validCredentials.getProperty("config.repositoryId");

        config = new Config();
        config.setEndpoint(CMISConnectionType.valueOf(validCredentials.getProperty("config.endpoint")));

        // Defaults
        config.setAuthentication(Authentication.STANDARD);
        config.setCxfPortProvider("org.apache.chemistry.opencmis.client.bindings.spi.webservices.CXFPortProvider");

    }

    @Test
    public void validCredentialsConnectivityTest() {
        try {
            // Call the @TestConnectivity
            config.testConnect(baseUrl, username, password, repositoryId);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidCredentialsConnectivityTest() {
        try {
            // Call the @TestConnectivity
            config.testConnect(baseUrl, "noUsername", "noPassword", "");
        } catch (ConnectionException ce) {
            assertTrue(Arrays.asList(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ConnectionExceptionCode.UNKNOWN).contains(ce.getCode()));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            // Call the @TestConnectivity
            config.testConnect("unknown", username, password, "");
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.UNKNOWN, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void nullCredentialsConnectivityTest() {
        try {
            // Call the @TestConnectivity
            config.testConnect(null, null, null, null);
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
