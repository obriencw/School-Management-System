package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.List;

public class CourseService implements CourseDAO {
    @Override
    //Method to get all available courses from course table in database
    public List<Course> getAllCourses() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();  //Build session factory
        Session session = factory.openSession(); //Open session
        String hql = "FROM Course c";  //SQL query
        TypedQuery<Course> query = session.createQuery(hql,Course.class); //Generate TypedQuery
        List<Course> results = query.getResultList(); //Execute SELECT query and return the query results as a typed list
        session.close();
        factory.close();
        return results;
    }
}
