package net.golikov.springdddexample.ddd.model.database;

import net.golikov.springdddexample.ddd.model.Grade;

public interface PersistedGrade extends Grade, Persisted {

    interface Factory<P extends PersistedStudent<T>, T extends PersistedGrade> {
        T persist(P student, Grade grade) throws Exception;
    }
}
