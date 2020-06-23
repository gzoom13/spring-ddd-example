package net.golikov.springdddexample.ddd.model.json.to.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.json.to.StudentJson;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static net.golikov.springdddexample.ddd.model.json.to.Json.collectArray;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicStudentJson implements StudentJson<BasicGradeJson> {

    private final ObjectMapper objectMapper;
    private final Student<Grade> delegate;

    public BasicStudentJson(ObjectMapper objectMapper, Student<Grade> delegate) {
        this.objectMapper = objectMapper;
        this.delegate = delegate;
    }

    @Override
    public String getName() throws Exception {
        return delegate.getName();
    }

    @Override
    public List<BasicGradeJson> getGrades() throws Exception {
        return delegate.getGrades().stream().map(this::basicGradeJson).collect(Collectors.toList());
    }

    @Override
    public JsonNode toJson() throws Exception {
        return objectMapper.createObjectNode()
                .put("name", getName())
                .set("grades", collectArray(objectMapper, getGrades()));
    }

    @Lookup
    BasicGradeJson basicGradeJson(Grade grade) {
        return null;
    }
}
