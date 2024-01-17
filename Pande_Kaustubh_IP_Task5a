-- Drop procedures if they already exist
DROP PROCEDURE IF EXISTS Q1;
DROP PROCEDURE IF EXISTS Q2;
DROP PROCEDURE IF EXISTS Q3;
DROP PROCEDURE IF EXISTS Q3_a;
DROP PROCEDURE IF EXISTS Q3_b;
DROP PROCEDURE IF EXISTS Q3_c;
DROP PROCEDURE IF EXISTS Q3_d;
DROP PROCEDURE IF EXISTS Q4;
DROP PROCEDURE IF EXISTS Q4_a;
DROP PROCEDURE IF EXISTS Q5;
DROP PROCEDURE IF EXISTS Q5_a;
DROP PROCEDURE IF EXISTS Q5_b;
DROP PROCEDURE IF EXISTS Q5_c;
DROP PROCEDURE IF EXISTS Q5_c;
DROP PROCEDURE IF EXISTS Q5_d;
DROP PROCEDURE IF EXISTS Q6;
DROP PROCEDURE IF EXISTS Q6_a;
DROP PROCEDURE IF EXISTS Q7;
DROP PROCEDURE IF EXISTS Q7_a;
DROP PROCEDURE IF EXISTS Q7_b;
DROP PROCEDURE IF EXISTS Q7_c;
DROP PROCEDURE IF EXISTS Q8;
DROP PROCEDURE IF EXISTS Q8_a;
DROP PROCEDURE IF EXISTS Q8_b;
DROP PROCEDURE IF EXISTS Q8_c;
DROP PROCEDURE IF EXISTS Q8_d;
DROP PROCEDURE IF EXISTS Q8_e;
DROP PROCEDURE IF EXISTS Q8_f;
DROP PROCEDURE IF EXISTS Q8_g;
DROP PROCEDURE IF EXISTS Q9;
DROP PROCEDURE IF EXISTS Q10;
DROP PROCEDURE IF EXISTS Q11;
DROP PROCEDURE IF EXISTS Q12;
DROP PROCEDURE IF EXISTS Q13;
DROP PROCEDURE IF EXISTS Q14;





-- Query 1: Enter a new customer -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO

CREATE PROCEDURE Q1 
    @customer_name VARCHAR(100), 
    @customer_address VARCHAR(100), 
    @category INT
AS
BEGIN
    INSERT INTO Customer (customer_name, customer_address, category) 
    VALUES (@customer_name, @customer_address, @category);
END;
GO

-- Query 2: Enter a new department  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 
CREATE PROCEDURE Q2
    @department_number INT,
    @department_data VARCHAR(100)
AS
BEGIN
    INSERT INTO Department (department_number, department_data) 
    VALUES (@department_number, @department_data);
END;

GO


-- Query 3: Inserting Processes-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

GO
CREATE PROCEDURE Q3
    @process_id INT,
    @process_data VARCHAR(100)
AS
BEGIN
    INSERT INTO Process(process_id, process_data)
    VALUES (@process_id, @process_data)
END;

-- Paint Process Process insertion
GO

CREATE PROCEDURE Q3_a
    @process_id INT,
    @paint_type VARCHAR(100),
    @painting_method VARCHAR(100)
AS
BEGIN
    INSERT INTO Paint_process(process_id, paint_type, painting_method)
    VALUES (@process_id, @paint_type, @painting_method)
END;

-- Cut Process insertion
GO
CREATE PROCEDURE Q3_b
    @process_id INT,
    @cutting_type VARCHAR(100),
    @machine_type VARCHAR(100)
AS
BEGIN
    INSERT INTO Cut_process(process_id, cutting_type, machine_type)
    VALUES (@process_id, @cutting_type, @machine_type)
END;

-- Fit Process insertion
GO
CREATE PROCEDURE Q3_c
    @process_id INT,
    @fit_type VARCHAR(100)
AS
BEGIN
    INSERT INTO Fit_process(process_id, fit_type)
    VALUES (@process_id, @fit_type)
