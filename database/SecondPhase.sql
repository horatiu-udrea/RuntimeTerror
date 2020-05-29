/*
 This file will be used to simulate the second phase of execution of our program:
 -- In the second phase, PC members are required to bid the proposals. Once the bidding
 -- process closed, the conference chair or co-chairs assign to each reviewer the papers
 -- required to be evaluated. By default any reviewer does not receive for evaluation a paper
 -- refused in the bidding phase. 2-4 reviewers. The possible results of each reviewer are found
 -- in the class diagram. In case of papers having contradictory evaluations the chair/co-chairs
 -- request reviewers to discuss in order to get closer evaluations. The result is justified
 -- by means of a set of recommendations that each reviewer attach to evaluated proposals.
 -- After receiving the acceptance decision, authors are invited to improve their accepted
 -- papers taking into account recommendations. PC members excepting the chair/co-chairs
 -- can submit proposals by logging as authors. In this case, they can't see neither which
 -- are the reviewers of their paper nor the comments between reviewers.
 --
 -- In order for the phase to be complete and correct we need to have gone through
 -- the first phase and preliminary phase which are described and created in the "FirstPhase.sql" and "PreliminaryPhase.sql"
 -- file.
 --
 -- To make things faster we will add all the things concerned with the first phase and preliminary phase
 -- in this file too. Thus we will be able to run this file and then we will have our
 -- database in the second phase of the execution.
 --
 -- In both the preliminary phase and the first phase files the execution of the phase was not completed.
 -- Here we will make sure that the execution is completed.
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
INSERT INTO cms.conference (name, currentphase, startdate, enddate, submissiondeadline, proposaldeadline, biddingdeadline, submitpaperearly) VALUES ('Oracle Java One', 2, '2020-09-01 14:00:00', '2020-09-03 20:00:00', '2020-08-01 22:00:00', '2020-08-15 22:00:00', '2020-08-20 22:00:00', 1);

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
-- 4 PCMember
-- 1 Co chair
-- 1 chair
 */
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Vlad Ungureanu', 'Vlad99', 'Vlad99', 'ubb Cluj', 'VU99@gmail.com', 'Vladweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Razvan Zoltan', 'Zoli99', 'Zoli99', 'ubb Cluj', 'RZ99@gmail.com', 'Zoliweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Andrei Suciu', 'Andrei99', 'Andrei99', 'ubb Cluj', 'AS99@gmail.com', 'Andreiweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Andrei Andrei', 'Andreii99', 'Andreii99', 'ubb Cluj', 'AAA99@gmail.com', 'Andreiiweb.com', 1, 2);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Antoniu Stan', 'Toni99', 'Toni99', 'ubb Cluj', 'TS99@gmail.com', 'Toniweb.com', 1, 3);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Darius Ternovan', 'Darius99', 'Darius99', 'ubb Cluj', 'DT99@gmail.com', 'Dariusweb.com', 1, 4);

/*
Authors:
-- 7 normal authors but we keep in mind another author that is a PC Member
-- the PC Member submitted both abstract and the full paper
-- 4 of them did not submit anything
-- 2 of them submitted only abstract
-- 1 of them submitted abstract + full paper
-- (because we set the conference to be special and to allow the authors to submit the full paper early)
 */

INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Mihai Berechet', 'Mihai99', 'Mihai99', 'Cambridge', 'MB99@gmail.com', 'Mihaiweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Corina Mihai', 'Corina99', 'Corina99', 'ubb Cluj', 'CM99@gmail.com', 'Corinaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Vlad Enache', 'VladE99', 'VladE99', 'poli Bucuresti', 'VE99@gmail.com', 'Vladeweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Ana Enache', 'Ana99', 'Ana99', 'ubb Cluj', 'AE99@gmail.com', 'Anaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Diana Sirbu', 'Diana99', 'Diana99', '-', 'DS99@gmail.com', 'Dianaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Bianca Berlea', 'Bianca99', 'Bianca99', 'poli Bucuresti', 'BB99@gmail.com', 'Biancaweb.com', 1, 1);
INSERT INTO cms.users (name, username, password, affiliation, email, webpage, validated, type) VALUES ('Bianca Caluian', 'BiancaC99', 'BiancaC99', 'ubb Cluj', 'BC99@gmail.com', 'BiancaCweb.com', 1, 1);

