package net.golikov.springdddexample.ddd.model.database;

import net.golikov.springdddexample.ddd.model.StudyGroup;

public interface PersistedStudyGroup<T extends PersistedStudent<?>> extends StudyGroup<T>, Persisted {

    interface Factory<T extends PersistedStudyGroup<?>> {
        T persist(StudyGroup<?> studyGroup) throws Exception;
    }
}
