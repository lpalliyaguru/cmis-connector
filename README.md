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
            <url>https://repository.mulesoft.org/releases/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-cmis</artifactId>
        <version>1.3</version>
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

Returns all repositories that are available at the endpoint.



      <cmis:repositories />

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
|changeLogToken|    The change log token to start from or <code>null</code>|yes||
|includeProperties| Indicates if changed properties should be included in
                         the result|no||

Get Object By Id
----------------

Returns a CMIS object from the repository and puts it into the cache.



     <cmis:get-object-by-id objectId="#[bean:objectId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|objectId| The object id|no||

Get Object By Path
------------------

Returns a CMIS object from the repository and puts it into the cache.



     <cmis:get-object-by-path objectId="#[bean:path]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|path| Path of the object to retrieve|no||

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
|folderPath|      Folder in the repository that will hold the document|no||
|filename|        Name of the file|no||
|content|         File content (no byte array or input stream for now)|no||
|mimeType|        Stream content-type|no||
|versioningState| An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.|no||*NONE*, *MAJOR*, *MINOR*, *CHECKEDOUT*
|objectType||no||
|properties| the properties optional document properties to set|yes||
|force| if should folder structure must be created when there 
    are missing intermediate folders|yes|false|

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
|folderId|        Folder Object Id|no||
|filename|        Name of the file|no||
|content|         File content (no byte array or input stream for now)|no||
|mimeType|        Stream content-type|no||
|versioningState| An enumeration specifying what the versioing state of the newly-created object MUST be. If the repository does not support versioning, the repository MUST ignore the versioningState parameter.|no||*NONE*, *MAJOR*, *MINOR*, *CHECKEDOUT*
|objectType||no||
|properties| the properties optional document properties to set|yes|false|

Create Folder
-------------

Creates a folder. Note that this is not recusive creation. You just create
one folder



     <cmis:create-folder folderName="hello" parentObjectId="repository.rootFolder" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folderName|     Folder name (eg: "my documents")|no||
|parentObjectId| Parent folder for the folder being created (eg: repository.rootFolder)|no||

Get Type Definition
-------------------

Returns the type definition of the given type id.



     <cmis:get-type-definition typeId="12345" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|typeId| Object type Id|no||

Get Checkout Docs
-----------------

Retrieve list of checked out documents.



     <cmis:get-checkout-docs />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filter| comma-separated list of properties to filter|yes||
|orderBy| comma-separated list of query names and the ascending modifier 
     "ASC" or the descending modifier "DESC" for each query name|yes||

Query
-----

Sends a query to the repository



     <cmis:query searchAllVersions="true" statement="SELECT * FROM cmis:document" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|statement| the query statement (CMIS query language)|no||
|searchAllVersions| specifies if the latest and non-latest versions 
                         of document objects should be included|no||
|filter| comma-separated list of properties to filter|yes||
|orderBy| comma-separated list of query names and the ascending modifier 
     "ASC" or the descending modifier "DESC" for each query name|yes||

Get Parent Folders
------------------

Retrieves the parent folders of a fileable cmis object



     
       <cmis:get-parent-folders objectId="workspace://SpacesStore/ae87c116-be51-43df-8f79-f8859fb5bb20" />
       or
       <cmis:get-parent-folders cmisObject="#[payload]" />
    

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| the object whose parent folders are needed. can be null if "objectId" is set.|yes||
|objectId| id of the object whose parent folders are needed. can be null if "object" is set.|yes||

Folder
------

Navigates the folder structure.



     <cmis:get-object-by-path path="/mule-cloud-connector" />
     <cmis:folder get="CHILDREN" folderId="#[payload:id]"/>
     
     or 
     
     <cmis:get-object-by-path path="/mule-cloud-connector" />
     <cmis:folder get="DESCENDANTS" folderId="#[payload:id]"/>
     
     

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folder| Folder Object. Can be null if "folderId" is set.|yes||
|folderId| Folder Object id. Can be null if "folder" is set.|yes||
|get| NavigationOptions that specifies whether to get the parent folder,
             the list of immediate children or the whole descendants tree|no||*PARENT*, *CHILDREN*, *DESCENDANTS*, *TREE*
|depth| if "get" value is DESCENDANTS, represents the depth of the
             descendants tree|yes||
|filter| comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)|yes||
|orderBy| comma-separated list of query names and the ascending modifier 
     "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)|yes||

Get Content Stream
------------------

Retrieves the content stream of a Document.



     <cmis:get-content-stream cmisObject="#[variable:document]" /> 

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| The document from which to get the stream. Can be null if "objectId" is set.|yes||
|objectId| Id of the document from which to get the stream. Can be null if "object" is set.|yes||

Move Object
-----------

