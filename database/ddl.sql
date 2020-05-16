CREATE TABLE IF NOT EXISTS conference (ame VARCHAR(100) NOT NULL, currentphase INT NOT NULL, startdate DATETIME NOT NULL, enddate DATETIME NOT NULL, submissiondeadline DATETIME NOT NULL, proposaldeadline DATETIME NOT NULL, biddingdeadline DATETIME NOT NULL, submitpaperearly BOOLEAN NOT NULL);
CREATE TABLE IF NOT EXISTS users (pk_userid INT AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(50) NOT NULL, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL, affiliation VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, webpage VARCHAR(50) NOT NULL, validated BOOLEAN NOT NULL, `type` INT NOT NULL);
CREATE TABLE IF NOT EXISTS papers (pk_paperid INT AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(100) NOT NULL, abstract VARCHAR(500) NOT NULL, field VARCHAR(100) NOT NULL, keywords VARCHAR(100) NOT NULL, topics VARCHAR(100) NOT NULL, documentpath VARCHAR(100) NOT NULL, status INT NOT NULL);
CREATE TABLE IF NOT EXISTS papersubmissions (fk_paperid INT, fk_userid INT, CONSTRAINT PK_UserSectionChoice PRIMARY KEY (fk_paperid, fk_userid), CONSTRAINT fk_papersubmissions_fk_paperid_pk_paperid FOREIGN KEY (fk_paperid) REFERENCES papers(pk_paperid) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_papersubmissions_fk_userid_pk_userid FOREIGN KEY (fk_userid) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE IF NOT EXISTS bidpapers (fk_userid INT, fk_paperid INT, paperbidresult INT NOT NULL, CONSTRAINT PK_BidPaper PRIMARY KEY (fk_userid, fk_paperid), CONSTRAINT fk_bidpapers_fk_userid_pk_userid FOREIGN KEY (fk_userid) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_bidpapers_fk_paperid_pk_paperid FOREIGN KEY (fk_paperid) REFERENCES papers(pk_paperid) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE IF NOT EXISTS reviews (fk_userid INT, fk_paperid INT, recommandation VARCHAR(500) NOT NULL, qualifier INT NOT NULL, CONSTRAINT PK_Review PRIMARY KEY (fk_userid, fk_paperid), CONSTRAINT fk_reviews_fk_userid_pk_userid FOREIGN KEY (fk_userid) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_reviews_fk_paperid_pk_paperid FOREIGN KEY (fk_paperid) REFERENCES papers(pk_paperid) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE IF NOT EXISTS sections (pk_sectionid INT AUTO_INCREMENT PRIMARY KEY, fk_sessionchair INT NULL, fk_userid INT NULL, `name` VARCHAR(100) NOT NULL, presentationdocumentpath VARCHAR(100) NOT NULL, starttime DATETIME NOT NULL, endtime DATETIME NOT NULL, roomname VARCHAR(100) NOT NULL, CONSTRAINT fk_sections_fk_sessionchair_pk_userid FOREIGN KEY (fk_sessionchair) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_sections_fk_userid_pk_userid FOREIGN KEY (fk_userid) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT);
CREATE TABLE IF NOT EXISTS usersectionchoices (fk_sectionid INT, fk_userid INT, CONSTRAINT PK_UserSectionChoice PRIMARY KEY (fk_sectionid, fk_userid), CONSTRAINT fk_usersectionchoices_fk_sectionid_pk_sectionid FOREIGN KEY (fk_sectionid) REFERENCES sections(pk_sectionid) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_usersectionchoices_fk_userid_pk_userid FOREIGN KEY (fk_userid) REFERENCES users(pk_userid) ON DELETE RESTRICT ON UPDATE RESTRICT);
