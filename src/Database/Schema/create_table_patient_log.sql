CREATE TABLE IF NOT EXISTS 'Patient_Log' (
   'plkey' INTEGER PRIMARY KEY AUTOINCREMENT,
   'P_NHS_number_OLD' int(10),
   'P_NHS_number_NEW' int(10),
   'P_first_name_OLD' varchar(20),
   'P_first_name_NEW' varchar(20),
   'P_middle_name_OLD' varchar(20),
   'P_middle_name_NEW' varchar(20),
   'P_surname_OLD' varchar(20),
   'P_surname_NEW' varchar(20),
   'P_date_of_birth_OLD' DATE,
   'P_date_of_birth_NEW' DATE,
   'P_postcode_OLD' varchar(8),
   'P_postcode_NEW' varchar(8),
   'SQL_action' varchar(15),
   'Time_enter' DATE
);

CREATE TRIGGER update_Patient_Log AFTER UPDATE ON Patient
BEGIN
  INSERT INTO Patient_Log  (P_NHS_number_OLD, P_NHS_number_NEW, P_first_name_OLD, P_first_name_NEW,
                            P_middle_name_OLD, P_middle_name_NEW, P_surname_OLD, P_surname_NEW,
                            P_date_of_birth_OLD, P_date_of_birth_NEW, P_postcode_OLD, P_postcode_NEW, SQL_action, Time_enter)
          values (old.P_NHS_number,new.P_NHS_number,old.P_first_name,new.P_first_name,old.P_middle_name,
                  new.P_middle_name,old.P_surname, new.P_surname,old.P_date_of_birth, new.P_date_of_birth
                  old.P_postcode, new.P_postcode, 'UPDATE', DATETIME('NOW') );
END;

CREATE TRIGGER insert_Patient_Log AFTER INSERT ON Patient
BEGIN
  INSERT INTO Patient_Log  (P_NHS_number_NEW, P_first_name_NEW,
                            P_middle_name_NEW, P_surname_NEW,
                            P_date_of_birth_NEW, P_postcode_NEW, SQL_action, Time_enter)
          values (new.P_NHS_number,new.P_first_name,
                  new.P_middle_name,new.P_surname, new.P_date_of_birth
                  new.P_postcode, 'INSERT', DATETIME('NOW') );
END;

CREATE TRIGGER delete_Patient_Log DELETE ON Patient
BEGIN

  INSERT INTO Patient_Log  (P_NHS_number_OLD, P_first_name_OLD,
                            P_middle_name_OLD, P_surname_OLD,
                            P_date_of_birth_OLD, P_postcode_OLD, SQL_action, Time_enter)

          values (old.P_NHS_number,old.P_first_name,
                  old.P_middle_name,old.P_surname, old.P_date_of_birth
                  old.P_postcode, 'DELETE', DATETIME('NOW') );


END;




