package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.database.PersistedStudent;
import net.golikov.springdddexample.ddd.model.database.postgres.StudentPg;
import net.golikov.springdddexample.ddd.model.database.postgres.StudentPgFactory;
import net.golikov.springdddexample.ddd.model.filesystem.StudentFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicStudentFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentIndexedFactory implements PersistedStudent.Factory<StudyGroupIndexed, StudentIndexed>, StudentFs.Factory<StudyGroupIndexed, StudentIndexed> {

    private final StudentPgFactory studentPgFactory;
    private final BasicStudentFs.Factory studentFsFactory;

    public StudentIndexedFactory(StudentPgFactory studentPgFactory, BasicStudentFs.Factory studentFsFactory) {
        this.studentPgFactory = studentPgFactory;
        this.studentFsFactory = studentFsFactory;
    }

    @Override
    @Transactional
    public StudentIndexed persist(StudyGroupIndexed studyGroup, Student<?> student) throws Exception {
        StudentPg studentPg = studentPgFactory.persist(studyGroup.getPersistedStudyGroup(), student);
        BasicStudentFs studentFs = studentFsFactory.saveStudentFs(studyGroup.getStudyGroupFs(), student);
        return createStudentIndexed(studentPg, studentFs);
    }

    @Override
    public StudentIndexed saveStudentFs(StudyGroupIndexed studyGroup, Student<?> student) throws Exception {
        return persist(studyGroup, student);
    }

    @Lookup
    StudentIndexed createStudentIndexed(StudentPg persistedStudent, BasicStudentFs studentFs) {
        return null;
    }

}
