package net.golikov.springdddexample.ddd.model.json.to;

import net.golikov.springdddexample.ddd.model.StudyGroup;

public interface StudyGroupJson<T extends StudentJson<?>> extends Json, StudyGroup<T> {
}
