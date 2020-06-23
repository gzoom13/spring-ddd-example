package net.golikov.springdddexample.ddd.model.json.from.basic;

import com.fasterxml.jackson.databind.JsonNode;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudyGroupFromJson implements StudyGroup<StudentFromJson> {

    private final JsonNode objectNode;

    public StudyGroupFromJson(JsonNode objectNode) {
        this.objectNode = objectNode;
    }

    @Override
    public String getName() {
        return objectNode
                .get("name")
                .asText();
    }

    @Override
    public List<StudentFromJson> getStudents() {
        JsonNode studentsNode = objectNode
                .get("students");
        StudentArrayFromJson studentArrayFromJson = getStudents(studentsNode);
        return studentArrayFromJson.getList();
    }

    @Lookup
    public StudentArrayFromJson getStudents(JsonNode students) {
        return null;
    }
}
