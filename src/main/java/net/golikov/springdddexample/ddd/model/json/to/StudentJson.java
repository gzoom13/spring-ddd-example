package net.golikov.springdddexample.ddd.model.json.to;

import net.golikov.springdddexample.ddd.model.Student;

public interface StudentJson<T extends GradeJson> extends Json, Student<T> {
}