/*
Abstracts and Papers:
-- 8 entries in the Papers and Papers submission table
-- all of them will have the abstracts
-- 4 of them will also have the document path for the full paper
 */
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('soft pentru medicina', 'medicinaAbstract', 'medicina', 'medicina, software', 'medicina Topic', '', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('AiPaper', 'ITAbstract', 'ai', 'ai, artificial intelligence', 'ai Topic', '.aiPaper.pdf', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('soft pentru masini', 'masiniAbstract', 'masini', 'masini, software', 'masini Topic', '', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('MPPPaper', 'MPPAbstract', 'mpp', 'mpp, sdi', 'mpp Topic', '.mppPaper.pdf', 1);
/*
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('Fuzzy systems', 'fuzzyAbstract', 'fuzzy systems', 'fuzzy systems, fuzzy logic, software', 'fuzzy Topic', '', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('ISSPaper', 'ISSAbstract', 'iss', 'iss, software engineering', 'iss Topic', '.issPaper.pdf', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('Car parking soft', 'carAbstract', 'software, cars, car', 'medicina, software', 'car Topic', '', 1);
INSERT INTO cms.papers (name, abstract, field, keywords, topics, documentpath, status) VALUES ('WEBPaper', 'WEBAbstract', 'web', 'web, web programming', 'web Topic', '.webPaper.pdf', 1);
 */
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (1, 18) ON DUPLICATE KEY UPDATE fk_userid = 18;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (2, 19) ON DUPLICATE KEY UPDATE fk_userid = 19;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (3, 17) ON DUPLICATE KEY UPDATE fk_userid = 17;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (4, 16) ON DUPLICATE KEY UPDATE fk_userid = 16;
/*
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (5, 14) ON DUPLICATE KEY UPDATE fk_userid = 14;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (6, 13) ON DUPLICATE KEY UPDATE fk_userid = 13;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (7, 12) ON DUPLICATE KEY UPDATE fk_userid = 12;
INSERT INTO cms.papersubmissions (fk_paperid, fk_userid) VALUES (8, 9) ON DUPLICATE KEY UPDATE fk_userid = 9;
 */

/*
 Our 4 PC Members will bid for the 2 papers that we have so far
 -- For the first paper, 2 will bid "Pleased to review"/1, 1 "Indecisive"/2 and 1 "Refuse to Review"/3
 -- For the second paper, 1 will bid "Pleased to review"/1, 2 "Indecisive"/2 and 1 "Refuse to Review"/3
 */
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (7, 2, 1) ON DUPLICATE KEY UPDATE paperbidresult = 1;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (7, 4, 1) ON DUPLICATE KEY UPDATE paperbidresult = 1;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (8, 2, 1) ON DUPLICATE KEY UPDATE paperbidresult = 1;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (8, 4, 2) ON DUPLICATE KEY UPDATE paperbidresult = 2;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (9, 2, 2) ON DUPLICATE KEY UPDATE paperbidresult = 2;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (9, 4, 2) ON DUPLICATE KEY UPDATE paperbidresult = 2;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (10, 2, 3) ON DUPLICATE KEY UPDATE paperbidresult = 3;
INSERT INTO cms.bidpapers (fk_userid, fk_paperid, paperbidresult) VALUES (10, 4, 3) ON DUPLICATE KEY UPDATE paperbidresult = 3;

/*
 Normally we would need more data in any table, Papers, Users but for a quick test we will assign the reviewers papers to review
 -- 2 reviewers per paper
 -- initially when we assign a reviewer to a paper the review will have the 0 status "Not yet reviewed"
 -- We have 4 reviewers 2 papers
 -- For the first paper we will have both reviewer give a qualifier so that we can further update the status of the paper
 -- For the second paper we will have a reviewer with status "Not yet reviewed" and another one that actually got to review the paper
 */
INSERT INTO cms.reviews (fk_userid, fk_paperid, recommandation, qualifier) VALUES (7, 2, 'meh', 3) ON DUPLICATE KEY UPDATE qualifier = 0;
INSERT INTO cms.reviews (fk_userid, fk_paperid, recommandation, qualifier) VALUES (8, 2, 'git gud', 4) ON DUPLICATE KEY UPDATE qualifier = 4;
INSERT INTO cms.reviews (fk_userid, fk_paperid, recommandation, qualifier) VALUES (9, 4, '', 0) ON DUPLICATE KEY UPDATE qualifier = 0;
INSERT INTO cms.reviews (fk_userid, fk_paperid, recommandation, qualifier) VALUES (10, 4, 'do more research', 5) ON DUPLICATE KEY UPDATE qualifier = 4;

/*
 As a result our first paper has one positive review and no negative ones so it will be accepted
 */
UPDATE papers SET status = 2 WHERE pk_paperid = 2;