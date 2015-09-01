/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.system;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.module.cmis.Config;
import org.mule.module.cmis.automation.util.ConfigurationUtils;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConnectivitySystemTestCases {

    private Properties validCredentials;
    private String baseUrl;
    private String username;
    private String password;
    private String repositoryId;

    @Before
    public void setUp() throws Exception {
        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();
        baseUrl = validCredentials.getProperty("config.baseUrl");
        username = validCredentials.getProperty("config.username");
        password = validCredentials.getProperty("config.password");
        repositoryId = validCredentials.getProperty("config.repositoryId");
    }

    @Test
    public void validCredentialsConnectivityTest() {
        Config config = new Config();
        try {
            //Call the @TestConnectivity
            config.testConnect(baseUrl, username, password, repositoryId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    public void invalidCredentialsConnectivityTest() {

        Config config = new Config();
        try {
            //Call the @TestConnectivity
            config.testConnect(baseUrl, "", "noPassword", "");
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

        try {
            //Call the @TestConnectivity
            config.testConnect("unknown", username, password, "");
        } catch (ConnectionException ce) {
            assertEquals(ConnectionExceptionCode.INCORRECT_CREDENTIALS, ce.getCode());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    public void nullCredentialsConnectivityTest() {

        Config config = new Config();
        try {
            //Call the @TestConnectivity
            config.testConnect(null, null, null, null);
        } catch (ConnectionException ce) {
            assertEquals("001", ce.getThirdPartyCode());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
