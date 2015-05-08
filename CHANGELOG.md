CMIS Anypoint Connector Release Notes
==========================================
  
Date: 15-Jun-2015
  
Version: 3.0.0
  
Supported versions:
------------------------------
Apache Chemistry - 0.10.0       
Alfresco Opencmis Extension - 0.7

Supported Mule Runtime Versions:
--------------------------------
3.5.x

Supported Operations:
------------------------------
* apply-acl
* apply-aspect
* apply-policy
* cancel-check-out
* changelog
* check-in
* check-out
* create-document-by-id
* create-document-by-path
* create-folder
* create-relationship
* delete
* delete-tree
* folder
* get-acl
* get-all-versions
* get-applied-policies
* get-checkout-docs
* get-content-stream
* get-object-by-id
* get-object-by-path
* get-object-relationships
* get-or-create-folder-by-path
* get-parent-folders
* get-type-definition
* move-object
* query
* repositories
* repository-info
* update-object-properties 
  
New Features and Functionality
------------------------------
Migrated to DevKit 3.5.3

Closed Issues in this release
-----------------------------
- Removed duplicate operations create-document-by-id-from-content & create-document-by-path-from-content. (CLDCONNECT-1601)
- Modified the properties attribute of create-document-by-id, create-document-by-path, apply-aspect & update-object-properties to accept Map of Objects instead of Strings. (CLDCONNECT-2328)
- Fixed closing of the content payload if its an input stream. (CLDCONNECT-2351)
    
