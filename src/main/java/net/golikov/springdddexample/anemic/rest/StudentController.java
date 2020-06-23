package net.golikov.springdddexample.anemic.rest;

import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import net.golikov.springdddexample.anemic.service.StudentService;
import net.golikov.springdddexample.anemic.service.StudyGroupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StudentController {

    private final StudyGroupService studyGroupService;
    private final StudentService studentService;

    public StudentController(StudyGroupService studyGroupService, StudentService studentService) {
        this.studentService = studentService;
        this.studyGroupService = studyGroupService;
    }

    @GetMapping("/study-groups/{studyGroupId}/students")
    public Iterable<Student> getStudents() {
        return studentService.getAll();
    }

    @GetMapping("/study-groups/{studyGroupId}/students/{studentId}")
    public Student getStudent(@PathVariable long studentId) {
        return studentService.get(studentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        format("Student with id %s was not found", studentId)));
    }

    @PostMapping(value = "/study-groups/{studyGroupId}/students", consumes = APPLICATION_JSON_VALUE)
    public Student saveStudent(@PathVariable long studyGroupId, @RequestBody Student student) throws IOException {
        StudyGroup studyGroup = studyGroupService.get(studyGroupId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        format("Study group with id %s was not found", studyGroupId)));
        return studentService.save(studyGroup, student);
    }


}
