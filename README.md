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
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-cmis</artifactId>
        <version>1.3-SNAPSHOT</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <cmis:config username="value" password="value" repositoryId="value" baseUrl="value"/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
|username|Username|no|
|password|Password|no|
|repositoryId|The identifier for the Repository that this connector instance works with|no|
|baseUrl|URL base for the SOAP connector.|no|




























Repositories
------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Repository Info
---------------

Returns information about the CMIS repository, the optional capabilities it supports and its Access Control information if applicable.



     <cmis:repository-info/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Changelog
---------

Gets repository changes.



     <cmis:changelog changeLogToken="#[payload]" includeProperties="false" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|changeLogToken|The change log token to start from or <code>null</code>|yes||
|includeProperties|Indicates if changed properties should be included in the result|no||



Get Object By Id
----------------

Returns a CMIS object from the repository and puts it into the cache.



     <cmis:get-object-by-id objectId="#[bean:objectId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|objectId|The object id|no||



Get Object By Path
------------------

Returns a CMIS object from the repository and puts it into the cache.



     <cmis:get-object-by-path objectId="#[bean:path]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|path|Path of the object to retrieve|no||



Create Document By Path
-----------------------

Creates a new document in the repository.



    
    <cmis:create-document-by-path folderPath="#[bean:path]"
                                  filename="myfilename"
                                  content="#[bean:content]"
                                  mimeType="text/html"
                                  versioningState="none"/>
    

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folderPath|Folder in the repository that will hold the document|no||
|filename|Name of the file|no||
|content|File content (no byte array or input stream for now)|no||
|mimeType|Stream content-type|no||
|versioningState|An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.|no||*NONE*, *MAJOR*, *MINOR*, *CHECKEDOUT*
|objectType||no||
|force||yes|false|

Returns object id of the created



Create Document By Id
---------------------

Creates a new document in the repository.



    
    <cmis:create-document-by-id folderId="#[bean:folderId]"
                                filename="myfilename"
                                content="#[bean:content]"
                                mimeType="text/html"
                                versioningState="none"/>
    

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folderId|Folder Object Id|no||
|filename|Name of the file|no||
|content|File content (no byte array or input stream for now)|no||
|mimeType|Stream content-type|no||
|versioningState|An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.|no||*NONE*, *MAJOR*, *MINOR*, *CHECKEDOUT*
|objectType||no||

Returns object id of the created



Create Folder
-------------

Creates a folder. Note that this is not recusive creation. You just create
one folder



     <cmis:create-folder folderName="hello" parentObjectId="repository.rootFolder" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folderName|Folder name (eg: "my documents")|no||
|parentObjectId|Parent folder for the folder being created (eg: repository.rootFolder)|no||



Get Type Definition
-------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|typeId||no||



Get Checkout Docs
-----------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filter||yes||
|orderBy||yes||



Query
-----

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statement||no||
|searchAllVersions||no||
|filter||yes||
|orderBy||yes||



Get Parent Folders
------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||



Folder
------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folder||yes||
|folderId||yes||
|get||no||*PARENT*, *CHILDREN*, *DESCENDANTS*, *TREE*
|depth||yes||
|filter||yes||
|orderBy||yes||



Get Content Stream
------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||



Move Object
-----------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||
|sourceFolderId||no||
|targetFolderId||no||



Update Object Properties
------------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||
|properties||no||



Get Object Relationships
------------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||



Get Acl
-------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||



Get All Versions
----------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document||yes||
|documentId||yes||
|filter||yes||
|orderBy||yes||



Check Out
---------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document||yes||
|documentId||yes||



Cancel Check Out
----------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document||yes||
|documentId||yes||



Check In
--------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document||yes||
|documentId||yes||
|content||no||
|filename||no||
|mimeType||no||
|major||no||
|checkinComment||no||



Apply Acl
---------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||
|addAces||no||
|removeAces||no||
|aclPropagation||no||



Get Applied Policies
--------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||



Apply Policy
------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||
|policyIds||no||



Delete
------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject||yes||
|objectId||yes||
|allVersions||yes|false|



Delete Tree
-----------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folder||yes||
|folderId||yes||
|allversions||no||
|unfile||yes||
|continueOnFailure||no||



























