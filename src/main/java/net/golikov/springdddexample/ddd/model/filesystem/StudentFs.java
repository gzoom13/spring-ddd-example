package net.golikov.springdddexample.ddd.model.filesystem;

import net.golikov.springdddexample.ddd.model.Student;

public interface StudentFs<T extends GradeFs> extends Student<T>, OnFilesystem {
    interface Factory<P extends StudyGroupFs<?>, T extends StudentFs<?>> {
        T saveStudentFs(P student, Student<?> grade) throws Exception;
    }
}
