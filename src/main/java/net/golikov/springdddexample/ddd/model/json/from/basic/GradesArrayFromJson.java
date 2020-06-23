package net.golikov.springdddexample.ddd.model.json.from.basic;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class GradesArrayFromJson {

    private final JsonNode jsonNode;

    public GradesArrayFromJson(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public List<GradeFromJson> getList() {
        if (jsonNode == null) {
            return Collections.emptyList();
        }
        if (!jsonNode.isArray()) {
            throw new IllegalStateException("Not an array");
        }
        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map(this::createGrade)
                .collect(Collectors.toList());
    }

    @Lookup
    public GradeFromJson createGrade(JsonNode jsonNode) {
        return null;
    }

}
