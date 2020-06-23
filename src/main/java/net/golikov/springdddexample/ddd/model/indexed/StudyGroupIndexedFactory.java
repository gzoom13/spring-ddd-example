package net.golikov.springdddexample.ddd.model.indexed;

import net.golikov.springdddexample.ddd.model.StudyGroup;
import net.golikov.springdddexample.ddd.model.database.PersistedStudyGroup;
import net.golikov.springdddexample.ddd.model.database.postgres.StudyGroupPg;
import net.golikov.springdddexample.ddd.model.database.postgres.StudyGroupPgFactory;
import net.golikov.springdddexample.ddd.model.filesystem.StudyGroupFs;
import net.golikov.springdddexample.ddd.model.filesystem.basic.BasicStudyGroupFs;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudyGroupIndexedFactory implements PersistedStudyGroup.Factory<StudyGroupIndexed>, StudyGroupFs.Factory<StudyGroupIndexed> {

    private final StudyGroupPgFactory studyGroupPgFactory;
    private final BasicStudyGroupFs.Factory studyGroupFsFactory;

    public StudyGroupIndexedFactory(StudyGroupPgFactory studyGroupPgFactory, BasicStudyGroupFs.Factory studyGroupFsFactory) {
        this.studyGroupPgFactory = studyGroupPgFactory;
        this.studyGroupFsFactory = studyGroupFsFactory;
    }

    @Override
    @Transactional
    public StudyGroupIndexed persist(StudyGroup<?> studyGroup) throws Exception {
        StudyGroupPg studyGroupPg = studyGroupPgFactory.persist(studyGroup);
        BasicStudyGroupFs studyGroupFs = studyGroupFsFactory.saveStudyGroupFs(studyGroup);
        return createStudyGroupIndexed(studyGroupPg, studyGroupFs);
    }

    public List<StudyGroupIndexed> getAllStudyGroups() throws Exception {
        List<StudyGroupIndexed> result = new ArrayList<>();
        for (StudyGroupPg studyGroupPg : studyGroupPgFactory.getAllStudyGroups()) {
            BasicStudyGroupFs basicStudyGroupFs = studyGroupFsFactory.saveStudyGroupFs(studyGroupPg);
            result.add(createStudyGroupIndexed(studyGroupPg, basicStudyGroupFs));
        }
        return result;
    }

    @Override
    public StudyGroupIndexed saveStudyGroupFs(StudyGroup<?> studyGroup) throws Exception {
        return persist(studyGroup);
    }

    @Lookup
    StudyGroupIndexed createStudyGroupIndexed(StudyGroupPg persistedStudyGroup, BasicStudyGroupFs studyGroupFs) {
        return null;
    }

}
