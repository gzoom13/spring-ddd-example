package net.golikov.springdddexample.ddd.model.database.postgres;

import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
import org.jooq.DSLContext;
import org.jooq.Sequence;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.golikov.springdddexample.ddd.model.database.postgres.StudyGroupPg.*;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.sequence;

@Component
public class StudyGroupPgFactory implements PersistedStudyGroup.Factory<StudyGroupPg> {
    static final Sequence<Long> STUDY_GROUP_SEQUENCE = sequence(name("STUDY_GROUP_SEQ"), Long.class);

    private final DSLContext dslContext;
    private final StudentPgFactory studentPgFactory;
    private final Converter studyGroupPgConverter;

    public StudyGroupPgFactory(DSLContext dslContext,
                               StudentPgFactory studentPgFactory,
                               StudyGroupPg.Converter studyGroupPgConverter) {
        this.dslContext = dslContext;
        this.studentPgFactory = studentPgFactory;
        this.studyGroupPgConverter = studyGroupPgConverter;
    }

    @Override
    @Transactional
    public StudyGroupPg persist(StudyGroup<?> studyGroup) throws Exception {
        Long id = dslContext.nextval(STUDY_GROUP_SEQUENCE);
        dslContext.insertInto(StudyGroupPg.STUDY_GROUP_TABLE)
                .columns(ID_FIELD, NAME_FIELD)
                .values(id, studyGroup.getName())
                .execute();
        StudyGroupPg persistedStudyGroup = findById(id);
        for (Student<?> student : studyGroup.getStudents()) {
            studentPgFactory.persist(persistedStudyGroup, student);
        }
        return persistedStudyGroup;
    }

    public List<StudyGroupPg> getAllStudyGroups() {
        return dslContext.select(ID_FIELD)
                .from(STUDY_GROUP_TABLE)
                .fetch(ID_FIELD, studyGroupPgConverter);
    }

    @Lookup
    public StudyGroupPg findById(long databaseId) {
        return null;
    }
}
