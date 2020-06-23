package net.golikov.springdddexample.ddd.model.database.postgres;

import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
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
public class StudyGroupPg implements PersistedStudyGroup<StudentPg> {

    static final Field<Long> ID_FIELD = field("ID", Long.class);
    static final Field<String> NAME_FIELD = field("NAME", String.class);
    static final Table<Record> STUDY_GROUP_TABLE = table("STUDY_GROUP");

    private final long databaseId;
    private final DSLContext dslContext;
    private final StudentPg.Converter studentConverter;

    public StudyGroupPg(long databaseId, DSLContext dslContext, StudentPg.Converter studentConverter) {
        this.databaseId = databaseId;
        this.dslContext = dslContext;
        this.studentConverter = studentConverter;
    }

    @Override
    public String getName() {
        return dslContext.select(NAME_FIELD)
                .from(STUDY_GROUP_TABLE)
                .where(ID_FIELD.eq(databaseId)).fetchOne(NAME_FIELD);
    }

    @Override
    @Transactional
    public List<StudentPg> getStudents() {
        return dslContext.select(StudentPg.ID_FIELD)
                .from(StudentPg.STUDENT_TABLE)
                .where(StudentPg.STUDY_GROUP_ID.eq(databaseId))
                .fetch(StudentPg.ID_FIELD, studentConverter);
    }

    @Override
    public long getDatabaseId() {
        return databaseId;
    }


    @Component
    static class Converter extends AbstractConverter<Long, StudyGroupPg> {
        public Converter() {
            super(Long.class, StudyGroupPg.class);
        }

        @Override
        public StudyGroupPg from(Long id) {
            return createStudyGroup(id);
        }

        @Override
        public Long to(StudyGroupPg userObject) {
            return userObject.getDatabaseId();
        }

        @Lookup
        public StudyGroupPg createStudyGroup(long databaseId) {
            return null;
        }
    }
}
