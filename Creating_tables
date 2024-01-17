DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Department_update;
DROP TABLE IF EXISTS updates;

DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS assigned;
DROP TABLE IF EXISTS supervised;

DROP TABLE IF EXISTS Fit_job;
DROP TABLE IF EXISTS Paint_job;
DROP TABLE IF EXISTS Cut_job;

DROP TABLE IF EXISTS pass_through;

DROP TABLE IF EXISTS Fit_process;
DROP TABLE IF EXISTS Paint_process;
DROP TABLE IF EXISTS Cut_process;
DROP TABLE IF EXISTS Department_update;
DROP TABLE IF EXISTS Assembly_update;
DROP TABLE IF EXISTS Process_update;

DROP TABLE IF EXISTS Process_cost;
DROP TABLE IF EXISTS Assembly_cost;
DROP TABLE IF EXISTS Department_cost;

DROP TABLE IF EXISTS Cost_transaction;

DROP TABLE IF EXISTS Department_account;
DROP TABLE IF EXISTS Process_account;
DROP TABLE IF EXISTS Assembly_account;

DROP TABLE IF EXISTS Account;

DROP TABLE IF EXISTS Job;

DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Process;
DROP TABLE IF EXISTS Assembly;

DROP TABLE IF EXISTS Customer;







-- Creating tables with primary and foreign key constraints

CREATE TABLE Customer (
    customer_name VARCHAR(255),
    customer_address VARCHAR(255),
    category INT,
    PRIMARY KEY (customer_name)
);

CREATE TABLE Assembly (
    assembly_id INT,
    date_ordered DATE,
    details TEXT,
    PRIMARY KEY (assembly_id)
);

CREATE TABLE Process (
    process_id INT,
    process_data TEXT,
    PRIMARY KEY (process_id)
);

CREATE TABLE Department (
    department_number INT,
    department_data TEXT,
    PRIMARY KEY (department_number)
);

CREATE TABLE Job (
    job_number INT,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY (job_number)
);

CREATE TABLE Account (
    account_number INT,
    date_established DATE,
    PRIMARY KEY (account_number)
);

CREATE TABLE Assembly_account (
    account_number INT,
    Assembly_cost float
    PRIMARY KEY (account_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_AssemblyAccount_Account FOREIGN KEY (account_number) REFERENCES Account(account_number)
);

CREATE TABLE Process_account (
    account_number INT,
    Process_cost float,
    PRIMARY KEY (account_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_ProcessAccount_Account FOREIGN KEY (account_number) REFERENCES Account(account_number)
);

CREATE TABLE Department_account (
    account_number INT,
    Dept_cost float
    PRIMARY KEY (account_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_DepartmentAccount_Account FOREIGN KEY (account_number) REFERENCES Account(account_number)
);

CREATE TABLE Cost_transaction (
    transaction_number INT,
    sup_cost float,
    PRIMARY KEY (transaction_number)
);

CREATE TABLE Process_update (
    account_number INT,
    process_id INT,
    job_number INT,
    CONSTRAINT FK_Pro_acc FOREIGN KEY (account_number) REFERENCES Account(account_number),
    CONSTRAINT FK_Pro_id FOREIGN KEY (process_id) REFERENCES Process(process_id),
    CONSTRAINT FK_Pro_jn FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Assembly_update (
    account_number INT,
    assembly_id INT,
    job_number INT,
    CONSTRAINT FK_Pro_assemb FOREIGN KEY (account_number) REFERENCES Account(account_number),
    CONSTRAINT FK_Pro_asid FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id),
    CONSTRAINT FK_Pro_acjn FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Department_update (
    account_number INT,
    department_number INT,
    process_id INT,
    FOREIGN KEY (account_number) REFERENCES Account(account_number),
    FOREIGN KEY (department_number) REFERENCES Department(department_number),
    FOREIGN KEY (process_id) REFERENCES Process(process_id)
);



CREATE TABLE Cut_process (
    process_id INT,
    cutting_type VARCHAR(100),
    machine_type VARCHAR(100),
    PRIMARY KEY (process_id),

    -- Foreign Key Constraint
    CONSTRAINT FK_CutProcess_Process FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Paint_process (
    process_id INT,
    paint_type VARCHAR(100),
    painting_method VARCHAR(100),
    PRIMARY KEY (process_id),

    -- Foreign Key Constraint
    CONSTRAINT FK_PaintProcess_Process FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE Fit_process (
    process_id INT,
    fit_type VARCHAR(100),
    PRIMARY KEY (process_id),

    -- Foreign Key Constraint
    CONSTRAINT FK_FitProcess_Process FOREIGN KEY (process_id) REFERENCES Process(process_id)
);

CREATE TABLE pass_through (
    process_id INT,
    assembly_id INT,

    -- Primary Key Constraint
    CONSTRAINT PK_passthrough PRIMARY KEY (process_id, assembly_id),

    -- Foreign Key Constraint
    CONSTRAINT FK_pass_through_Process FOREIGN KEY (process_id) REFERENCES Process(process_id),
    CONSTRAINT FK_pass_through_Assembly FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id)
);

CREATE TABLE Cut_job (
    job_number INT,
    machine_type VARCHAR(100),
    time_used TIME,
    material_used VARCHAR(100),
    labor_time TIME,
    PRIMARY KEY (job_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_CutJob_Job FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Paint_job (
    job_number INT,
    color VARCHAR(100),
    volume INT,
    labor_time TIME,
    PRIMARY KEY (job_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_PaintJob_Job FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE Fit_job (
    job_number INT,
    labor_time TIME,
    PRIMARY KEY (job_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_FitJob_Job FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE supervised (
    process_id INT,
    department_number INT,

    -- Primary Key Constraint
    CONSTRAINT PK_supervised PRIMARY KEY (process_id, department_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_supervise_Process FOREIGN KEY (process_id) REFERENCES Process(process_id),
    CONSTRAINT FK_supervise_Department FOREIGN KEY (department_number) REFERENCES Department(department_number)
);

CREATE TABLE assigned (
    process_id INT,
    assembly_id INT,
    job_number INT,

    -- Primary Key Constraint
    CONSTRAINT PK_assigned PRIMARY KEY (process_id, assembly_id, job_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_assigned_Process FOREIGN KEY (process_id) REFERENCES Process(process_id),
    CONSTRAINT FK_assigned_Assembly FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id),
    CONSTRAINT FK_assigned_Job FOREIGN KEY (job_number) REFERENCES Job(job_number)
);

CREATE TABLE records (
    job_number INT,
    transaction_number INT,

    -- Primary Key Constraint
    CONSTRAINT PK_recods PRIMARY KEY (job_number, transaction_number),

    -- Foreign Key Constraint
    CONSTRAINT FK_records_Job FOREIGN KEY (job_number) REFERENCES Job(job_number),
    CONSTRAINT FK_records_CostTransaction FOREIGN KEY (transaction_number) REFERENCES Cost_transaction(transaction_number)
);

CREATE TABLE updates (
    account_number INT,
    transaction_number INT,
    PRIMARY KEY (account_number, transaction_number),
    FOREIGN KEY (account_number) REFERENCES Account(account_number),
    FOREIGN KEY (transaction_number) REFERENCES Cost_transaction(transaction_number)
);


CREATE TABLE Orders (
    customer_name VARCHAR(255),
    assembly_id INT,
    PRIMARY KEY (customer_name, assembly_id),
    FOREIGN KEY (customer_name) REFERENCES Customer(customer_name),
    FOREIGN KEY (assembly_id) REFERENCES Assembly(assembly_id)
);