END;

-- insert supervised
GO
CREATE PROCEDURE Q3_d
    @process_id INT,
    @department_number INT
AS 
BEGIN
    INSERT INTO supervised(process_id, department_number)
    VALUES (@process_id, @department_number)
END;




-- Query 4 : Enter Assembly with customer name -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q4
    @assembly_id INT,
    @date_ordered DATE,
    @details TEXT,
    @customer_name VARCHAR(100)
AS
BEGIN
    INSERT INTO Assembly (assembly_id, date_ordered, details)
    VALUES (@assembly_id, @date_ordered, @details);
END;

GO
CREATE PROCEDURE Q4_a
    @assembly_id INT,
    @process_id INT
AS
BEGIN
    INSERT INTO pass_through (process_id, assembly_id)
    VALUES (@process_id, @assembly_id);
END;


-- Query 5 : Create account and association-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q5
    @account_number INT,
    @date_established DATE
AS
BEGIN
    INSERT INTO Account (account_number, date_established)
    VALUES (@account_number, @date_established);
END;
-- Assembly account
GO
CREATE PROCEDURE Q5_a
    @account_number INT,
    @Assembly_cost float
AS
BEGIN
    INSERT INTO Assembly_account (account_number, Assembly_cost)
    VALUES (@account_number, @Assembly_cost);
END;

-- Process account
GO
CREATE PROCEDURE Q5_b
    @account_number INT,
    @Process_cost float
AS
BEGIN
    INSERT INTO Process_account (account_number, Process_cost)
    VALUES (@account_number, @Process_cost);
END;


-- Department account
GO
CREATE PROCEDURE Q5_d
    @account_number INT,
    @Dept_cost float
AS
BEGIN
    INSERT INTO Department_account (account_number, dept_cost)
    VALUES (@account_number, @dept_cost);
END;


-- Query 6 : Enter new Job -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q6
    @job_number INT,
    @start_date DATE,
    @end_date DATE
AS
BEGIN
    INSERT INTO Job(job_number, start_date, end_date)
    VALUES (@job_number, @start_date, @end_date)
END;

-- Inserting assigned
GO
CREATE PROCEDURE Q6_a
    @process_id INT,
    @assembly_id INT,
    @job_number INT
AS
BEGIN
    INSERT INTO assigned(process_id, assembly_id, job_number)
    VALUES (@process_id, @assembly_id, @job_number)
END;


-- Query 7 : At the completion of a job, enter the date it completed and the information relevant to the type of job -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q7
    @job_number INT,
    @end_date DATE
AS
BEGIN
    INSERT INTO Job(job_number, end_date)
    VALUES(@job_number, @end_date)
END;

-- Insert cut job
GO
CREATE PROCEDURE Q7_a
    @job_number INT,
    @machine_type VARCHAR(100),
    @cut_time_used TIME,
    @cut_material_used VARCHAR(100),
    @cut_labor_time TIME
AS
BEGIN
    INSERT INTO Cut_job (job_number, machine_type, time_used, material_used, labor_time)
    VALUES (@job_number, @machine_type, @cut_time_used, @cut_material_used, @cut_labor_time)
END;

-- Insert paint job
GO
CREATE PROCEDURE Q7_b
    @job_number INT,
    @paint_color VARCHAR(100),
    @paint_volume INT,
    @paint_labor_time TIME
AS
BEGIN
    INSERT INTO Paint_job (job_number, color, volume, labor_time)
    VALUES (@job_number, @paint_color, @paint_volume, @paint_labor_time);
END;

-- Insert fit job
GO
CREATE PROCEDURE Q7_c
    @job_number INT,
    @labor_time TIME
AS
BEGIN
    INSERT INTO Fit_job (job_number, labor_time)
    VALUES (@job_number, @labor_time);
END;

-- Query 8 : Transaction No and Sup-cost -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS Q8;
GO
CREATE PROCEDURE Q8
    @transaction_number INT,
    @sup_cost FLOAT
