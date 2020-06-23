package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.database.PersistedGrade;
import net.golikov.springdddexample.ddd.model.database.postgres.GradePg;
import net.golikov.springdddexample.ddd.model.filesystem.GradeFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicGradeFs;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class GradeIndexed implements PersistedGrade, GradeFs {

    private final GradePg persistedGrade;
    private final BasicGradeFs gradeFs;

    public GradeIndexed(GradePg persistedGrade, BasicGradeFs gradeFs) {
        this.persistedGrade = persistedGrade;
        this.gradeFs = gradeFs;
    }

    @Override
    public String getCourse() {
        return persistedGrade.getCourse();
    }

    @Override
    public String getGrade() {
        return persistedGrade.getGrade();
    }

    @Override
    public Path getPath() {
        return gradeFs.getPath();
    }

    @Override
    public long getDatabaseId() {
        return persistedGrade.getDatabaseId();
    }
}
