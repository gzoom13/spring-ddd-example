package net.golikov.springdddexample.ddd.model.database.postgres;

import net.golikov.springdddexample.ddd.model.database.PersistedStudent;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractConverter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudentPg implements PersistedStudent<GradePg> {

    static final Field<Long> ID_FIELD = field("ID", long.class);
    static final Field<Long> STUDY_GROUP_ID = field("STUDY_GROUP_ID", long.class);
    static final Field<String> NAME_FIELD = field("NAME", String.class);
    static final Table<Record> STUDENT_TABLE = table("STUDENT");

    private final long databaseId;
    private final DSLContext dslContext;
    private final GradePg.Converter gradeConverter;

    public StudentPg(long databaseId, DSLContext dslContext, GradePg.Converter gradeConverter) {
        this.databaseId = databaseId;
        this.dslContext = dslContext;
        this.gradeConverter = gradeConverter;
    }

    @Override
    public String getName() {
        return dslContext.select(NAME_FIELD)
                .from(STUDENT_TABLE)
                .where(ID_FIELD.eq(databaseId)).fetchOne(NAME_FIELD);
    }

    @Override
    @Transactional
    public List<GradePg> getGrades() {
        return dslContext.select(GradePg.ID_FIELD)
                .from(GradePg.GRADE_TABLE)
                .where(GradePg.STUDENT_ID_FIELD.eq(databaseId))
                .fetch(GradePg.ID_FIELD, gradeConverter);
    }

    @Override
    public long getDatabaseId() {
        return databaseId;
    }

    @Component
    static class Converter extends AbstractConverter<Long, StudentPg> {
        public Converter() {
            super(Long.class, StudentPg.class);
        }

        @Override
        public StudentPg from(Long id) {
            return createStudent(id);
        }

        @Override
        public Long to(StudentPg userObject) {
            return userObject.getDatabaseId();
        }

        @Lookup
        public StudentPg createStudent(long databaseId) {
            return null;
        }
    }
}