Moves a fileable cmis object from one location to another. Take into account that a fileable
object may be filled in several locations. Thats why you must specify a source folder.



     <cmis:move-object sourceFolderId="1111" 
      targetFolderId="workspace://SpacesStore/2437b2ff-8804-4426-a268-fcfb3ef34ffc" 
            objectId="workspace://SpacesStore/ae87c116-be51-43df-8f79-f8859fb5bb20" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| The object to move. Can be null if "objectId" is set.|yes||
|objectId| The object's id. Can be null if "cmisObject" is set.|yes||
|sourceFolderId| Id of the source folder|no||
|targetFolderId| Id of the target folder|no||

Update Object Properties
------------------------

Update an object's properties



     <cmis:update-object-properties objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28">
          <cmis:properties>
              <cmis:property key="propkey" value="propValue"/>
          </cmis:properties>
      </cmis:update-object-properties>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| Object to be updated. Can be null if "objectId" is set.|yes||
|objectId| The object's id. Can be null if "cmisObject" is set.|yes||
|properties| The properties to update|no||

Get Object Relationships
------------------------

Returns the relationships if they have been fetched for an object.



     <cmis:get-object-relationships objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| the object whose relationships are needed|yes||
|objectId||yes||

Get Acl
-------

Returns the ACL if it has been fetched for an object.



     <cmis:get-acl objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28"  />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| the object whose Acl is needed|yes||
|objectId||yes||

Get All Versions
----------------

Retrieve an object's version history



     <cmis:get-all-versions document="#[payload]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document| the document whose versions are to be retrieved|yes||
|documentId||yes||
|filter| comma-separated list of properties to filter (only for CHILDREN or DESCENDANTS navigation)|yes||
|orderBy| comma-separated list of query names and the ascending modifier 
     "ASC" or the descending modifier "DESC" for each query name (only for CHILDREN or DESCENDANTS navigation)|yes||

Check Out
---------

Checks out the document and returns the object id of the PWC (private working copy).



     <cmis:check-out documentId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document| The document to be checked out. Can be null if "documentId" is set.|yes||
|documentId||yes||

Cancel Check Out
----------------

If applied to a PWC (private working copy) of the document, the check out
will be reversed. Otherwise, an exception will be thrown.



     <cmis:cancel-check-out documentId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document| The checked out document. Can be null if "documentId" is set.|yes||
|documentId||yes||

Check In
--------

If applied to a PWC (private working copy) it performs a check in.
Otherwise, an exception will be thrown.



     <cmis:check-in content="modified content" filename="#[payload:name]"
                      checkinComment="change on file" major="true"
                     mimeType="application/octet-stream;charset=UTF-8" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|document| The document to check-in. Can be null if "documentId" is set.|yes||
|documentId| Id of the document to check-in. Can be null if "document" is set.|yes||
|content|           File content (no byte array or input stream for now)|no||
|filename|          Name of the file|no||
|mimeType|          Stream content-type|no||
|major||no||
|checkinComment| Check-in comment|no||
|properties| custom properties|yes||

Apply Acl
---------

Set the permissions associated with an object.



     <cmis:get-acl objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| the object whose Acl is intended to change.|yes||
|objectId||yes||
|addAces| added access control entities|no||
|removeAces| removed access control entities|no||
|aclPropagation| wheter to propagate changes or not. can be <ul>
         <li>(a) REPOSITORYDETERMINED</li>
         <li>(b) OBJECTONLY</li>
         <li>(c) PROPAGATE</li>
         </ul>|no||

Get Applied Policies
--------------------

Get the policies that are applied to an object.



     <cmis:get-applied-policies objectId="workspace://SpacesStore/64b078f5-3024-403b-b133-fa87d0060f28"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| The document from which to get the stream. Can be null if "objectId" is set.|yes||
|objectId| Id of the document from which to get the stream. Can be null if "object" is set.|yes||

Apply Policy
------------

Applies policies to this object.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| The document from which to get the stream. Can be null if "objectId" is set.|yes||
|objectId| Id of the document from which to get the stream. Can be null if "object" is set.|yes||
|policyIds| Policy ID's to apply|no||

Delete
------

Remove an object



     <cmis:delete object="#[payload]" allVersions="true" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|cmisObject| The object to be deleted. Can be null if "objectId" is set.|yes||
|objectId| The object's id. Can be null if "cmisObject" is set.|yes||
|allVersions| If true, deletes all version history of the object. Defaults to "false".|yes|false|

Delete Tree
-----------

Deletes a folder and all subfolders.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|folder| Folder Object. Can be null if "folderId" is set.|yes||
|folderId| Folder Object id. Can be null if "folder" is set.|yes||
|allversions| If true, then delete all versions of the document. 
                   If false, delete only the document object specified.|no||
|unfile| Specifies how the repository must process file-able child- 
              or descendant-objects.|yes||
|continueOnFailure| Specified whether to continue attempting to perform 
 this operation even if deletion of a child- or descendant-object 
 in the specified folder cannot be deleted or not.|no||













