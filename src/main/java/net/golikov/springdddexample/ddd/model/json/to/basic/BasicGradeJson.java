package net.golikov.springdddexample.ddd.model.json.to.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.json.to.GradeJson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicGradeJson implements GradeJson {

    private final ObjectMapper objectMapper;
    private final Grade delegate;

    public BasicGradeJson(ObjectMapper objectMapper, Grade delegate) {
        this.objectMapper = objectMapper;
        this.delegate = delegate;
    }

    @Override
    public String getCourse() throws Exception {
        return delegate.getCourse();
    }

    @Override
    public String getGrade() throws Exception {
        return delegate.getGrade();
    }

    @Override
    public JsonNode toJson() throws Exception {
        return objectMapper.createObjectNode()
                .put("course", getCourse())
                .put("grade", getGrade());
    }
}
