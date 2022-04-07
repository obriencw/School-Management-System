package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

    // DAO implemented by CourseService Class
public interface CourseDAO {
    List<Course> getAllCourses();
}
