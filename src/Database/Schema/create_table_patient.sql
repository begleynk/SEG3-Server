CREATE TABLE IF NOT EXISTS 'Patient' (
    'P_NHS_number' int(10) NOT NULL,
    'P_first_name' varchar(20) NOT NULL,
    'P_middle_name' varchar(20) NOT NULL,
    'P_surname' varchar(30) NOT NULL,
    'P_date_of_birth' date NOT NULL,
    'P_postcode' varchar(8) NOT NULL,
    'P_disability' varchar(30) DEFAULT NULL,
    PRIMARY KEY ('P_NHS_number'),
    UNIQUE ('P_first_name','P_middle_name','P_surname','P_date_of_birth') ON CONFLICT REPLACE
);