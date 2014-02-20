ALTER TABLE 'Patient_Questionnaire'
  ADD CONSTRAINT 'patient_questionnaire_ibfk_1' FOREIGN KEY ('P_NHS_number') REFERENCES 'Patient' ('P_NHS_number') ON UPDATE CASCADE,
  ADD CONSTRAINT 'patient_questionnaire_ibfk_2' FOREIGN KEY ('Q_id') REFERENCES 'Questionnaire' ('Q_id') ON UPDATE CASCADE;