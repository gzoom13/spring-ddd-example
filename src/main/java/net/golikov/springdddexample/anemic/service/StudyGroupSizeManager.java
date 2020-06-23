package net.golikov.springdddexample.anemic.service;

import net.golikov.springdddexample.anemic.model.StudyGroup;
import org.springframework.stereotype.Component;

@Component
public class StudyGroupSizeManager {

    public void saveSize(StudyGroup studyGroup) {
        studyGroup.setTooBig(studyGroup.getStudents().size() >= 30);
//        someMapper.updateTooBig(studyGroup);
    }

    public void getSize(StudyGroup studyGroup) {
//        studyGroup.setTooBig(someMapper.getTooBig(studyGroup));
    }
}
