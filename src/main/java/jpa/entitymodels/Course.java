package jpa.entitymodels;

import javax.persistence.*;

    // Create 'course' table in database
@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @Column(name = "id", nullable = false)
    private int cId;
    @Column(name = "name", length = 50, nullable = false)
    private String cName;
    @Column(name = "instructor", length = 50, nullable = false)
    private String cInstructorName;

        // No arg constructor
    public Course() {

    }
        // Constructor initializes each private member associated with Course Class with parameters

    public Course(int courseId, String courseName, String courseInstructor) {
        this.cId = courseId;
        this.cName = courseName;
        this.cInstructorName = courseInstructor;
    }


        //Generated getters and setters for Course ID, Course Name and Instructor Name
        public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }
}
