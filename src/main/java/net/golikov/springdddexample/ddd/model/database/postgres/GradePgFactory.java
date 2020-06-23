package net.golikov.springdddexample.ddd.model.database.postgres;

import net.golikov.springdddexample.ddd.model.Grade;
import org.jooq.DSLContext;
import org.jooq.Sequence;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static net.golikov.springdddexample.ddd.model.database.postgres.GradePg.*;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.sequence;

@Component
public class GradePgFactory implements Factory<StudentPg, GradePg> {
    static final Sequence<Long> GRADE_SEQUENCE = sequence(name("GRADE_SEQ"), Long.class);

    private final DSLContext dslContext;

    public GradePgFactory(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public GradePg persist(StudentPg student, Grade grade) throws Exception {
        Long id = dslContext.nextval(GRADE_SEQUENCE);
        dslContext.insertInto(GRADE_TABLE)
                .columns(GradePg.ID_FIELD, COURSE_FIELD, GRADE_FIELD, GradePg.STUDENT_ID_FIELD)
                .values(id, grade.getCourse(), grade.getGrade(), student.getDatabaseId())
                .execute();
        return findById(id);
    }

    @Lookup
    public GradePg findById(long databaseId) {
        return null;
    }
}
