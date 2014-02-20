-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 20, 2014 at 02:37 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6
--
-- Database: 'SEG3'
--

-- --------------------------------------------------------

--
-- Table structure for table 'Admin'
--

CREATE TABLE IF NOT EXISTS 'Admin' (
  'A_username' varchar(20) NOT NULL,
  'A_pin' int(4) NOT NULL,
  PRIMARY KEY ('A_username','A_pin')
)

-- --------------------------------------------------------

--
-- Table structure for table 'Patient'
--

CREATE TABLE IF NOT EXISTS 'Patient' (
'P_NHS_number' int(10) NOT NULL,
'P_first_name' varchar(20) NOT NULL,
'P_middle_name' varchar(20) NOT NULL,
'P_surname' varchar(30) NOT NULL,
'P_date_of_birth' date NOT NULL,
'P_postcode' varchar(8) NOT NULL,
'P_disability' varchar(30) DEFAULT NULL,
PRIMARY KEY ('P_NHS_number'),
UNIQUE KEY 'P_first_name' ('P_first_name','P_middle_name','P_surname','P_date_of_birth'))

-- --------------------------------------------------------

--
-- Table structure for table 'Patient_Questionnaire'
--

CREATE TABLE IF NOT EXISTS 'Patient_Questionnaire' (
  'P_NHS_number' int(10) NOT NULL,
  'Q_id' int(8) NOT NULL,
  'Completed' tinyint(1) NOT NULL,
  PRIMARY KEY ('P_NHS_number','Q_id'),
  KEY 'Q_id' ('Q_id')
)


-- --------------------------------------------------------

--
-- Table structure for table 'Question'
--

CREATE TABLE IF NOT EXISTS 'Question' (
  'Question_id' int(8) NOT NULL,
  'Answer' varchar(300) DEFAULT NULL,
  PRIMARY KEY ('Question_id')
)

-- --------------------------------------------------------

--
-- Table structure for table 'Questionnaires'
--

CREATE TABLE IF NOT EXISTS 'Questionnaires' (
  'Q_id' int(8) NOT NULL,
  'Anything' varchar(5) DEFAULT NULL,
  PRIMARY KEY ('Q_id')
)

--
-- Constraints for dumped tables
--

--
-- Constraints for table 'Patient_Questionnaire'
--
ALTER TABLE 'Patient_Questionnaire'
  ADD CONSTRAINT 'patient_questionnaire_ibfk_1' FOREIGN KEY ('P_NHS_number') REFERENCES 'Patient' ('P_NHS_number') ON UPDATE CASCADE,
  ADD CONSTRAINT 'patient_questionnaire_ibfk_2' FOREIGN KEY ('Q_id') REFERENCES 'Questionnaire' ('Q_id') ON UPDATE CASCADE;