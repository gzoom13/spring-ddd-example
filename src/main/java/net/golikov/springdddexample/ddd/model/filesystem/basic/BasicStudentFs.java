package net.golikov.springdddexample.ddd.model.filesystem.basic;

import net.golikov.springdddexample.ddd.model.Grade;
import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.filesystem.StudentFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newDirectoryStream;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicStudentFs implements StudentFs<BasicGradeFs> {

    private final Path path;

    public BasicStudentFs(Path path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public List<BasicGradeFs> getGrades() throws IOException {
        List<BasicGradeFs> result = new ArrayList<>();
        try (DirectoryStream<Path> paths = newDirectoryStream(getPath(), Files::isRegularFile)) {
            for (Path path : paths) {
                result.add(createGradeFs(path));
            }
        }
        return result;
    }

    public Path getPath() {
        return path;
    }

    @Lookup
    public BasicGradeFs createGradeFs(Path path) {
        return null;
    }

    @Component
    public static class Factory implements StudentFs.Factory<BasicStudyGroupFs, BasicStudentFs> {

        private final BasicGradeFs.Factory basicGradeFsFactory;

        public Factory(BasicGradeFs.Factory basicGradeFsFactory) {
            this.basicGradeFsFactory = basicGradeFsFactory;
        }

        @Override
        public BasicStudentFs saveStudentFs(BasicStudyGroupFs studyGroup, Student<?> student) throws Exception {
            Path studentPath = studyGroup.getPath()
                    .resolve(student.getName());
            Files.createDirectories(studentPath);
            BasicStudentFs result = getStudentFs(studentPath);
            for (Grade grade : student.getGrades()) {
                basicGradeFsFactory.saveGradeFs(result, grade);
            }
            return result;
        }

        @Lookup
        public BasicStudentFs getStudentFs(Path path) {
            return null;
        }

    }
}
