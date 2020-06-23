package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.database.PersistedGrade;
import net.golikov.springdddexample.ddd.model.database.postgres.GradePg;
import net.golikov.springdddexample.ddd.model.database.postgres.GradePgFactory;
import net.golikov.springdddexample.ddd.model.filesystem.GradeFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicGradeFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GradeIndexedFactory implements PersistedGrade.Factory<StudentIndexed, GradeIndexed>, GradeFs.Factory<StudentIndexed, GradeIndexed> {

    private final GradePgFactory gradePgFactory;
    private final BasicGradeFs.Factory gradeFsFactory;

    public GradeIndexedFactory(GradePgFactory gradePgFactory,
                               BasicGradeFs.Factory gradeFsFactory) {
        this.gradePgFactory = gradePgFactory;
        this.gradeFsFactory = gradeFsFactory;
    }

    @Override
    @Transactional
    public GradeIndexed persist(StudentIndexed student, Grade grade) throws Exception {
        GradePg gradePg = gradePgFactory.persist(student.getPersistedStudent(), grade);
        BasicGradeFs gradeFs = gradeFsFactory.saveGradeFs(student.getStudentFs(), grade);
        return createGradeIndexed(gradePg, gradeFs);
    }

    @Override
    public GradeIndexed saveGradeFs(StudentIndexed student, Grade grade) throws Exception {
        return persist(student, grade);
    }

    @Lookup
    GradeIndexed createGradeIndexed(GradePg persistedGrade, BasicGradeFs gradeFs) {
        return null;
    }
}
