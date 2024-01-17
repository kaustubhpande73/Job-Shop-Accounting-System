import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.sql.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class IP {
    // Database credentials
    final static String HOSTNAME = "pand0021.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "pand0021";
    final static String PASSWORD = "Stepbiggermx12!";

 // Database connection string
    final static String URL =
    String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
    HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // Stored Procedure templates
    //Query1
    final static String ENTER_CUSTOMER_PROCEDURE = "EXEC Q1 @customer_name = ?, @customer_address = ?, @category = ?";
    //Query2
    final static String ENTER_DEPARTMENT_PROCEDURE = "EXEC Q2 @department_number = ?, @department_data = ?";
    
    //Query3
    final static String insertProcess = "EXEC Q3 @process_id = ?, @process_data = ?";
    final static String insertPaintProcess = "EXEC Q3_a @process_id = ?, @paint_type = ?, @painting_method = ?";
    final static String insertCutProcess = "EXEC Q3_b @process_id = ?, @cutting_type = ?, @machine_type = ?";
    final static String insertFitProcess = "EXEC Q3_c @process_id = ?, @fit_type = ?";
    final static String insertSupervised = "EXEC Q3_d @process_id = ?, @department_number = ?";
   
    
    //Query4
    final static String INSERT_ASSEMBLY_PROCEDURE = "EXEC Q4 @assembly_id = ?, @date_ordered = ?, @details = ?, @customer_name = ?";
    final static String INSERT_PASS_THROUGH_PROCEDURE = "EXEC Q4_a @assembly_id = ?, @process_id = ?";
    
    //Query5
    final static String createAccountProcedure = "EXEC Q5 @account_number = ?, @date_established = ?";
    final static String associateWithAssemblyProcedure = "EXEC Q5_a @account_number = ?, @Assembly_cost";
    final static String associateWithProcessProcedure = "EXEC Q5_b @account_number = ?, Process_cost";
    final static String associateWithDepartmentProcedure = "EXEC Q5_d @account_number = ?, dept_cost";

    //Query6
    final static String ENTER_NEW_JOB_PROCEDURE = "EXEC Q6 @job_number = ?, @start_date = ?, @end_date = ?";
    final static String ASSOCIATE_JOB_PROCEDURE = "EXEC Q6_a @process_id = ?, @assembly_id = ?, @job_number = ?";
    
    //Query8
    final static String insertTransaction = "EXEC Q8 @transaction_number = ?, @sup_cost = ?";
    final static String updateProcessCost = "EXEC Q8_b @transaction_number = ?, @sup_cost = ?";
    final static String updateDepartmentCost = "EXEC Q8_c @transaction_number = ?, @sup_cost = ?";
    final static String updateAssemblyCost = "EXEC Q8_d @transaction_number = ?, @sup_cost = ?";
    final static String linkTransactionProcess = "EXEC Q8_e @process_id = ?, @job_no = ?, @transaction_number = ?, @sup_cost = ?";
    final static String linkTransactionAssembly = "EXEC Q8_f @assembly_id = ?, @job_id = ?, @transaction_number = ?, @sup_cost = ?";
    final static String linkTransactionDepartment = "EXEC Q8_g @process_id = ?, @department_number = ?, @transaction_number = ?, @sup_cost = ?";

    //Query7
    final static String completeJobProcedure = "EXEC Q7 @job_number = ?, @end_date = ?";
    final static String insertCutJobProcedure = "EXEC Q7_a @job_number = ?, @machine_type = ?, @cut_time_used = ?, @cut_material_used = ?, @cut_labor_time = ?";
    final static String insertPaintJobProcedure = "EXEC Q7_b @job_number = ?, @paint_color = ?, @paint_volume = ?, @paint_labor_time = ?";
    final static String insertFitJobProcedure = "EXEC Q7_c @job_number = ?, @labor_time = ?";
    
    //Query9
    final static String get_total_cost = "EXEC Q9 @AssemblyID = ?";
    
    //Query10
    final static String get_total_labor_time = "EXEC Q10 @DepartmentNumber = ?, @CompletionDate = ?";
    
    //Query11
    final static String retrieveProcessesForAssembly = "EXEC Q11 @AssemblyID = ?";
    
    //Query12
    final static String retrieveCustomersByCategoryRange = "EXEC Q12 @categoryMin = ?, @categoryMax = ?";

    //Query13
    final static String deleteCutJobs = "EXEC Q13 @Job_No_from = ?, @Job_No_to = ?";
    
    //Query14
    final static String changePaintJobColor = "EXEC Q14 @JobNumber = ?, @NewColor = ?";



    // User input prompt
    final static String PROMPT = "\nPlease select one of the options below: \n" +
    		"1) Enter a new customer (30/day) \n" +
    		"2) Enter a new department (infrequent) \n" +
    		"3) Enter a new process-id and its department together with its type and information relevant to the type (infrequent). \n" +
    		"4) Enter a new assembly with its customer-name, assembly-details, assembly-id, and date-ordered and associate it with one or more processes (40/day) \n" +
    		"5) Create a new account and associate it with the process, assembly, or department to which it is applicable (10/day) \n" +
    		"6) Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced (50/day) \n" +
    		"7) At the completion of a job, enter the date it completed and the information relevant to the type of job (50/day) \n" +
    		"8) Enter a transaction-no and its sup-cost and update all the costs (details) of the affected accounts by adding sup-cost to their current values of details (50/day) \n" +
    		"9) Retrieve the total cost incurred on an assembly-id (200/day) \n" +
    		"10) Retrieve the total labor time within a department for jobs completed in the department during a given date (20/day) \n" +
    		"11) Retrieve the processes through which a given assembly-id has passed so far (in date-commenced order) and the department responsible for each process (100/day) \n" +
    		"12) Retrieve the customers (in name order) whose category is in a given range (100/day) \n" +
    		"13) Delete all cut-jobs whose job-no is in a given range (1/month) \n" +
    		"14) Change the color of a given paint job \n" +
    		"15) File Import \n" +
    		"16) File Export \n" +
    		"17) Exit!";

    public static void main(String[] args) throws SQLException {
        System.out.println("WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");

        try (final Scanner sc = new Scanner(System.in)) {
            String option = "";
            while (!option.equals("17")) {
                System.out.println(PROMPT);
                option = sc.next();

                switch (option) {
                    case "1":
                    	try (Connection connection = DriverManager.getConnection(URL)) {
                            Scanner sc1 = new Scanner(System.in);

                            System.out.println("Enter the Customer Name:");
                            String customerName = sc1.nextLine();

                            System.out.println("Enter the Customer Address:");
                            String customerAddress = sc1.nextLine();

                            System.out.println("Enter the Customer Category (1-10):");
                            int category = Integer.parseInt(sc1.nextLine()); // Reading category as a String and parsing to integer

                            try (PreparedStatement stmt = connection.prepareStatement(ENTER_CUSTOMER_PROCEDURE)) {
                                stmt.setString(1, customerName);
                                stmt.setString(2, customerAddress);
                                stmt.setInt(3, category);

                                int rowsAffected = stmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("New customer added successfully.");
                                } else {
                                    System.out.println("No new customer was added.");
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("An error occurred while adding a new customer.");
                            e.printStackTrace();
                        }
                        break;
                        
                    case "2":
                        
                        try {
                            // Prompt the user for department information
                            System.out.println("Enter the Department number: ");
                            int departmentNumber = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter the Department data: ");
                            String departmentData = sc.nextLine();

                            // Create a SQL connection and prepare the stored procedure
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(ENTER_DEPARTMENT_PROCEDURE)) {

                                // Setting the parameters for the stored procedure
                                statement.setInt(1, departmentNumber);
                                statement.setString(2, departmentData);

                                // Executing the stored procedure
                                int rowsAffected = statement.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("Department inserted successfully.");
                                } else {
                                    System.out.println("Department insertion failed.");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    
                        break;
                        
               
                    case "3": 
                        try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("Enter Process ID: ");
                            int processId = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Process Data: ");
                            String processData = sc.nextLine();
                            System.out.println("Enter Department Number: ");
                            int departmentNumber = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Process Type (1 for Paint, 2 for Cut, 3 for Fit): ");
                            int processType = sc.nextInt();
                            sc.nextLine(); 

                            // Insert process
                            try (PreparedStatement processStmt = connection.prepareStatement(insertProcess)) {
                                processStmt.setInt(1, processId);
                                processStmt.setString(2, processData);
                                processStmt.executeUpdate();
                                System.out.println("Process information added successfully.");
                            }

                            if (processType == 1) { // Paint
                                System.out.println("Enter Paint Type: ");
                                String paintType = sc.nextLine();
                                System.out.println("Enter Painting Method: ");
                                String paintingMethod = sc.nextLine();

                                try (PreparedStatement paintStmt = connection.prepareStatement(insertPaintProcess)) {
                                    paintStmt.setInt(1, processId);
                                    paintStmt.setString(2, paintType);
                                    paintStmt.setString(3, paintingMethod);
                                    paintStmt.executeUpdate();
                                    System.out.println("Paint process details added successfully.");
                                }
                            } else if (processType == 2) { // Cut
                                System.out.println("Enter Cutting Type: ");
                                String cuttingType = sc.nextLine();
                                System.out.println("Enter Machine Type: ");
                                String machineType = sc.nextLine();

                                try (PreparedStatement cutStmt = connection.prepareStatement(insertCutProcess)) {
                                    cutStmt.setInt(1, processId);
                                    cutStmt.setString(2, cuttingType);
                                    cutStmt.setString(3, machineType);
                                    cutStmt.executeUpdate();
                                    System.out.println("Cut process details added successfully.");
                                }
                            } else if (processType == 3) { // Fit
                                System.out.println("Enter Fit Type: ");
                                String fitType = sc.nextLine();

                                try (PreparedStatement fitStmt = connection.prepareStatement(insertFitProcess)) {
                                    fitStmt.setInt(1, processId);
                                    fitStmt.setString(2, fitType);
                                    fitStmt.executeUpdate();
                                    System.out.println("Fit process details added successfully.");
                                }
                            } else {
                                System.out.println("Invalid process type selected.");
                            }

                            // Associate process with department
                            try (PreparedStatement superviseStmt = connection.prepareStatement(insertSupervised)) {
                                superviseStmt.setInt(1, processId);
                                superviseStmt.setInt(2, departmentNumber);
                                superviseStmt.executeUpdate();
                                System.out.println("Process associated with department successfully.");
                            }

                        } catch (SQLException e) {
                            System.out.println("An error occurred while processing.");
                            e.printStackTrace();
                        }
                        break;
                    // End of case "3"

                	
                    case "4":
                        try {
                            // Prompt the user for assembly information
                            System.out.println("Enter Assembly ID: ");
                            int assemblyId = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Date Ordered (YYYY-MM-DD): ");
                            String dateOrdered = sc.nextLine();
                            System.out.println("Enter Assembly Details: ");
                            String assemblyDetails = sc.nextLine();
                            System.out.println("Enter Customer Name: ");
                            String customerName = sc.nextLine();

                            // Create a SQL connection and prepare the stored procedure for inserting assembly
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(INSERT_ASSEMBLY_PROCEDURE)) {

                                // Set the parameters for the INSERT_ASSEMBLY_PROCEDURE stored procedure
                                statement.setInt(1, assemblyId);
                                statement.setString(2, dateOrdered);
                                statement.setString(3, assemblyDetails);
                                statement.setString(4, customerName);

                                // Execute the INSERT_ASSEMBLY_PROCEDURE stored procedure to insert the assembly
                                int rowsAffected = statement.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("Assembly inserted successfully.");
                                } else {
                                    System.out.println("Assembly insertion failed.");
                                }
                            }

                            // Associate the assembly with processes
                            System.out.println("Enter Process IDs to be associated with (comma-separated): ");
                            String processIds = sc.nextLine();
                            String[] processIdArray = processIds.split(",");

                            // Create a SQL connection and prepare the stored procedure for associating processes with the assembly
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(INSERT_PASS_THROUGH_PROCEDURE)) {

                                // Set the parameters for the INSERT_PASS_THROUGH_PROCEDURE stored procedure
                                for (String processId : processIdArray) {
                                    statement.setInt(1, Integer.parseInt(processId));
                                    statement.setInt(2, assemblyId);

                                    // Execute the INSERT_PASS_THROUGH_PROCEDURE stored procedure to associate processes
                                    int rowsAffected = statement.executeUpdate();
                                }

                                
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "5":
                        try {
                            // Prompt for account details
                            System.out.println("Enter Account Number: ");
                            int accountNumber = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Date Established (YYYY-MM-DD): ");
                            String dateEstablished = sc.nextLine();

                            // Connect to database and execute createAccountProcedure
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(createAccountProcedure)) {

                                statement.setInt(1, accountNumber);
                                statement.setString(2, dateEstablished);
                                statement.executeUpdate();
                                System.out.println("Account created successfully.");
                            }

                            // Additional logic to handle associations with process, assembly, or department
                            System.out.println("Choose an option to associate with (1 for Process, 2 for Assembly, 3 for Department): ");
                            int associationOption = sc.nextInt();
                            sc.nextLine(); 
                            
                            System.out.println("Enter the cost:");
                            float cost = sc.nextFloat();
                            sc.nextLine();

                            if (associationOption == 1) {
                                // Associating with a Process
                                //System.out.println("Enter Labor Time (HH:MM:SS): ");
                                //String[] laborTimeUnits = sc.nextLine().split(":");
                               // int laborTime = Integer.parseInt(laborTimeUnits[0]) * 3600 + Integer.parseInt(laborTimeUnits[1]) * 60 + Integer.parseInt(laborTimeUnits[2]);

                                // Connect to database and execute associateWithProcessProcedure
                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(associateWithProcessProcedure)) {

                                    statement.setInt(1, accountNumber);
                                    statement.setFloat(2, cost);
                                    statement.executeUpdate();
                                    System.out.println("Account associated with the process successfully.");
                                }
                            } else if (associationOption == 2) {
                                // Associating with an Assembly
                                // Connect to database and execute associateWithAssemblyProcedure
                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(associateWithAssemblyProcedure)) {

                                    statement.setInt(1, accountNumber);
                                    statement.setFloat(2, cost);
                                    statement.executeUpdate();
                                    System.out.println("Account associated with the assembly successfully.");
                                }
                            } else if (associationOption == 3) {
                                // Connect to database and execute associateWithDepartmentProcedure
                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(associateWithDepartmentProcedure)) {

                                    statement.setInt(1, accountNumber);
                                    statement.setFloat(2, cost);
                                    statement.executeUpdate();
                                    System.out.println("Account associated with the department successfully.");
                                }
                            } else {
                                System.out.println("Invalid option. Please select 1 for Process, 2 for Assembly, or 3 for Department.");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    // End of case "5"


                    case "6":
                        try {
                            // Prompt for job details
                            System.out.println("Enter Job Number: ");
                            int jobNumber = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Start Date (YYYY-MM-DD): ");
                            String startDate = sc.nextLine();
                            System.out.println("Enter End Date (YYYY-MM-DD): ");
                            String endDate = sc.nextLine();

                            // Connecting to database to execute Q6 procedure
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(ENTER_NEW_JOB_PROCEDURE)) {

                                statement.setInt(1, jobNumber);
                                statement.setString(2, startDate);
                                statement.setString(3, endDate);
                                statement.executeUpdate();
                                System.out.println("New job entered successfully.");
                            }

                            // Prompt for process and assembly association with the job
                            System.out.println("Enter Process ID: ");
                            int processId = sc.nextInt();
                            System.out.println("Enter Assembly ID: ");
                            int assemblyId = sc.nextInt();

                            // Connect to database and execute Q6_a procedure
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(ASSOCIATE_JOB_PROCEDURE)) {

                                statement.setInt(1, processId);
                                statement.setInt(2, assemblyId);
                                statement.setInt(3, jobNumber);
                                statement.executeUpdate();
                                System.out.println("Job associated with process and assembly successfully.");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                        
                        
                    case "7":
                        try {
                            System.out.println("Enter Job Number: ");
                            int jobNumber = sc.nextInt();
                            sc.nextLine(); 
                            System.out.println("Enter Completion Date for the Job (YYYY-MM-DD): ");
                            String endDate = sc.nextLine();

                            // Connect to database and execute completeJobProcedure
                            try (Connection connection = DriverManager.getConnection(URL);
                                 PreparedStatement statement = connection.prepareStatement(completeJobProcedure)) {

                                statement.setInt(1, jobNumber);  // Set job_number parameter
                                statement.setString(2, endDate); // Set end_date parameter
                                statement.executeUpdate();
                                System.out.println("Job completion date recorded successfully.");
                            }

                            // Prompt for job type
                            System.out.println("Enter job type (1 for Cut, 2 for Paint, 3 for Fit): ");
                            int jobType = sc.nextInt();
                            sc.nextLine(); 

                            if (jobType == 1) {
                                // Cut job details

                            	System.out.println("Enter Machine Type: ");
                                String machineType = sc.nextLine();
                                System.out.println("Enter Cut Time Used (HH:MM:SS): ");
                                String cutTimeUsed = sc.nextLine();
                                System.out.println("Enter Cut Material Used: ");
                                String cutMaterialUsed = sc.nextLine();
                                System.out.println("Enter Cut Labor Time (HH:MM:SS): ");
                                String cutLaborTime = sc.nextLine();

                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(insertCutJobProcedure)) {

                                    statement.setInt(1, jobNumber);
                                    statement.setString(2, machineType);
                                    statement.setString(3, cutTimeUsed);
                                    statement.setString(4, cutMaterialUsed);
                                    statement.setString(5, cutLaborTime);
                                    statement.executeUpdate();
                                    System.out.println("Cut job details recorded successfully.");
                                }
                            } else if (jobType == 2) {
                            	// Paint job details
                               
                                System.out.println("Enter Paint Color: ");
                                String paintColor = sc.nextLine();
                                System.out.println("Enter Paint Volume: ");
                                int paintVolume = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Enter Paint Labor Time (HH:MM:SS): ");
                                String paintLaborTime = sc.nextLine();

                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(insertPaintJobProcedure)) {

                                    statement.setInt(1, jobNumber);
                                    statement.setString(2, paintColor);
                                    statement.setInt(3, paintVolume);
                                    statement.setString(4, paintLaborTime);
                                    statement.executeUpdate();
                                    System.out.println("Paint job details recorded successfully.");
                                }
                            
                            } else if (jobType == 3) {
                                // Fit job details
     
                                System.out.println("Enter Fit Labor Time (HH:MM:SS): ");
                                String fitLaborTime = sc.nextLine();

                                try (Connection connection = DriverManager.getConnection(URL);
                                     PreparedStatement statement = connection.prepareStatement(insertFitJobProcedure)) {

                                    statement.setInt(1, jobNumber);
                                    statement.setString(2, fitLaborTime);
                                    statement.executeUpdate();
                                    System.out.println("Fit job details recorded successfully.");
                                }
                            } else {
                                System.out.println("Invalid job type selected.");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    // End of case "7"
                        
                    case "8":
                    	
                    	try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("Enter Transaction Number: ");
                            int transactionNumber = sc.nextInt();
                            System.out.println("Enter Supplementary Cost: ");
                            double supCost = sc.nextDouble();
                            sc.nextLine();
                            // Step 1: Insert the transaction
                            try (PreparedStatement transactionStmt = connection.prepareStatement(insertTransaction)) {
                                transactionStmt.setInt(1, transactionNumber);
                                transactionStmt.setDouble(2, supCost);
                                transactionStmt.executeUpdate();
                                System.out.println("Transaction inserted successfully.");
                            }

                            // Step 2: Update costs in Process, Department, and Assembly cost tables
                            try (PreparedStatement processCostStmt = connection.prepareStatement(updateProcessCost);
                                 PreparedStatement departmentCostStmt = connection.prepareStatement(updateDepartmentCost);
                                 PreparedStatement assemblyCostStmt = connection.prepareStatement(updateAssemblyCost)) {

                                processCostStmt.setInt(1, transactionNumber);
                                processCostStmt.setDouble(2, supCost);
                                processCostStmt.executeUpdate();

                                departmentCostStmt.setInt(1, transactionNumber);
                                departmentCostStmt.setDouble(2, supCost);
                                departmentCostStmt.executeUpdate();

                                assemblyCostStmt.setInt(1, transactionNumber);
                                assemblyCostStmt.setDouble(2, supCost);
                                assemblyCostStmt.executeUpdate();

                                System.out.println("Costs updated successfully in Process, Department, and Assembly cost tables.");
                            }

                            // Step 3: Link the transaction with processes, assemblies, and departments
                            System.out.println("Enter Process ID: ");
                            int processId = sc.nextInt();
                            System.out.println("Enter Job Number: ");
                            int jobNo = sc.nextInt();
                            try (PreparedStatement linkProcessStmt = connection.prepareStatement(linkTransactionProcess)) {
                                linkProcessStmt.setInt(1, processId);
                                linkProcessStmt.setInt(2, jobNo);
                                linkProcessStmt.setInt(3, transactionNumber);
                                linkProcessStmt.setDouble(4, supCost);
                                linkProcessStmt.executeUpdate();
                                System.out.println("Transaction linked to process successfully.");
                            }


                        } catch (SQLException e) {
                            System.out.println("An error occurred during the operation.");
                            e.printStackTrace();
                        }
                        break;
                
                    	
                    	
                    case "9":
                    	System.out.println("Enter Assembly ID: ");
                	    int assemblyId = sc.nextInt();

                	    try (final Connection connection = DriverManager.getConnection(URL);
                	         final PreparedStatement statement = connection.prepareStatement(get_total_cost)) {

                	        System.out.println("Dispatching the query...");

                	        statement.setInt(1, assemblyId);

                	        final ResultSet resultSet = statement.executeQuery();
                	        
                	        System.out.printf("Executing the stored procedure...\n");
                	        System.out.println("Cost:");

                	        // Unpacking the result set returned by the database and print out the total cost
                	        if (resultSet.next()) {
                	            System.out.println(String.format("Total cost incurred on assembly ID %d is: %.2f",
                	                    assemblyId, resultSet.getDouble(1)));
                	        } else {
                	            System.out.println("No cost data found for the specified assembly ID.");
                	        }
                	    } catch (SQLException e) {
                	        System.out.println("An error occurred while executing the query.");
                	        e.printStackTrace();
                	    }
                	    break;
                    
                    case "10":
                        System.out.println("Enter Department Number: ");
                        int departmentNumber = sc.nextInt();
                        sc.nextLine(); 

                        System.out.println("Enter Completion Date (YYYY-MM-DD): ");
                        String completionDate = sc.nextLine();

                        try (final Connection connection = DriverManager.getConnection(URL);
                             final PreparedStatement statement = connection
                                                                .prepareStatement(get_total_labor_time)) {
                            System.out.println("Dispatching the query...");

                            statement.setInt(1, departmentNumber);
                            statement.setString(2, completionDate);

                            System.out.printf("Executing the stored procedure...\n");

                            final ResultSet resultSet = statement.executeQuery();
                                                
                            System.out.println("Total Labor Time:");

                            if (resultSet.next()) {
                                System.out.println(String.format("%d hours",
                                                                resultSet.getInt("Total_Labor_Time")));
                            } else {
                                System.out.println("No labor time data found for the specified department and date.");
                            }
                        } catch (SQLException e) {
                            System.out.println("An error occurred while executing the query.");
                            e.printStackTrace();
                        }
                        break;
                    // End of case "10"
                        
                    case "11":
                    	try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("Enter the Assembly ID for which you want process information:");
                            int assId = sc.nextInt();

                            try (PreparedStatement stmt = connection.prepareStatement(retrieveProcessesForAssembly)) {
                                stmt.setInt(1, assId);

                                ResultSet resultSet = stmt.executeQuery();
                                boolean hasResults = false;

                                System.out.println("\nRetrieving Process Information for Assembly ID: " + assId);
                                System.out.println("-----------------------------------------------------------");
                                System.out.printf("%-12s %-15s %-20s\n", "Process ID", "Start Date", "Department Number");
                                System.out.println("-----------------------------------------------------------");

                                while (resultSet.next()) {
                                    hasResults = true;
                                    int processId = resultSet.getInt("process_id");
                                    Date startDate = resultSet.getDate("start_date");
                                    int deptNumber = resultSet.getInt("department_number"); // Renamed variable to avoid duplication

                                    System.out.printf("%-12d %-15s %-20d\n", processId, startDate.toString(), deptNumber);
                                }

                                if (!hasResults) {
                                    System.out.println("No processes found for Assembly ID: " + assId);
                                }
                                System.out.println("-----------------------------------------------------------");
                            }
                        } catch (SQLException e) {
                            System.out.println("An error occurred while retrieving process information.");
                            e.printStackTrace();
                        }
                        break;
                        
                    case "12": 
                        try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("Enter the minimum category value:");
                            int categoryMin = sc.nextInt();
                            System.out.println("Enter the maximum category value:");
                            int categoryMax = sc.nextInt();

                            try (PreparedStatement stmt = connection.prepareStatement(retrieveCustomersByCategoryRange)) {
                                stmt.setInt(1, categoryMin);
                                stmt.setInt(2, categoryMax);

                                ResultSet resultSet = stmt.executeQuery();

                                System.out.println("Customers in Category Range (" + categoryMin + " to " + categoryMax + "):");
                                while (resultSet.next()) {
                                    String customerName = resultSet.getString("customer_name");
                                    System.out.println("Customer Name: " + customerName);
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("An error occurred while retrieving customer information.");
                            e.printStackTrace();
                        }
                      
                        break;
                        
                    case "13": 
                        try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("\n--- Delete Cut Jobs ---");
                            System.out.println("Enter the starting job number of the range:");
                            int jobNoFrom = sc.nextInt();
                            System.out.println("Enter the ending job number of the range:");
                            int jobNoTo = sc.nextInt();

                            try (PreparedStatement stmt = connection.prepareStatement(deleteCutJobs)) {
                                stmt.setInt(1, jobNoFrom);
                                stmt.setInt(2, jobNoTo);

                                int rowsAffected = stmt.executeUpdate();
                                System.out.println("\n" + rowsAffected + " cut job(s) deleted successfully.");
                                System.out.println("-----------------------");
                            }
                        } catch (SQLException e) {
                            System.out.println("\nError: Unable to delete cut jobs.");
                            System.out.println("-----------------------");
                            e.printStackTrace();
                        }
                        break;
                        
                        
                    case "14": 
                        try (Connection connection = DriverManager.getConnection(URL)) {
                            System.out.println("Enter the Job Number of the paint job you want to change:");
                            int jobNumber = sc.nextInt();
                            sc.nextLine(); // Consume the newline character left after nextInt
                            System.out.println("Enter the New Color for the paint job:");
                            String newColor = sc.nextLine();

                            try (PreparedStatement stmt = connection.prepareStatement(changePaintJobColor)) {
                                stmt.setInt(1, jobNumber);
                                stmt.setString(2, newColor);

                                int rowsAffected = stmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("The color of the paint job has been successfully updated.");
                                } else {
                                    System.out.println("No paint job was updated. Please check the Job Number.");
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("An error occurred while updating the paint job color.");
                            e.printStackTrace();
                        }
                        break;

                    case "15": 
                        System.out.println("Enter the file name:");
                        
                        while (!sc.hasNextLine()) {
                            try {
                                // Wait for the user to type input
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        
                        String inputFilePath = sc.nextLine();

                        try (Connection connection = DriverManager.getConnection(URL);
                             BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {

                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] customerData = line.split(","); // Adjust the delimiter as per your file format

                                if (customerData.length < 3) {
                                    System.out.println("Invalid customer data format: " + line);
                                    continue; // Skip to the next line if the format is not as expected
                                }

                                String customerName = customerData[0].trim();
                                String customerAddress = customerData[1].trim();
                                int category = Integer.parseInt(customerData[2].trim()); 
                                try (PreparedStatement stmt = connection.prepareStatement(ENTER_CUSTOMER_PROCEDURE)) {
                                    stmt.setString(1, customerName);
                                    stmt.setString(2, customerAddress);
                                    stmt.setInt(3, category);

                                }
                            }

                            System.out.println("File import complete!");
                        } catch (IOException e) {
                            System.out.println("An error occurred while reading the file: " + e.getMessage());
                            e.printStackTrace();
                        } catch (SQLException e) {
                            System.out.println("An error occurred while inserting customer data: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                        
               
                    
                        
                    case "16":

                    	 try (Connection connection = DriverManager.getConnection(URL)) {
                             System.out.println("Enter the minimum category value:");
                             int categoryMin = sc.nextInt();
                             System.out.println("Enter the maximum category value:");
                             int categoryMax = sc.nextInt();
                             sc.nextLine(); 

                             // Prompt for output file name
                             System.out.println("Enter the output file name:");
                             String outputFileName = sc.nextLine();

                             // Define the file path where the output will be saved
                             String outputFilePath = "C:/Users/Kaustubh/Downloads/" + outputFileName; 

                             try (PreparedStatement stmt = connection.prepareStatement(retrieveCustomersByCategoryRange);
                                  FileWriter writer = new FileWriter(outputFilePath)) {
                                 stmt.setInt(1, categoryMin);
                                 stmt.setInt(2, categoryMax);

                                 ResultSet resultSet = stmt.executeQuery();

                                 // Writing the results to the file
                                 while (resultSet.next()) {
                                     String customerName = resultSet.getString("customer_name");
                                     writer.write("Customer Name: " + customerName + "\n");
                                 }

                                 System.out.println("Customers in Category Range (" + categoryMin + " to " + categoryMax + ") exported to " + outputFilePath);
                             } catch (IOException e) {
                                 System.out.println("An error occurred while writing to the file.");
                                 e.printStackTrace();
                             }
                         } catch (SQLException e) {
                             System.out.println("An error occurred while retrieving customer information.");
                             e.printStackTrace();
                         }
                         break;
                        

                    case "17":
                        // User chose option 17, so we print a departing message
                        System.out.println("Exiting the program. Goodbye!");
                        break;
                    default:
                        // Unrecognized option, so we inform the user and ask them to try again
                        System.out.println(String.format(
                                "Invalid choice: %s\nPlease select a valid option.", option));
                        break;
                
                   
                    





                }
                }
            }
        }
    }

