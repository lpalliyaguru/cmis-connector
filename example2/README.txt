Paypal payment authorization demo
==============================

INTRODUCTION
  This demo shows how to get documents from a CMIS repository and update it's contents.
  It receives the path to a target document and an id from a pastie (from http://pastie.org), and updates the document content with the pastie's text.
  

HOW TO DEMO:
  1. Set the following system properties:
    a. cmis.repository.base Base URL for the CMIS repository
    b. cmis.repository.id Password for the CMIS repository
    c. cmis.demo.baseDirectory Path to an existing directory in the repository where result documents will be saved.

  2. Deploy the example in a mule Container and hit
        http://localhost:9090/update-file?fileName=demoFile&pastieId=1873255&checkInComment=Comment
    This will update the contents of the file named "demoFile" in the base directory with the text from pastie #1873255.


HOW IT WORKS:   
   - First, the flow tries to fetch de document using "get-object-by-path"
   - CASE:
     If the document exists, it is checked out. Then the pastie is fetched from pastie.org and the document is checked-in with the pastie's content.
   - CASE:
     If the document does not exist, the pastie is fetched and a new document is created.
