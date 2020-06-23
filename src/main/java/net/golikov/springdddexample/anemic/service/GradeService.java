package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.database.GradeRepository;
import net.golikov.springdddexample.anemic.model.Grade;
import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class GradeService {

    private final GradeRepository gradeRepository;
    private final GradeFileSystemManager gradeFileSystemManager;

    public GradeService(GradeRepository gradeRepository, GradeFileSystemManager gradeFileSystemManager) {
        this.gradeRepository = gradeRepository;
        this.gradeFileSystemManager = gradeFileSystemManager;
    }

    public Optional<Grade> get(long studyGroupId) {
        return gradeRepository.findById(studyGroupId);
    }

    @Transactional
    public Grade save(StudyGroup group, Student student, Grade grade) throws IOException {
        Grade result = gradeRepository.save(grade);
        gradeFileSystemManager.save(group, student, grade);
        return result;
    }

    public Iterable<Grade> getAll() {
        return gradeRepository.findAll();
    }
}
