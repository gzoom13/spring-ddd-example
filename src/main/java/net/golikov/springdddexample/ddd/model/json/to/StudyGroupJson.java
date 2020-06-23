package net.golikov.springdddexample.ddd.model.json.to;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.golikov.springdddexample.ddd.model.StudyGroup;

public interface StudyGroupJson<T extends StudentJson<?>> extends Json, StudyGroup<T> {
    @Override
    ObjectNode toJson() throws Exception;
}
