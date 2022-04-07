package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import javax.security.auth.login.AppConfigurationEntry;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {
    @Override
    // This method displays all students in the student table from the database
    public List<Student> getAllStudents() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM Student s";
        TypedQuery<Student> query = session.createQuery(hql, Student.class);
        List<Student> results = query.getResultList();
        session.close();
        factory.close();
        return results;
    }

    @Override
    // This method takes the user's email and checks to see if it is in the student table in the database
    // If the user's email is not found in the database, print "Email not found"
    public Student getStudentByEmail(String sEmail) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            String myquery = "FROM Student s WHERE email = :email";
            TypedQuery<Student> query = session.createQuery(myquery, Student.class);
            query.setParameter("email", sEmail);
            Student student = new Student();
            student = (Student) query.getSingleResult();
            session.close();
            factory.close();
            return student;
        } catch (NoResultException e) {
            System.out.println("Email not found");
            return null;
        }
    }

    @Override
        // This method takes the users email and password as input
        // Compare user input to info in Student table of database
        // If email or password do not match database, print 'Error'
    public boolean validateStudent(String sEmail, String sPass) {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            TypedQuery<Student> actualEmail = session.createQuery("FROM Student s WHERE s.sEmail = :sEmail");
            actualEmail.setParameter("sEmail", sEmail);
            Student actualEmailResult = actualEmail.getSingleResult();
            TypedQuery<Student> actualPassword = session.createQuery("FROM Student s WHERE s.sPass = :sPass");
            actualPassword.setParameter("sPass", sPass);
            Student actualPasswordResult = actualPassword.getSingleResult();
            if (actualEmailResult.getsEmail().equals(sEmail) && actualPasswordResult.getsPass().equals(sPass)) {
                return true;
            }
            session.close();
            factory.close();
        } catch (NoResultException e) {
            System.out.println("Error");
            return false;
        }
        return false;

    }

    @Override
        // This method adds a course to the student_course table in database
        // If the email associated with the student has already registered for a course with the same course ID,
        // print "Already registered for course"
    public void registerStudentToCourse(String sEmail, int cId) {

        Student student = getStudentByEmail(sEmail);
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        boolean inCourse = false;
        for (Course c : student.getsCourses()) {
            if (c.getcId() == cId) {
                inCourse = true;
                System.out.println("Already registered for course");
                break;
            }
        }
        if (!inCourse) {

            CourseService courseService = new CourseService();
            List<Course> courses1 = courseService.getAllCourses();
            for (Course c : courses1) {
                if (c.getcId() == cId) {
                    student.getsCourses().add(c);
                    Session session = factory.openSession();
                    Transaction t = session.beginTransaction();
                    session.merge(student);
                    t.commit();
                    session.close();
                    factory.close();
                }
            }
        }
    }

    @Override
        // This method displays all the courses that the student with associated email has registered for
    public List<Course> getStudentCourses(String sEmail) {
        return getStudentByEmail(sEmail).getsCourses();
    }

    public boolean addStudent(Student student) {
        boolean result = true;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }finally {
            em.close();
            emf.close();
        }
        return result;
    }
}
