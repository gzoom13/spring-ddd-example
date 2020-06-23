package net.golikov.springdddexample.anemic.database;

import net.golikov.springdddexample.anemic.model.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Long> {
}
