## CS 480 (Database Systems) - Apoorv Mishra (664719426)
## Java/JDBC program for Employee-Department Tables

In this java program we interface the MySQL database using JDBC connectors.

## Prerequisites

#### Environment Setup
See below for how to set up your environment to run the Java/JDBC program.

#### Requirements
The following are required for local environment setup:

Java : JDK 1.8


#### Java Development Kit (JDK)
#### Download
Check what version of Java you have installed. Run java -version in your terminal. If it displays something like java version "1.8.#####", you are good to go.

Otherwise, download the appropriate version of the _jdk_ from the official Oracle Java website.

#### JDBC Connector

The following steps should be taken to use the JDBC connector:

1) Install JDBC driver.

2) Include the downloaded _jar_ file in the _jre_ and _jdk_ folders with other libraries.

3) Use `com.mysql.jdbc.Driver` as the class that implements java.sql.Driver.

4) Provide the `url`, `user` and `password` for the MySQL workbench.

5) `Conection connection = DriverManager.getConnection(url, user, password)` is used to connect to the required database.

## Understanding the Code

The basic functionality of  the code is to perform various transactions in the given tables:

_employee(<u>eid</u>:integer, name:string, salary:integer, did:integer)_

_department(<u>did</u>:integer, mid:integer)_

As given in the question - there can be 6 types of transactions, I have included `switch/case` statement to determine which transaction is being processed.

The transactions are read from a _transfile_ which a text file. To read the _transfile_, I have used:

`File file = new File("filename with location");`
`Scanner scanner = new Scanner(file); `

Each transaction consists of a string which can has details such as _transaction type_, _did_, _eid_ and other column values for the tables.

To read each value from a line I have used:

1) `int transactionType = Integer.parseInt(transaction.split(" +")[0])` to get the transaction type for each transaction.

2) Similarly to get other values we can change the index of the `String[]` returned after we split the string using the regular expression.

The code consists of 9 methods, excluding the main method.
The methods with their functionality are described below:

1) `createTables()` - As the name of the method suggests, this method contains the MySQL queries to create the two required tables - _employee_ and _department_.

2) `deleteExistingEmployee(String transaction)` - This method receives the transaction i.e. the current line from the _transfile_. This method extracts the details from the transaction and deletes an employee if the employee exists or else throws an ERROR message.

3) `insertNewEmployee(String transaction)` - This method also gets the transaction as a parameter. This method contains MySQL query to insert a tuple into the _employee_ table. This method also checks whether an employee already exists and whether the department exists before adding the new employee.

4) `insertInDepartment(String transaction)` - This method contains MySQL query to insert a tuple in the department table. If the `mid` is provided in the transaction, it sets the `mid` to the desired value or else sets it to `null`. If the `did` already exists, it updates the `mid`.

5) `getAverageSalaryOfAllEmployees()` - This method calculates the average salary of all the employees.

6) `getEmployeeNamesForManager(String transaction)` - This method is used to get all the Employee names who are under a manager whose `mid` is given in the transaction.

7) `getAverageSalaryForEmployeesUnderAManager(String transaction)` - This method is used to calculate the average salary of the employees who are under a manager whose `mid` is given in the transaction.

8) `printTables()` - This method is used to print the tables i.e. print the result set of the MySQL query which selects all the columns from each table respectively.

9) `dropTables()` - This method is used to drop the tables and is called at the end of the program.

A default case is included in the `switch/case` which is executed when a transaction type is other than (1 to 6). This simply prints an error message. 

All SQL Exceptions and Number Format Exceptions are handled in the program. The connections are closed at the end of the program.
 







