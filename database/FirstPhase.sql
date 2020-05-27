/*
This file will be used to simulate the first phase of execution of our program:
-- In the first phase, each person interested to submit a proposal creates its
-- own account fulfilling the information required (at start all will be default,
-- non-validated users "Listeners").
-- Once the account is validated, the submitter logs in, submits the abstract
-- and fulfills the information required
-- Depending on the conference it is possible to upload also the full paper
-- (in db we will keep a relative path "./FolderFiles/filename". FolderFiles will
-- be a folder on the server where all the files uploaded by the users from the ui
-- will be saved.
--
-- In order for the phase to be complete and correct we need to have gone through
-- the preliminary phase which is described and created in the "PreliminaryPhase.sql"
-- file.
--
-- To make things faster we will add all the things concerned with the preliminary phase
-- in this file too. Thus we will be able to run this file and then we will have our
-- database in the first phase of the execution.
--
-- In the preliminary phase file the execution of the phase was not completed.
-- Here in this file we will make sure that the preliminary phase is completed.
 */

DELETE FROM conference;
DELETE FROM reviews;
DELETE FROM bidpapers;
DELETE FROM papersubmissions;
DELETE FROM usersectionchoices;
DELETE FROM sections;
DELETE FROM papers;
DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE papers AUTO_INCREMENT = 1;
ALTER TABLE sections AUTO_INCREMENT = 1;

/* The conference */
INSERT INTO cms.conference (name, currentphase, startdate, enddate, submissiondeadline, proposaldeadline, biddingdeadline, submitpaperearly) VALUES ('Oracle Java One', 1, '2020-09-01 14:00:00', '2020-09-03 20:00:00', '2020-08-01 22:00:00', '2020-08-15 22:00:00', '2020-08-20 22:00:00', 1);

/*
Steering committee members:
-- One of them logged into their account and in their respective UI
-- created the Conference
 */
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Jamil Zaitouny', 'Jamil99', 'Jamil99', 'ubb Cluj', 'JZ99@gmail.com', 'JZweb.com', 1, 5);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Horatiu Udrea', 'Hori99', 'Hori99', 'ubb Cluj', 'HI99@gmail.com', 'Horiweb.com', 1, 5);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Camelia Zalum', 'Cami99', 'Cami99', 'ubb Cluj', 'CI99@gmail.com', 'Camiweb.com', 1, 5);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Florin Ureche Undo', 'Undo99', 'Undo99', 'ubb Cluj', 'UO99@gmail.com', 'Undoweb.com', 1, 5);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Alina Zsok', 'Alina99', 'Alina99', 'ubb Cluj', 'AA99@gmail.com', 'Alinaweb.com', 1, 5);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Razvan Toma', 'Razvi99', 'Razvi99', 'ubb Cluj', 'RI99@gmail.com', 'Razviweb.com', 1, 5);


/*
Program committee members:
-- 3 PCMember
-- 1 Co chair
-- 1 chair
 */
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Vlad Ungureanu', 'Vlad99', 'Vlad99', 'ubb Cluj', 'VU99@gmail.com', 'Vladweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Razvan Zoltan', 'Zoli99', 'Zoli99', 'ubb Cluj', 'RZ99@gmail.com', 'Zoliweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Andrei Suciu', 'Andrei99', 'Andrei99', 'ubb Cluj', 'AS99@gmail.com', 'Andreiweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Antoniu Stan', 'Toni99', 'Toni99', 'ubb Cluj', 'TS99@gmail.com', 'Toniweb.com', 1, 3);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Darius Ternovan', 'Darius99', 'Darius99', 'ubb Cluj', 'DT99@gmail.com', 'Dariusweb.com', 1, 4);

/*
Authors:
-- 3 of them have the "Author" default type and are not validated
-- 2 of them have the "Author" type, they are validated but they did not submit anything
-- 1 of them has the "Author" type, is validated and also submitted the abstract
-- 1 of them has the "Author" type, is validated and submitted abstract + full paper
-- (because we set the conference to be special and to allow the authors to submit the full paper early)
 */

INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Mihai Berechet', 'Mihai99', 'Mihai99', 'Cambridge', 'MB99@gmail.com', 'Mihaiweb.com', 0, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Corina Mihai', 'Corina99', 'Corina99', 'ubb Cluj', 'CM99@gmail.com', 'Corinaweb.com', 0, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Vlad Enache', 'VladE99', 'VladE99', 'poli Bucuresti', 'VE99@gmail.com', 'Vladeweb.com', 0, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Ana Enache', 'Ana99', 'Ana99', 'ubb Cluj', 'AE99@gmail.com', 'Anaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Diana Sirbu', 'Diana99', 'Diana99', '-', 'DS99@gmail.com', 'Dianaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Bianca Berlea', 'Bianca99', 'Bianca99', 'poli Bucuresti', 'BB99@gmail.com', 'Biancaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Bianca Caluian', 'BiancaC99', 'BiancaC99', 'ubb Cluj', 'BC99@gmail.com', 'BiancaCweb.com', 1, 1);

/*
Abstract and Paper:
-- 1 of the validated author submitted the abstract
-- 1 of the validated author submitted the abstract and the full paper
-- We will have an entry in the PaperSubmissions table for both situations
 */
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('soft pentru medicina', 'medicinaAbstract', 'medicina', 'medicina, software', 'medicina Topic', '', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('AiPaper', 'ITAbstract', 'ai', 'ai, artificial intelligence', 'ai Topic', './FolderFiles/aiPaper.pdf', 1);
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (1, 17) ON DUPLICATE KEY UPDATE fk_userid = 17;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (2, 18) ON DUPLICATE KEY UPDATE fk_userid = 18;