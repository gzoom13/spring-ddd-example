package net.golikov.springdddexample.ddd.model.groupsizecheck;

import net.golikov.springdddexample.ddd.model.database.PersistedStudent;
import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudyGroupWithSizeCheck<T extends PersistedStudent<?>> implements PersistedStudyGroup<T> {

    private final PersistedStudyGroup<T> delegate;
    private final DSLContext dslContext;

    public StudyGroupWithSizeCheck(PersistedStudyGroup<T> delegate, DSLContext dslContext) {
        this.delegate = delegate;
        this.dslContext = dslContext;
    }

    @Override
    public String getName() throws Exception {
        return delegate.getName();
    }

    @Override
    public List<T> getStudents() throws Exception {
        return delegate.getStudents();
    }

    @Override
    public long getDatabaseId() {
        return delegate.getDatabaseId();
    }

    public boolean isTooBig() throws Exception {
        // return dslContext.select(...)
        return false;
    }

    @Component
    public static class Factory {

        private final DSLContext dslContext;

        public Factory(DSLContext dslContext) {
            this.dslContext = dslContext;
        }

        public <T extends PersistedStudent<?>> StudyGroupWithSizeCheck<T> create(PersistedStudyGroup<T> studyGroup) {
            // dslContext.insertInto()
            return studyGroupWithSizeCheck(studyGroup);
        }

        @Lookup
        <T extends PersistedStudent<?>> StudyGroupWithSizeCheck<T> studyGroupWithSizeCheck(PersistedStudyGroup<T> studyGroup) {
            return null;
        }
    }

}
