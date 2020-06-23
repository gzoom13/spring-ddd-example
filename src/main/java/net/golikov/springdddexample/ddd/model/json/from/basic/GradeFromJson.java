package net.golikov.springdddexample.ddd.model.json.from.basic;

import com.fasterxml.jackson.databind.JsonNode;
import net.golikov.springdddexample.ddd.model.Grade;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class GradeFromJson implements Grade {

    private final JsonNode jsonNode;

    public GradeFromJson(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    @Override
    public String getCourse() {
        return jsonNode.get("course").asText();
    }

    @Override
    public String getGrade() {
        return jsonNode.get("grade").asText();
    }
}
