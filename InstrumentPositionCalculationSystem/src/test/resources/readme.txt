Problem Statement:
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
ABC Investment Bank is a prominent global bank, as part of the General Ledger System, you are required to develop a position calculation process.
Position Calculation process takes start of day positions and transaction files as input, apply transactions on positions based on transaction type (B/S) and 
account type (I/E), and computes end of day positions. Depending on the direction of the transaction (Buy/Sell) each transaction is recorded as debit and credit 
into external and internal accounts in the “Positions” file.

Input:
Positions File: contain start positions for instruments
Transactions Files: contains transactions for a day

Process:
Read Positions and Transactions files, compute new positions and write to new end of day positions file.
For each transaction in Transaction file,
Apply TransactionQuantity into matching instrument records in the position file
If Transaction Type =B ,
      For AccountType=E, Quantity=Quantity + TransactionQuantity
      For AccountType=I, Quantity=Quantity - TransactionQuantity
If Transaction Type =S ,
      For AccountType=E, Quantity=Quantity - TransactionQuantity
      For AccountType=I, Quantity=Quantity + TransactionQuantity
=======================================================================================================================================================================
This project is implementation of above problem statement.
=======================================================================================================================================================================

How to setup this project:

Prerequisites:
 - Java Version: jdk1.8 or higher
 - Maven
 - Any latest IDE - InteliJ, Oxygen Eclipse(preferred)
 
- Download the project from github using git link: https://github.com/pratikbhingardeve/UBSAssignment 
- Import in workspace.
- Configure project as maven project.
- Make sure following folders are present in your project directory if not then create it.
	input
	output
	testInputFiles
	testOutputFiles
	
- Build it and run it with System Parameter:
	E.g.
	input.init.positions=Input_StartOfDay_Positions.txt 
	input.transactions=Input_Transactions.txt 
	output.eod.positions=EndOfDay_Positions.txt
 