/*
Useful select queries
*/

/*
Select the name and submit paper early as text
*/
SELECT currentphase AS CurrentPhase, name AS ConferenceName,
       IF(submitpaperearly = 0, 'Authors cannot upload their full paper with their paper abstract.',
          'Authors can upload their full paper with their paper abstract.') AS ConferenceType
FROM conference;

/*
Select everything from the bidpapers table and display besides the user id, the username, password and type
*/
SELECT U.username AS Username, p.name AS PaperName,
CASE
    WHEN paperbidresult = 1 THEN 'Pleased to Review'
    WHEN paperbidresult = 2 THEN 'Indecisive'
    WHEN paperbidresult = 3 THEN 'Refuse to Review'
END AS PaperBidResult
FROM bidpapers
INNER JOIN users AS U ON bidpapers.fk_userid = U.pk_userid
INNER JOIN papers p on bidpapers.fk_paperid = p.pk_paperid;


/*
Select name and status from papers table
*/
SELECT name AS PaperName,
CASE
    WHEN status = 1 THEN 'Undecided'
    WHEN status = 2 THEN 'Accepted'
    WHEN status = 3 THEN 'Rejected'
    WHEN status = 4 THEN 'Conflicting'
END AS Status
FROM papers;

/*
Select username and paper name from the paper submissions table
*/
SELECT username AS Username, p.name AS PaperName FROM papersubmissions
INNER JOIN users ON papersubmissions.fk_userid = users.pk_userid
INNER JOIN papers p on papersubmissions.fk_paperid = p.pk_paperid;

/*
Select username, paper and qualifier
*/
SELECT username AS Username, p.name AS PaperName,
CASE
    WHEN qualifier = 0 THEN 'Not Yet Reviewed'
    WHEN qualifier = 1 THEN 'Strong Accept'
    WHEN qualifier = 2 THEN 'Accept'
    WHEN qualifier = 3 THEN 'Weak Accept'
    WHEN qualifier = 4 THEN 'Borderline Paper'
    WHEN qualifier = 5 THEN 'Weak Reject'
    WHEN qualifier = 6 THEN 'Reject'
    WHEN qualifier = 7 THEN 'Strong Reject'
END AS Qualifier
FROM reviews
INNER JOIN users u on reviews.fk_userid = u.pk_userid
INNER JOIN papers p on reviews.fk_paperid = p.pk_paperid;

/*
Select username, paper name and the name of the section
*/

SELECT a.username AS SessionChair, u.username AS AuthorUsername, p.name AS PaperName, sections.name AS SectionName FROM sections
INNER JOIN users u on sections.fk_userid = u.pk_userid
INNER JOIN papers p on sections.fk_paperid = p.pk_paperid
INNER JOIN users a on sections.fk_sessionchair = a.pk_userid;

/*
Select the name, username and password for certain actors
-- 1 - Author
-- 2 - Program Committee
-- 3 - Co Chair
-- 4 - Chair
-- 5 - Steering Committee
*/
SELECT name AS Name, username AS Username, password AS Password,
CASE
    WHEN type = 1 THEN 'Author'
    WHEN type = 2 THEN 'Program Committee'
    WHEN type = 3 THEN 'Co Chair'
    WHEN type = 4 THEN 'Chair'
    WHEN type = 5 THEN 'Steering Committee'
END AS Type
FROM users;