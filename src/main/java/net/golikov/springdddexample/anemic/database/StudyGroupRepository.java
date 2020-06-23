package net.golikov.springdddexample.anemic.database;

import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.data.repository.CrudRepository;

public interface StudyGroupRepository extends CrudRepository<StudyGroup, Long> {
}
