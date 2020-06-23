package net.golikov.springdddexample.ddd.model.database;

import net.golikov.springdddexample.ddd.model.Student;

public interface PersistedStudent<T extends PersistedGrade> extends Student<T>, Persisted {

    interface Factory<P extends PersistedStudyGroup<T>, T extends PersistedStudent<?>> {
        T persist(P studyGroup, Student<?> student) throws Exception;
    }
}
