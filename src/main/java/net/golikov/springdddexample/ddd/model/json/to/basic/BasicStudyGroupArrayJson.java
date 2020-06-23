package net.golikov.springdddexample.ddd.model.json.to.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.json.to.Json;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static net.golikov.springdddexample.ddd.model.json.to.Json.collectArray;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicStudyGroupArrayJson implements Json {

    private final ObjectMapper objectMapper;
    private final List<? extends StudyGroup<?>> studyGroups;

    public BasicStudyGroupArrayJson(ObjectMapper objectMapper, List<? extends StudyGroup<?>> studyGroups) {
        this.objectMapper = objectMapper;
        this.studyGroups = studyGroups;
    }

    public List<BasicStudyGroupJson> getStudyGroups() {
        return studyGroups.stream().map(this::basicStudyGroupJson).collect(Collectors.toList());
    }

    @Override
    public JsonNode toJson() throws Exception {
        return collectArray(objectMapper, getStudyGroups());
    }

    @Lookup
    BasicStudyGroupJson basicStudyGroupJson(StudyGroup<?> student) {
        return null;
    }
}
