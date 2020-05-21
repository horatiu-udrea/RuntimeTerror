/*
 This file will be used to simulate the preliminary phase of execution of our program:
 -- In this preliminary phase we need to have already the conference created
 -- The users that can create the conferences are members of the steering committee
 -- We will have at least a steering committee user that created the conference
 -- Further more the preliminary phase implies multiple PC members uploading
 -- information about their name, affiliation, email address,
 -- their personal web page, their username for the CMS and the password
 -- for accessing the information about the conference.
 -- Further more, the chair or one of co-chairs have the permission to change deadlines
 -- by postponing them to later data.
 -- These PCMembers create their accounts so they initially will have the author user type
 -- The SCMember that created the conference or either SCMembers will need to switch the
 -- account type of the PCMembers from Author to PCMember / Chair / Co-chair
 -- For the purpose of testing this script will add PCMember / Co-chair accounts both
 -- that have their type already set and that do not have their type already set
 -- We will have the chair set so that we can test if he can change the deadline of the conference
 -- We will have some co-chairs set so that we can test if they can change some deadline of the conference

 */

DELETE FROM conference;
DELETE FROM papers;
DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;

/* The conference */
INSERT INTO cms.conference (name, currentphase, startdate, enddate, submissiondeadline, proposaldeadline, biddingdeadline, submitpaperearly) VALUES ('Oracle Java One', 0, '2020-09-01 14:00:00', '2020-09-03 20:00:00', '2020-08-01 22:00:00', '2020-08-15 22:00:00', '2020-08-20 22:00:00', 1);

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
-- 1 of them have the default Author
-- 2 PCMember
-- 1 Co chair
-- 1 chair
 */
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Vlad Ungureanu', 'Vlad99', 'Vlad99', 'ubb Cluj', 'VU99@gmail.com', 'Vladweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Razvan Zoltan', 'Zoli99', 'Zoli99', 'ubb Cluj', 'RZ99@gmail.com', 'Zoliweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Andrei Suciu', 'Andrei99', 'Andrei99', 'ubb Cluj', 'AS99@gmail.com', 'Andreiweb.com', 0, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Antoniu Stan', 'Toni99', 'Toni99', 'ubb Cluj', 'TS99@gmail.com', 'Toniweb.com', 1, 3);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Darius Ternovan', 'Darius99', 'Darius99', 'ubb Cluj', 'DT99@gmail.com', 'Dariusweb.com', 1, 4);


