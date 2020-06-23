package net.golikov.springdddexample.ddd.model.database.postgres;

import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.Student;
import org.jooq.DSLContext;
import org.jooq.Sequence;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static net.golikov.springdddexample.ddd.model.database.postgres.StudentPg.*;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.sequence;

@Component
public class StudentPgFactory implements Factory<StudyGroupPg, StudentPg> {
    static final Sequence<Long> STUDENT_SEQUENCE = sequence(name("STUDENT_SEQ"), Long.class);

    private final DSLContext dslContext;
    private final GradePgFactory gradePgFactory;

    public StudentPgFactory(DSLContext dslContext, GradePgFactory gradePgFactory) {
        this.dslContext = dslContext;
        this.gradePgFactory = gradePgFactory;
    }

    @Transactional
    @Override
    public StudentPg persist(StudyGroupPg studyGroup, Student<?> student) throws Exception {
        Long id = dslContext.nextval(STUDENT_SEQUENCE);
        dslContext.insertInto(STUDENT_TABLE)
                .columns(ID_FIELD, NAME_FIELD, STUDY_GROUP_ID)
                .values(id, student.getName(), studyGroup.getDatabaseId())
                .execute();
        StudentPg persistedStudent = findById(id);
        for (Grade grade : student.getGrades()) {
            gradePgFactory.persist(persistedStudent, grade);
        }
        return persistedStudent;
    }

    @Lookup
    public StudentPg findById(long databaseId) {
        return null;
    }
}
