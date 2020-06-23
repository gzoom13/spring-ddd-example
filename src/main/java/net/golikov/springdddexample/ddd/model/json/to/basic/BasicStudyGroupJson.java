package net.golikov.springdddexample.ddd.model.json.to.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.json.to.StudyGroupJson;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static net.golikov.springdddexample.ddd.model.json.to.Json.collectArray;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicStudyGroupJson implements StudyGroupJson<BasicStudentJson> {

    private final ObjectMapper objectMapper;
    private final StudyGroup<Student<?>> delegate;

    public BasicStudyGroupJson(ObjectMapper objectMapper, StudyGroup<Student<?>> delegate) {
        this.objectMapper = objectMapper;
        this.delegate = delegate;
    }

    @Override
    public String getName() throws Exception {
        return delegate.getName();
    }

    @Override
    public List<BasicStudentJson> getStudents() throws Exception {
        return delegate.getStudents().stream().map(this::basicStudentJson).collect(Collectors.toList());
    }

    @Override
    public JsonNode toJson() throws Exception {
        return objectMapper.createObjectNode()
                .put("name", getName())
                .set("students", collectArray(objectMapper, getStudents()));
    }

    @Lookup
    BasicStudentJson basicStudentJson(Student<?> student) {
        return null;
    }
}
