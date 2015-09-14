Anypoint CMIS Connector Demo
=========================

INTRODUCTION
------------
  This demo shows how to get documents from a CMIS repository and update it's contents.
  It receives the path to a target document and an id from a pastie (from http://pastie.org), 
  and updates the document content with the pastie's text.
  
HOW TO DEMO:
------------

  1. After importing the project, Set the following global property:
  
    a. mule.cmis.baseDirectory Path to an existing directory in the repository where result documents will be saved.

  2. Deploy the example in a mule Container and hit
        http://localhost:8081/demo?fileName=demoFile&pastieId=1873255.
        
    This will update the contents of the file named "demoFile" in the base directory with the text from pastie #1873255.


HOW IT WORKS:
-------------

   - The flow tries to fetch the document using "get-object-by-path"
      - If the document exists, it is checked out. Then the pastie is fetched from pastie.org and the document is 
         checked-in with the pastie's content.
      - If the document does not exist, the pastie is fetched and a new document is created.