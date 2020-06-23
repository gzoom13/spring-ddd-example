package net.golikov.springdddexample.ddd.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
import net.golikov.springdddexample.ddd.model.indexed.StudyGroupIndexed;
import net.golikov.springdddexample.ddd.model.indexed.StudyGroupIndexedFactory;
import net.golikov.springdddexample.ddd.model.json.from.basic.StudyGroupFromJson;
import net.golikov.springdddexample.ddd.model.json.to.basic.BasicStudyGroupArrayJson;
import net.golikov.springdddexample.ddd.model.json.to.basic.BasicStudyGroupJson;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StudyGroupController {

    private final ObjectMapper objectMapper;
    private final StudyGroupIndexedFactory studyGroupIndexedFactory;

    public StudyGroupController(ObjectMapper objectMapper, StudyGroupIndexedFactory studyGroupIndexedFactory) {
        this.objectMapper = objectMapper;
        this.studyGroupIndexedFactory = studyGroupIndexedFactory;
    }

    @GetMapping(value = "/study-groups")
    public String getAllStudyGroups() throws Exception {
        List<StudyGroupIndexed> allStudyGroups = studyGroupIndexedFactory.getAllStudyGroups();
        JsonNode studyGroupsJson = jsonStudyGroupArray(allStudyGroups).toJson();
        return objectMapper.writeValueAsString(studyGroupsJson);
    }

    @PostMapping(value = "/study-groups", consumes = APPLICATION_JSON_VALUE)
    public String saveStudyGroup(@RequestBody JsonNode json) throws Exception {
        StudyGroupFromJson studyGroupFromJson = studyGroupFrom(json);
        PersistedStudyGroup<?> persistedStudyGroup = studyGroupIndexedFactory.persist(studyGroupFromJson);
        return objectMapper.writeValueAsString(jsonStudyGroup(persistedStudyGroup).toJson());
    }

    @Lookup
    StudyGroupFromJson studyGroupFrom(JsonNode jsonNode) {
        return null;
    }

    @Lookup
    BasicStudyGroupJson jsonStudyGroup(StudyGroup<?> studyGroup) {
        return null;
    }
    @Lookup
    BasicStudyGroupArrayJson jsonStudyGroupArray(List<? extends StudyGroup<?>> studyGroups) {
        return null;
    }

}
