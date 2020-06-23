package net.golikov.springdddexample.ddd.model.filesystem;

import net.golikov.springdddexample.ddd.model.Grade;

public interface GradeFs extends Grade, OnFilesystem {
    interface Factory<P extends StudentFs<?>, T extends GradeFs> {
        T saveGradeFs(P student, Grade grade) throws Exception;
    }
}
