

Intro

  The CMIS Connector provides an easy to integrate with CMS Repositories
  that have a CMIS Endpoint activated, such Alfresco.

Install

The connector can either be installed for all applications running within the
Mule instance or can be setup to be used for a single application.


To Install Connector for All Applications

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

To Install for a Single Application

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done.

Add the connector as a dependency to your project. This can be done by adding
the following under the <dependencies> element in the pom.xml file of the
application:
    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-cmis</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>

Configure

To use the CMIS connector within a flow the namespace for the connector must
be included. The resulting configuration file header will look similar to the
following:

      <mule xmlns="http://www.mulesoft.org/schema/mule/core"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:cmis="http://www.mulesoft.org/schema/mule/cmis"
            xsi:schemaLocation="
             http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/3.1/mule.xsd
             http://www.mulesoft.org/schema/mule/cmis http://www.mulesoft.org/schema/mule/cmis/1.0-SNAPSHOT/mule-cmis.xsd">

Once the namespace has been included then the CMIS connector can then be
configured by using the config element of the connector. The following is an
example of a config element for the CMIS connector:

      <cmis:config name="alfresco" username="admin" password="admin"
                   base-url="http://cmis.alfresco.com/cmis/" 
                   repository-id="371554cd-ac06-40ba-98b8-e6b60275cca7" />

Also if you need a custom initialization (use a mock implementation) you can
initialize the connector as:
     <cmis:config facade-ref="mockCmisFacade"/>
     ...
     <bean name="mockCMISFacade" 
          xmlns="http://www.springframework.org/schema/beans" 
          class="org.mule.module.cmis.MockCMISFacade">
        <description>Test mock</description>
        <property name="repositoryInfo">
           <bean class="org.apache.chemistry.opencmis.commons.impl.dataobjects.RepositoryInfoImpl">
              <property name="id" value="371554cd-ac06-40ba-98b8-e6b60275cca7"/>
              <property name="latestChangeLogToken" value="42542"/>
           </bean>    
        </property>
        <property name="changeEvents">
           <bean class="org.apache.chemistry.opencmis.client.runtime.ChangeEventsImpl">
              <property name="latestChangeLogToken" value="42543"/>
              <property name="changeEvents">
                 <list>
                    <bean class="org.apache.chemistry.opencmis.client.runtime.ChangeEventImpl">
                        <property name="objectId" value="ABC"/>
                        <property name="changeType"> 
                            <util:constant static-field="org.apache.chemistry.opencmis.commons.enums.ChangeType.CREATED"/>
                        </property>
                    </bean>
                 </list>
              </property>
           </bean>
        </property>
     </bean>

Within the config element a name is given along with any required or optional
parameters. 

There can be multiple CMIS connectors instances. Be sure to named with the
attribute "name". Also every operation can refer the instance using the 
config-ref parameter.

Property placeholders can be used to externalize configuration values that can
then be set using a properties file or system properties.

Example Usage

The following are examples of the usage of the CMIS connector. For a full
reference please check out the schema reference. For more information of
different integration scenarios you can implement using cloud connectors see
Integrating with Cloud Connect.

  
All the operations can have an attribute config-ref

Changelog:

To get the repository changelog you first could call.

     <cmis:changelog changeLogToken="41784" includeProperties="false" />
     <expression-transformer>
        <return-argument expression="changeEvents" evaluator="bean"/>
     </expression-transformer>
     <collection-splitter />
    <logger level="WARN" message="#[bean:changeType] #[bean:objectId]"/>

To get the last changelog token (if it is the first time) you can get
the repository information:

    <cmis:repository-info/>
    <expression-transformer>
        <return-argument evaluator="ognl" expression="latestChangeLogToken" />
    </expression-transformer>

To get a Object from the repository you can:

    <cmis:get-object-by-id objectId="#[bean:objectId]" />

or by path:
   <cmis:get-object-by-path path="/mule-cmis-cloudconnector-test/README.txt" />

Folders can be created. For example, to create /mule-cmis-cloudconnector-test
folder:
    <cmis:repository-info/>
    <cmis:create-folder folderName="mule-cmis-cloudconnector-test" parentObjectId="#[bean:rootFolderId]"/>

to create documents one could do:
    <cmis:create-document-by-path folderPath="/mule-cmis-cloudconnector-test" 
                                     content="hello world" 
                                    filename="README.txt"
                                    mimeType="text/plain"
                                  objectType="D:cmiscustom:document"
                             versioningState="none" />
or refering by id the containing folder:
    <cmis:create-document-by-id     folderId="workspace://SpacesStore/5ae00d48-9eab-4a36-882f-ef7705c1affa" 
                                     content="hello world" 
                                    filename="README.txt"
                                    mimeType="text/plain"
                                  objectType="D:cmiscustom:document"
                             versioningState="none" />
Content can be a String, a byte array, or an InputStream.

