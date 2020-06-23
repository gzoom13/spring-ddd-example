package net.golikov.springdddexample.anemic.rest;

import net.golikov.springdddexample.anemic.model.StudyGroup;
import net.golikov.springdddexample.anemic.service.StudyGroupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    @GetMapping("/study-groups")
    public Iterable<StudyGroup> getStudyGroups() {
        return studyGroupService.getAll();
    }

    @GetMapping("/study-groups/{studyGroupId}")
    public StudyGroup getStudyGroup(@PathVariable long studyGroupId) {
        return studyGroupService.get(studyGroupId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        format("Study group with id %s was not found", studyGroupId)));
    }

    @PostMapping(value = "/study-groups", consumes = APPLICATION_JSON_VALUE)
    public StudyGroup saveStudyGroup(@RequestBody StudyGroup studyGroup) throws IOException {
        return studyGroupService.save(studyGroup);
    }


}
