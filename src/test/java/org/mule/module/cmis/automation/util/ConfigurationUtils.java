/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.util;

import org.mule.tools.devkit.ctf.exceptions.CTFUtilsException;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;
import org.mule.tools.devkit.ctf.utils.CTFFileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigurationUtils {
	
	
	private ConfigurationUtils(){
	}
	
	private static String getAutomationCredentialsPropertiesPath(String connectorPath) throws ConfigurationLoadingFailedException{
		
		
		String automationCredentialsProperty = System
				.getProperty("automation-credentials.properties");

		// No properties, so we try to read the default file
		if (automationCredentialsProperty == null)
			automationCredentialsProperty = "automation-credentials.properties";

		String resourcePath = connectorPath + "/src/test/resources";

		String automationFile = resourcePath + "/"
				+ automationCredentialsProperty;

		try {
			CTFFileUtils.getFileFullName(resourcePath,
					automationCredentialsProperty);
		} catch (CTFUtilsException e) {
			throw new ConfigurationLoadingFailedException(
					"No automation default file found within " + resourcePath,
					e);
		}

		return automationFile;
	}
	
	
	public static Properties getAutomationCredentialsProperties() throws ConfigurationLoadingFailedException{
		
		String connectorPath = System.getProperty("user.dir");
		
		Properties ret = new Properties();
		
		String automationPath = getAutomationCredentialsPropertiesPath(connectorPath);
		
		try {
			ret.load(new FileInputStream(automationPath));
		} catch (IOException e) {
			throw new ConfigurationLoadingFailedException(
					"Can not load Automation Credentials File within path " + automationPath,
					e);
		}
		
		return ret;
		
	}
	
	

}
