I. Included Source Files
------------
Receipt.java
ReceiptClient.java
ReceiptItem.java
ReceiptItemParser.java
SalesExemptReceiptItem.java
README.txt
receiptInputFile-TestCase1.txt
receiptInputFile-TestCase2.txt
receiptInputFile-TestCase3.txt
receiptInputFile-TestCase4.txt
receiptInputFile-TestCase5.txt
SalesTaxChallenge.jar




II. Running The Program
------------------------
This program reads input from files in the project directory. Input files have the naming convention 

		"receiptInputFile-TestCase(caseNumber).txt"		(i.e. receiptInputFile-TestCase1.txt)

The program identifies the number of valid input files in the project directory allowing for any number of input files to be
run at a single time. Output is piped into corresponding output files having the naming convention:

		"receiptOutputFile-TestCase(caseNumber).txt"	(i.e. receiptOutputFile-TestCase1.txt)
		
If output files do not exist, files are created first. Input values are new-line delimited and follow the syntax:
		
		"(quantity) (description) at (price)"			(i.e. 1 imported box of chocolates at 11.25)
		
Any input values that do not follow this syntax are logged into ErrorLog.txt.




III. Program Design
-------------------
This program runs a client that reads the project directory for input files. Valid input files are determined by using a
FileNameFilter object. Once all valid input files have been determined, each input file is read one at a time. A valid
line of input is determined by checking the line against a regex which defines the afore mentioned item syntax. A receipt
object is created for each input file. The Receipt class is implemented as linked list of ReceiptItem and/or 
SalesExemptReceiptItem objects.. Each item is read from the input file and is parsed using the ReceiptItemParser
class. If the parser determines the item to be valid, a ReceiptItem or SalesExemptReceiptItem object is created and
returned. The parser determines if the item is a regular ReceiptItem or a SalesExemptReceiptItem by checking if it's
description contains the keywords "pill", "book", or "chocolate". If the description contains any of those keywords a
SalesExemptReceiptItem object is created instead of a standard ReceiptItem. If the item description contains the keyword
imported, an additional tax is applied to the item. The program takes into account item quantity and factors taxes and
cost in accordingly. After all items from the input file have been created and added to the receipt, the program 
calculates the total cost and total sales taxes paid for the list of items. 