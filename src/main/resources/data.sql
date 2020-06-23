CREATE SEQUENCE STUDY_GROUP_SEQ;
CREATE SEQUENCE STUDENT_SEQ;
CREATE SEQUENCE GRADE_SEQ;

CREATE TABLE STUDY_GROUP(
    ID NUMBER,
    NAME VARCHAR
);
CREATE TABLE STUDENT(
    ID NUMBER,
    NAME VARCHAR,
    STUDY_GROUP_ID NUMBER,
    CONSTRAINT FK_STUDENT_STUDY_GROUP FOREIGN KEY (STUDY_GROUP_ID)
    REFERENCES STUDY_GROUP(ID)
    on delete cascade
);
CREATE TABLE GRADE(
    ID NUMBER,
    COURSE VARCHAR,
    GRADE VARCHAR,
    STUDENT_ID NUMBER,
    CONSTRAINT FK_STUDENT_GRADE FOREIGN KEY (STUDENT_ID)
    REFERENCES STUDENT(ID)
    on delete cascade
);