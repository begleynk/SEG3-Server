CREATE TABLE IF NOT EXISTS 'Admin' (
  'A_username' varchar(20) NOT NULL,
  'A_pin' int(4) NOT NULL,
  PRIMARY KEY ('A_username','A_pin')
);