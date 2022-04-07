package jpa.entitymodels;

import javax.persistence.*;
import java.util.List;


    // Create 'student' table in database
@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @Column(name = "email", length = 50, nullable = false)
    private String sEmail;
    @Column(name = "name", length = 50, nullable = false)
    private String sName;
    @Column(name = "password", length = 50, nullable = false)
    private String sPass;
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Course> sCourses;

        // No arg constructor
    public Student() {

    }

        // Constructor initializes each private member associated with Student Class with parameters
    public Student(String sEmail, String sName, String sPass, List<Course> sCourses ) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.sCourses = sCourses;

    }

        //Generated getters and setters for Student Email, Student Name and Student Password and list of Student Courses
    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public List<Course> getsCourses() {
        return sCourses;
    }

    public void setsCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }
}
