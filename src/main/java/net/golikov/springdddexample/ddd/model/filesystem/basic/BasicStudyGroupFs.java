package net.golikov.springdddexample.ddd.model.filesystem.basic;

import net.golikov.springdddexample.ddd.model.Student;
import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.filesystem.StudyGroupFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newDirectoryStream;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class BasicStudyGroupFs implements StudyGroupFs<BasicStudentFs> {

    private final Path path;

    public BasicStudyGroupFs(Path path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public List<BasicStudentFs> getStudents() throws IOException {
        List<BasicStudentFs> result = new ArrayList<>();
        try (DirectoryStream<Path> paths = newDirectoryStream(getPath(), Files::isDirectory)) {
            for (Path path : paths) {
                result.add(createStudentFs(path));
            }
        }
        return result;
    }

    public Path getPath() {
        return path;
    }

    @Lookup
    public BasicStudentFs createStudentFs(Path path) {
        return null;
    }

    @Component
    public static class Factory implements StudyGroupFs.Factory<BasicStudyGroupFs> {

        @Value("${studygroup.filesystem.root}")
        private String storagePath;

        private final BasicStudentFs.Factory studentFsFactory;

        public Factory(BasicStudentFs.Factory studentFsFactory) {
            this.studentFsFactory = studentFsFactory;
        }

        @Override
        public BasicStudyGroupFs saveStudyGroupFs(StudyGroup<?> studyGroup) throws Exception {
            Path studyGroupPath = studyGroupPath(studyGroup);
            Files.createDirectories(studyGroupPath);
            BasicStudyGroupFs result = studyGroupFs(studyGroupPath);
            for (Student<?> student : studyGroup.getStudents()) {
                studentFsFactory.saveStudentFs(result, student);
            }
            return result;
        }

        public BasicStudyGroupFs getStudyGroupFs(StudyGroup<?> studyGroup) throws Exception {
            Path studyGroupPath = studyGroupPath(studyGroup);
            return studyGroupFs(studyGroupPath);
        }

        private Path studyGroupPath(StudyGroup<?> studyGroup) throws Exception {
            return Paths.get(storagePath)
                    .resolve(studyGroup.getName());
        }

        @Lookup
        BasicStudyGroupFs studyGroupFs(Path path) {
            return null;
        }

    }
}
