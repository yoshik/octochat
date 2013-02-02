# --- !Ups

CREATE SEQUENCE chatmodel_id_seq;
CREATE TABLE chatmodel (
    id integer NOT NULL DEFAULT nextval('chatmodel_id_seq'),
    message varchar(255),
    owner varchar(255),
    repo varchar(255),
    author varchar(255)
);
 
# --- !Downs
 
DROP TABLE chatmodel;
DROP SEQUENCE chatmodel_id_seq;