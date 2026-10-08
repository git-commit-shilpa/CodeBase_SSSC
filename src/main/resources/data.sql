INSERT INTO MEMBER (memberid, username, password, fullname, role) VALUES (1, 'rflorin', 'test', 'Ryan Florin', 'admin');
INSERT INTO MEMBER (memberid, username, password, fullname, role) VALUES (2, 'kfindley', 'test', 'Kenneth Findley', 'admin');
INSERT INTO MEMBER (memberid, username, password, fullname, role) VALUES (3, 'jkastrounis', 'test', 'Jason Kastrounis', 'member');
INSERT INTO MEMBER (memberid, username, password, fullname, role) VALUES (4, 'azion', 'test', 'Amy Zion', 'member');


INSERT INTO MESSAGE (messageid, authorid, message) VALUES (1, 1, 'test');
INSERT INTO MESSAGE (messageid, authorid, message) VALUES (2, 2, 'test2');

ALTER TABLE MEMBER ALTER COLUMN memberid RESTART WITH 10;
ALTER TABLE MESSAGE ALTER COLUMN messageid RESTART WITH 10;