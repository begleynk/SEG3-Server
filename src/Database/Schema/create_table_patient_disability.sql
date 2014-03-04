CREATE TABLE IF NOT EXISTS 'Patient_Disability' (
  'P_disability' varchar(20) NOT NULL,
  'P_NHS_number' int(10) NOT NULL,
  PRIMARY KEY('P_NHS_number', 'P_disability'),
  FOREIGN KEY('P_NHS_number') REFERENCES 'Patient'('P_NHS_number'),
  FOREIGN KEY('P_disability') REFERENCES 'Disability'('P_disability')
  );