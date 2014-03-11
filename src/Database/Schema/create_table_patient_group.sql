CREATE TABLE IF NOT EXISTS 'Patient_Group' (
  'P_group' varchar(20) NOT NULL,
  'P_NHS_number' int(10) NOT NULL,
  PRIMARY KEY('P_NHS_number', 'P_group'),
  FOREIGN KEY('P_NHS_number') REFERENCES 'Patient'('P_NHS_number'),
  FOREIGN KEY('P_group') REFERENCES 'Group'('P_group')
  );