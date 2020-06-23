package net.golikov.springdddexample.ddd.model.json.from.basic;

import com.fasterxml.jackson.databind.JsonNode;
import net.golikov.springdddexample.ddd.model.Student;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudentFromJson implements Student<GradeFromJson> {

    private final JsonNode objectNode;

    public StudentFromJson(JsonNode objectNode) {
        this.objectNode = objectNode;
    }

    @Override
    public String getName() {
        return objectNode
                .get("name")
                .asText();
    }

    @Override
    public List<GradeFromJson> getGrades() {
        JsonNode studentsNode = objectNode
                .get("students");
        GradesArrayFromJson gradesArrayFromJson = getGrades(studentsNode);
        return gradesArrayFromJson.getList();
    }

    @Lookup
    public GradesArrayFromJson getGrades(JsonNode students) {
        return null;
    }
}
