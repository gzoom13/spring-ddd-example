package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.database.PersistedStudent;
import net.golikov.springdddexample.ddd.model.database.postgres.GradePg;
import net.golikov.springdddexample.ddd.model.database.postgres.StudentPg;
import net.golikov.springdddexample.ddd.model.filesystem.StudentFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicGradeFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicStudentFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class StudentIndexed implements PersistedStudent<GradeIndexed>, StudentFs<GradeIndexed> {

    private final BasicGradeFs.Factory gradeFsFactory;

    private final StudentPg persistedStudent;
    private final BasicStudentFs studentFs;

    public StudentIndexed(BasicGradeFs.Factory gradeFsFactory,
                          StudentPg persistedStudent,
                          BasicStudentFs studentFs) {
        this.gradeFsFactory = gradeFsFactory;
        this.persistedStudent = persistedStudent;
        this.studentFs = studentFs;
    }

    StudentPg getPersistedStudent() {
        return persistedStudent;
    }

    BasicStudentFs getStudentFs() {
        return studentFs;
    }

    @Override
    public Path getPath() {
        return studentFs.getPath();
    }

    @Override
    public String getName() {
        return persistedStudent.getName();
    }

    @Override
    public List<GradeIndexed> getGrades() {
        return persistedStudent.getGrades().stream()
                .map(gradePg -> {
                    Path gradePath = studentFs.getPath().resolve(gradePg.getCourse());
                    BasicGradeFs gradeFs = gradeFsFactory.getGradeFs(gradePath);
                    return gradeIndexed(gradePg, gradeFs);
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getDatabaseId() {
        return persistedStudent.getDatabaseId();
    }

    @Lookup
    GradeIndexed gradeIndexed(GradePg persistedGrade, BasicGradeFs gradeFs) {
        return null;
    }
}
