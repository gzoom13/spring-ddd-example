package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.database.StudyGroupRepository;
import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class StudyGroupService {

    private final StudentService studentService;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupFileSystemManager studyGroupFileSystemManager;

    public StudyGroupService(StudentService studentService,
                             StudyGroupRepository studyGroupRepository,
                             StudyGroupFileSystemManager studyGroupFileSystemManager) {
        this.studentService = studentService;
        this.studyGroupRepository = studyGroupRepository;
        this.studyGroupFileSystemManager = studyGroupFileSystemManager;
    }

    public Optional<StudyGroup> get(long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId);
    }

    public Iterable<StudyGroup> getAll() {
        return studyGroupRepository.findAll();
    }

    @Transactional
    public StudyGroup save(StudyGroup studyGroup) throws IOException {
        studyGroup.getStudents().forEach(student -> student.setStudyGroup(studyGroup));
        StudyGroup result = studyGroupRepository.save(studyGroup);
        studyGroupFileSystemManager.save(studyGroup);
        for (Student student : studyGroup.getStudents()) {
            studentService.save(studyGroup, student);
        }
        studyGroupFileSystemManager.save(studyGroup);
        return result;
    }
}
