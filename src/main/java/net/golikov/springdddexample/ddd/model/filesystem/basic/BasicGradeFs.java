package net.golikov.springdddexample.ddd.model.filesystem.basic;

import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.filesystem.GradeFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicGradeFs implements GradeFs {

    private final Path path;

    public BasicGradeFs(Path path) {
        this.path = path;
    }

    @Override
    public String getCourse() {
        return path.getFileName().toString();
    }

    @Override
    public String getGrade() throws IOException {
        return Files.readAllLines(path).get(0);
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Component
    public static class Factory implements GradeFs.Factory<BasicStudentFs, BasicGradeFs> {

        @Override
        public BasicGradeFs saveGradeFs(BasicStudentFs student, Grade grade) throws Exception {
            Path gradePath = student.getPath()
                    .resolve(grade.getCourse());
            Files.write(gradePath, grade.getGrade().getBytes());
            return getGradeFs(gradePath);
        }

        @Lookup
        public BasicGradeFs getGradeFs(Path path) {
            return null;
        }

    }
}
