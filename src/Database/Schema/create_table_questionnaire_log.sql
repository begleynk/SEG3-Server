CREATE TABLE IF NOT EXISTS 'Questionnaire_Log' (
   'qlkey' INTEGER PRIMARY KEY AUTOINCREMENT,
   'Q_id_OLD' INTEGER,
   'Q_id_NEW' INTEGER,
   'Q_title_OLD' VARCHAR(250),
   'Q_title_NEW' VARCHAR(250),
   'Q_state_OLD' TEXT,
   'Q_state_NEW' TEXT,
   'SQL_action' varchar(15),
   'Time_enter' DATE
);

CREATE TRIGGER update_Questionnaire_Log AFTER UPDATE  ON Questionnaire
BEGIN

  INSERT INTO Questionnaire_Log  (Q_id_OLD, Q_id_NEW, Q_title_OLD, Q_title_NEW,
                            Q_state_OLD, Q_state_NEW, SQL_action, Time_enter)

      values (old.Q_id,new.Q_id,old.Q_title,new.Q_title,old.Q_state,
                  new.Q_state, 'UPDATE', DATETIME('NOW') );


END;

CREATE TRIGGER insert_Questionnaire_Log AFTER INSERT  ON Questionnaire
BEGIN

  INSERT INTO Questionnaire_Log  (Q_id_NEW, Q_title_NEW,
                            Q_state_NEW, SQL_action, Time_enter)

      values (new.Q_id,new.Q_title, new.Q_state, 'INSERT', DATETIME('NOW') );

END;

CREATE TRIGGER delete_Questionnaire_Log DELETE  ON Questionnaire
BEGIN

  INSERT INTO Questionnaire_Log  (Q_id_OLD, Q_title_OLD, Q_state_OLD, SQL_action, Time_enter)


      values (old.Q_id, old.Q_title, old.Q_state, 'DELETE', DATETIME('NOW') );


END;




