Mule CMIS Cloud Connector
=========================

The CMIS Connector provides an easy to integrate with CMS Repositories that have a CMIS Endpoint activated, such Alfresco.

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>muleforge-releases</id>
            <name>MuleForge Snapshot Repository</name>
            <url>https://repository.muleforge.org/release/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the <dependencies> element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-cmis</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <cmis:config/>

Here is detailed list of all the configuration attributes:

| attribute | description |
|:-----------|:-----------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|
|username|Username|
|password|Password|
|repositoryId|The identifier for the Repository that this connector instance works with|
|baseUrl|URL base for the SOAP connector. For example http://cmis.alfresco.com/cmis/|
|endpoint|The type of endpoint|
