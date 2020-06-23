package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.database.StudentRepository;
import net.golikov.springdddexample.anemic.model.Grade;
import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class StudentService {

    private final GradeService gradeService;
    private final StudentRepository studentRepository;
    private final StudentFileSystemManager studyGroupFileSystemManager;

    public StudentService(GradeService gradeService,
                          StudentRepository studentRepository,
                          StudentFileSystemManager studyGroupFileSystemManager) {
        this.gradeService = gradeService;
        this.studentRepository = studentRepository;
        this.studyGroupFileSystemManager = studyGroupFileSystemManager;
    }

    public Optional<Student> get(long studentId) {
        return studentRepository.findById(studentId);
    }

    @Transactional
    public Student save(StudyGroup studyGroup, Student student) throws IOException {
        student.setStudyGroup(studyGroup);
        Student result = studentRepository.save(student);
        studyGroupFileSystemManager.save(studyGroup, student);
        for (Grade grade : student.getGrades()) {
            gradeService.save(studyGroup, student, grade);
        }
        return result;
    }

    public Iterable<Student> getAll() {
        return studentRepository.findAll();
    }
}
