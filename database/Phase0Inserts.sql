DELETE FROM cms.conference;
DELETE FROM cms.users;

/*The insert for the Conference*/
INSERT INTO cms.conference (ame, currentphase, startdate, enddate, submissiondeadline, proposaldeadline, biddingdeadline, submitpaperearly)
VALUES ('Conference of White Teeth', 0, '2020-05-19 00:00:00', '2020-05-30 00:00:00', '2020-05-22 00:00:00', '2020-05-24 00:00:00', '2020-05-27 00:00:00', 0);

/*The SC Member*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (14457, 'John Luigus', 'JhonLuigus', 'mymomlovesme123', 'TeethDoctors', 'jhon.luigus@gmail.com', 'https://thispersondoesnotexist.com/', 1, 1, 1);

/*Two Default Users*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (26457, 'Marie Joseph', 'MarieKitty', 'KittyLuvsU2', 'FreshBreath', 'marie_joseph80@yahoo.com', 'arandomsite.com', 0, 0, 0);


INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (922, 'Lou Billy Bo', 'LouBilly70', 'strongPassword4', 'HygienicTeeth', 'lou.billy.bo@gmail.com', 'http://burymewithmymoney.com/', 0, 0, 0);

/*One Chair*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (62780, 'Rose Blue', 'RoseBlue', 'mindAndSoul54', 'BeautyFromWhiteTeeth', 'Rose_Blue@gmail.com', 'http://endless.horse/', 1, 1, 3);

/*One Co-Chair*/
INSERT INTO cms.users (pk_userid, name, username, password, affiliation, email, webpage, validated, hasticket, type)
VALUES (8202, 'Bob Fill', 'BobFill', 'ev45Rsf7', 'TheAssociationOfGoodDentists', 'BobFill@gmail.com', 'http://corndog.io/', 1, 1, 4);
