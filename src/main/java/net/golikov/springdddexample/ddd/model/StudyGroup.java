package net.golikov.springdddexample.ddd.model;

import java.util.List;

public interface StudyGroup<T extends Student<?>> {

    String getName() throws Exception;

    List<T> getStudents() throws Exception;

}
