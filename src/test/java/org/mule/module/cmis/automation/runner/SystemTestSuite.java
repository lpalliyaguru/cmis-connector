/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.automation.runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.cmis.automation.system.ConnectivitySystemTestCases;

@RunWith(Suite.class)
@SuiteClasses({
        ConnectivitySystemTestCases.class
})
public class SystemTestSuite {

}
