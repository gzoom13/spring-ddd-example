package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
import net.golikov.springdddexample.ddd.model.database.postgres.StudentPg;
import net.golikov.springdddexample.ddd.model.database.postgres.StudyGroupPg;
import net.golikov.springdddexample.ddd.model.filesystem.StudyGroupFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicStudentFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicStudyGroupFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudyGroupIndexed implements PersistedStudyGroup<StudentIndexed>, StudyGroupFs<StudentIndexed> {

    private final BasicStudentFs.Factory gradeFsFactory;

    private final StudyGroupPg persistedStudyGroup;
    private final BasicStudyGroupFs studentFs;

    public StudyGroupIndexed(BasicStudentFs.Factory gradeFsFactory,
                             StudyGroupPg persistedStudyGroup,
                             BasicStudyGroupFs studentFs) {
        this.gradeFsFactory = gradeFsFactory;
        this.persistedStudyGroup = persistedStudyGroup;
        this.studentFs = studentFs;
    }

    StudyGroupPg getPersistedStudyGroup() {
        return persistedStudyGroup;
    }

    BasicStudyGroupFs getStudyGroupFs() {
        return studentFs;
    }

    @Override
    public Path getPath() {
        return studentFs.getPath();
    }

    @Override
    public String getName() {
        return persistedStudyGroup.getName();
    }

    @Override
    public List<StudentIndexed> getStudents() {
        return persistedStudyGroup.getStudents().stream()
                .map(studentPg -> {
                    Path gradePath = studentFs.getPath().resolve(studentPg.getName());
                    BasicStudentFs gradeFs = gradeFsFactory.getStudentFs(gradePath);
                    return studentIndexed(studentPg, gradeFs);
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getDatabaseId() {
        return persistedStudyGroup.getDatabaseId();
    }

    @Lookup
    StudentIndexed studentIndexed(StudentPg persistedStudent, BasicStudentFs gradeFs) {
        return null;
    }
}
