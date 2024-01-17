# Job-Shop-Accounting-System

A customer has a unique name, an address, and a category (an integer number from 1-10). Acustomer can order several assemblies. Each assembly is identified by a unique assembly-id, and has a date-ordered, and assembly-details. To manufacture assemblies, the organization contains a number of processes, each of which is identified by a unique process-id and is supervised by one department. Each department has its own department number and department-data. Each process also has process-data. Processes are classified into three types: paint, fit, and cut. The following information is kept about each type of process:<br>

- Fit: fit-type<br>
- Paint: paint-type, painting-method<br>
- Cut: cutting-type, machine-type<br>

During manufacture an assembly can pass through any sequence of processes in any order; it may pass through the same process more than once.

A job is assigned every time a process begins on an assembly. Information recorded about a job includes a unique job-no, a date the job commenced, and a date the job completed as well as additional information that depends on the type of job. Jobs are classified into three job types: cutjob, paint-job, and fit-job. Information stored about particular job types is:<br>

- Cut-job: type of machine used, amount of time the machine used, material used, and labor time.<br>
- Pain-job: color, volume, and labor time.<br>
- Fit-job: labor time.<br>

An account is maintained by the organization to keep track of expenditure for each process, each assembly, and each department. For each account, the database stores its unique account number and the date the account established. Three types of accounts are maintained:

- Assembly-account to record costs (details-1) for assemblies.<br>
- Department-account to record costs (details-2) for departments.<br>
- Process-account to record costs (details-3) for processes.<br>

As a job proceeds, cost transactions can be recorded against it. Each such transaction is identified by a unique transaction number, and is for a given cost (sup-cost). Each transaction of necessity updates three accounts:

- A process account<br>
- An assembly account<br>
- A department account<br>

The updated process account is for the process used by a job. The updated department account is for the department that manages that process. The updated assembly account is for the assembly that requires the job.

On-line queries and their frequencies for the job-shop accounting system:  

  1. Enter a new customer (30/day).  <br>
  2. Enter a new department (infrequent).<br>
  3. Enter a new assembly with its customer-name, assembly-details, assembly-id, and dateordered (40/day).<br>
  4. Enter a new process-id and its department together with its type and information relevant to the type (infrequent).<br>
  5. Create a new account and associate it with the process, assembly, or department to which it is applicable (10/day).<br>
  6. Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced (50/day).<br>
  7. At the completion of a job, enter the date it completed and the information relevant to the type of job (50/day).<br>
  8. Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details (50/day).<br>
  9. Retrieve the cost incurred on an assembly-id (200/day). 10. Retrieve the total labor time within a department for jobs completed in the department during a given date (20/day).<br>
  10. Retrieve the processes through which a given assembly-id has passed so far (in datecommenced order) and the department responsible for each process (100/day).<br>
  11. Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department (20/day).<br>
  12. Retrieve the customers (in name order) whose category is in a given range (100/day). 14. Delete all cut-jobs whose job-no is in a given range (1/month). 15. Change the color of a   given paint job (1/week).<br>

## Tasks Accomplished
1. Task 1: <br>
Design an ER diagram and a relational database to represent the Job-Shop Accounting database defined above. ER Diagrams are most often used to design or debug relational databases, and Create Relational Schema for tables/relations <br>

2. Task 2: <br>
Provide a Data Element Dictionary that lists the names, types, and sizes (in bytes) of all attributes and associated constraints for each table.<br>

3. Task 3: <br>
Discuss the choices of appropriate storage structures for each relational table assuming that all types of storage structures discussed are available. <br>

4. Task 4: <br>
Construct SQL statements to create tables and implement them on Azure SQL Database. All Create statements are included with appropriate constraints as defined in Task 2. <br>

5. Task 5: <br>
Write SQL Statements for the following queries
-  Enter a new customer
- Enter a new department 
- Enter a new assembly with its customer-name, assembly-details, assembly-id, and dateordered
- Enter a new process-id and its department together with its type and information relevant to the type
- Create a new account and associate it with the process, assembly, or department to which it is applicable 
- Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced
- At the completion of a job, enter the date it completed and the information relevant to the type of job 
- Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details
- Retrieve the cost incurred on an assembly-id
- Retrieve the total labor time within a department for jobs completed in the department during a given date 
- Retrieve the processes through which a given assembly-id has passed so far (in datecommenced order) and the department responsible for each process 
- Retrieve the jobs (together with their type information and assembly-id) completed during a given date in a given department  
- Retrieve the customers (in name order) whose category is in a given range 
- Delete all cut-jobs whose job-no is in a given range  
- Change the color of a given paint job 
- Import: enter new customers from a data file until the file is empty
- Export: Retrieve the customers (in name order) whose category is in a given range and output them to a data file instead of screen 
- QUIT
<br>

6. Task 6:<br>
Write a Web database application using Azure SQL Database and JSP which provides the Web pages for query 1 and query 13. Since both queries take the input data from the user, there are two Web pages for each query as follows:<br>

For query 1, one Web page to allow the user to enter the input data and one to display a message confirming the successful execution of the insertion;<br>

For query 13, there is one Web page to allow the user to enter the input data and one to display the retrieval results with appropriate headings. 
