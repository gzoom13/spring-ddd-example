package net.golikov.springdddexample.anemic.database;

import net.golikov.springdddexample.anemic.model.Student;
import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
