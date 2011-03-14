/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.module.cmis;

public enum VersioningState
{
    /**
     * The document MUST be created as a non-versionable document.
     */
    NONE("none"),

    /**
     * The document MUST be created as a major version
     */
    MAJOR("major"),

    /**
     * The document MUST be created as a minor version.
     */
    MINOR("minor"),

    /**
     * The document MUST be created in the checked-out state.
     */
    CHECKEDOUT("checkedout");


    private final String value;

    VersioningState(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static VersioningState fromValue(String v)
    {
        for (VersioningState c : VersioningState.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}