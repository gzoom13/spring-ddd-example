package net.golikov.springdddexample.ddd.model.database.postgres;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractConverter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class GradePg implements net.golikov.springdddexample.ddd.model.database.PersistedGrade {

    static final Field<Long> ID_FIELD = field("ID", long.class);
    static final Field<Long> STUDENT_ID_FIELD = field("STUDENT_ID", long.class);
    static final Field<String> COURSE_FIELD = field("COURSE", String.class);
    static final Field<String> GRADE_FIELD = field("GRADE", String.class);
    static final Table<Record> GRADE_TABLE = table("GRADE");

    private final long databaseId;
    private final DSLContext dslContext;

    public GradePg(long databaseId, DSLContext dslContext) {
        this.databaseId = databaseId;
        this.dslContext = dslContext;
    }

    @Override
    public String getCourse() {
        return dslContext.select(COURSE_FIELD)
                .from(GRADE_TABLE)
                .where(field("ID").eq(databaseId)).fetchOne(COURSE_FIELD);
    }

    @Override
    public String getGrade() {
        return dslContext.select(GRADE_FIELD)
                .from(GRADE_TABLE)
                .where(field("ID").eq(databaseId)).fetchOne(GRADE_FIELD);
    }

    @Override
    public long getDatabaseId() {
        return databaseId;
    }

    @Component
    static class Converter extends AbstractConverter<Long, GradePg> {
        public Converter() {
            super(Long.class, GradePg.class);
        }

        @Override
        public GradePg from(Long id) {
            return createGrade(id);
        }

        @Override
        public Long to(GradePg userObject) {
            return userObject.getDatabaseId();
        }

        @Lookup
        public GradePg createGrade(long databaseId) {
            return null;
        }
    }
}
