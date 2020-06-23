package net.golikov.springdddexample.anemic.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudyGroup {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="STUDY_GROUP_ID")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
