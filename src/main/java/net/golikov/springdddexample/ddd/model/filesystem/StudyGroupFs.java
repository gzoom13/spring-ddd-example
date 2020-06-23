package net.golikov.springdddexample.ddd.model.filesystem;

import net.golikov.springdddexample.ddd.model.StudyGroup;

public interface StudyGroupFs<T extends StudentFs<?>> extends StudyGroup<T>, OnFilesystem {
    interface Factory<T extends StudyGroupFs<?>> {
        T saveStudyGroupFs(StudyGroup<?> studyGroup) throws Exception;
    }
}