AS
BEGIN
    SET NOCOUNT ON;

    -- Inserting the transaction details into Cost_transaction table
    INSERT INTO Cost_transaction (transaction_number, sup_cost)
    VALUES (@transaction_number, @sup_cost);

    -- Update Assembly_account table
    UPDATE Assembly_account
    SET Assembly_cost = Assembly_cost + @sup_cost
    FROM Assembly_account AS aa
    INNER JOIN updates AS u ON aa.account_number = u.account_number
    WHERE u.transaction_number = @transaction_number;

    -- Update Process_account table
    UPDATE Process_account
    SET Process_cost = Process_cost + @sup_cost
    FROM Process_account AS pa
    INNER JOIN updates AS u ON pa.account_number = u.account_number
    WHERE u.transaction_number = @transaction_number;

    -- Update Department_account table
    UPDATE Department_account
    SET Dept_cost = Dept_cost + @sup_cost
    FROM Department_account AS da
    INNER JOIN updates AS u ON da.account_number = u.account_number
    WHERE u.transaction_number = @transaction_number;
END;
GO





-- Query 9 : Retrieve total cost-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS Q9;

GO
CREATE PROCEDURE Q9
    @AssemblyID INT
AS
BEGIN
    SELECT ct.Assembly_cost AS Total_Cost
    FROM Assembly_update au
    JOIN Assembly_account ct ON au.account_number = ct.account_number
    WHERE au.assembly_id = @AssemblyID;
END;

-- Query 10 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q10
    @DepartmentNumber INT,
    @CompletionDate DATE
AS
BEGIN
    SELECT SUM(ISNULL(DATEDIFF(MINUTE, '00:00:00', cj.labor_time), 0) 
               + ISNULL(DATEDIFF(MINUTE, '00:00:00', pj.labor_time), 0) 
               + ISNULL(DATEDIFF(MINUTE, '00:00:00', fj.labor_time), 0)) AS Total_Labor_Time
    FROM Department d
    JOIN supervised s ON d.department_number = s.department_number
    JOIN Assigned a ON s.process_id = a.process_id
    LEFT JOIN Job j ON a.job_number = j.job_number AND j.end_date = @CompletionDate
    LEFT JOIN Cut_job cj ON j.job_number = cj.job_number
    LEFT JOIN Paint_job pj ON j.job_number = pj.job_number
    LEFT JOIN Fit_job fj ON j.job_number = fj.job_number
    WHERE d.department_number = @DepartmentNumber
    AND j.end_date IS NOT NULL;
END;


-- Query 11 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

GO
CREATE PROCEDURE Q11
    @AssemblyID INT
AS
BEGIN
    SELECT p.process_id, j.start_date, d.department_number
    FROM Assembly a
    JOIN Assigned ass ON a.assembly_id = ass.assembly_id
    JOIN Job j ON ass.job_number = j.job_number
    JOIN Process p ON ass.process_id = p.process_id
    JOIN supervised s ON p.process_id = s.process_id
    JOIN Department d ON s.department_number = d.department_number
    WHERE a.assembly_id = @AssemblyID
    ORDER BY j.start_date;
END;

-- Query 12 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q12
    @CategoryMin INT,
    @CategoryMax INT
AS
BEGIN
    SELECT customer_name, category
    FROM Customer
    WHERE category BETWEEN @CategoryMin AND @CategoryMax
    ORDER BY customer_name;
END;

-- Query 13 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GO
CREATE PROCEDURE Q13
    @Job_No_from INT,
    @Job_No_to INT
AS
BEGIN
    DELETE FROM Cut_job
    WHERE job_number BETWEEN @Job_No_from AND @Job_No_to;
END;

-- Query 14 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

GO
CREATE PROCEDURE Q14
    @JobNumber INT,
    @NewColor VARCHAR(100)
AS
BEGIN
    UPDATE Paint_job
    SET color = @NewColor
    WHERE job_number = @JobNumber;
END;


