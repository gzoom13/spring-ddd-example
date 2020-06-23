package net.golikov.springdddexample.ddd.model.json.to.groupsizecheck;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.golikov.springdddexample.ddd.model.groupsizecheck.StudyGroupWithSizeCheck;
import net.golikov.springdddexample.ddd.model.json.to.StudentJson;
import net.golikov.springdddexample.ddd.model.json.to.StudyGroupJson;

import java.util.List;

public class StudyGroupWithSizeCheckJson<T extends StudentJson<?>> implements StudyGroupJson<T> {

    private final StudyGroupJson<T> json;
    private final StudyGroupWithSizeCheck<?> withSizeCheck;

    public StudyGroupWithSizeCheckJson(StudyGroupJson<T> json, StudyGroupWithSizeCheck<?> withSizeCheck) {
        this.json = json;
        this.withSizeCheck = withSizeCheck;
    }

    @Override
    public String getName() throws Exception {
        return json.getName();
    }

    @Override
    public List<T> getStudents() throws Exception {
        return json.getStudents();
    }

    @Override
    public ObjectNode toJson() throws Exception {
        return json.toJson()
                .put("tooBig", withSizeCheck.isTooBig());
    }
}
