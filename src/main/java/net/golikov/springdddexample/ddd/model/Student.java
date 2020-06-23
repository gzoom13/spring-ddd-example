package net.golikov.springdddexample.ddd.model;

import java.util.List;

public interface Student<T extends Grade> {

    String getName() throws Exception;

    List<T> getGrades() throws Exception;

}
