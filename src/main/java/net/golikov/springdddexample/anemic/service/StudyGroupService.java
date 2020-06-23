package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.database.StudyGroupRepository;
import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class StudyGroupService {

    private final StudentService studentService;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupFileSystemManager studyGroupFileSystemManager;
    private final StudyGroupSizeManager studyGroupSizeManager;

    public StudyGroupService(StudentService studentService,
                             StudyGroupRepository studyGroupRepository,
                             StudyGroupFileSystemManager studyGroupFileSystemManager,
                             StudyGroupSizeManager studyGroupSizeManager) {
        this.studentService = studentService;
        this.studyGroupRepository = studyGroupRepository;
        this.studyGroupFileSystemManager = studyGroupFileSystemManager;
        this.studyGroupSizeManager = studyGroupSizeManager;
    }

    public Optional<StudyGroup> get(long studyGroupId) {
        Optional<StudyGroup> result = studyGroupRepository.findById(studyGroupId);
        result.ifPresent(studyGroupSizeManager::getSize);
        return result;
    }

    public Stream<StudyGroup> getAll() {
        return StreamSupport.stream(studyGroupRepository.findAll().spliterator(), false)
                .peek(studyGroupSizeManager::getSize);
    }

    @Transactional
    public StudyGroup save(StudyGroup studyGroup) throws IOException {
        studyGroup.getStudents().forEach(student -> student.setStudyGroup(studyGroup));
        StudyGroup result = studyGroupRepository.save(studyGroup);
        studyGroupFileSystemManager.save(studyGroup);
        studyGroupSizeManager.saveSize(result);
        for (Student student : studyGroup.getStudents()) {
            studentService.save(studyGroup, student);
        }
        studyGroupFileSystemManager.save(studyGroup);
        return result;
    }
}
