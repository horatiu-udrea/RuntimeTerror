DELETE FROM cms.conference;
DELETE FROM cms.papers;
DELETE FROM cms.users;

/*Insert of the conference for PHASE 1*/
/*Same deadlines just the phase is different*/
INSERT INTO cms.conference (ame, currentphase, startdate, enddate, submissiondeadline, proposaldeadline, biddingdeadline, submitpaperearly)
VALUES ('Conference of White Teeth', 1, '2020-05-19 00:00:00', '2020-05-30 00:00:00', '2020-05-22 00:00:00', '2020-05-24 00:00:00', '2020-05-27 00:00:00', 0);

/*User inserts*/
/*Same from the Phase 0*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (14457, 'John Luigus', 'JhonLuigus', 'mymomlovesme123', 'TeethDoctors', 'jhon.luigus@gmail.com', 'https://thispersondoesnotexist.com/', 1, 1, 1);

INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (26457, 'Marie Joseph', 'MarieKitty', 'KittyLuvsU2', 'FreshBreath', 'marie_joseph80@yahoo.com', 'arandomsite.com', 0, 0, 0);

INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (922, 'Lou Billy Bo', 'LouBilly70', 'strongPassword4', 'HygienicTeeth', 'lou.billy.bo@gmail.com', 'http://burymewithmymoney.com/', 0, 0, 0);

INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (62780, 'Rose Blue', 'RoseBlue', 'mindAndSoul54', 'BeautyFromWhiteTeeth', 'Rose_Blue@gmail.com', 'http://endless.horse/', 1, 1, 3);

INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (8202, 'Bob Fill', 'BobFill', 'ev45Rsf7', 'TheAssociationOfGoodDentists', 'BobFill@gmail.com', 'http://corndog.io/', 1, 1, 4);

/*Author that is not validated*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (85182, 'Miriam Louis', 'MiriLou', 'bubblesA4', 'FreshBreath', 'miri.lou@yahoo.com', 'http://www.rrrgggbbb.com/', 0, 0, 5);

/*Author that is validated*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (72878, 'Bill Doby', 'BillDoby', 'rainbow1978', 'WhiteTeeth', 'bill_doby78@gmail.com', 'http://randomcolour.com/', 1, 0, 5);

/*Paper Inserts*/
INSERT INTO cms.papers (pk_paperid, fk_userid, name, field, keywords, topics, authors, documentpath, accepted, conflicting)
VALUES (768, 72878, 'The power of white teeth', 'dentistry', 'whitening', 'How beautiful teeth help your life', 'Kayle Medd', '', 0, 0);





