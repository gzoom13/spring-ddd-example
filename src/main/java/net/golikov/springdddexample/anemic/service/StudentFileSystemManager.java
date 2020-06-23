package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class StudentFileSystemManager {

    @Value("${studygroup.filesystem.root}")
    private String storagePath;

    public void save(StudyGroup studyGroup, Student student) throws IOException {
        Files.createDirectories(Paths.get(storagePath)
                .resolve(studyGroup.getName())
                .resolve(student.getName())
        );
    }
}
