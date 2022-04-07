package com.SMS.corey_obrien;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {

	private Session session;
	private Transaction transaction;
	private SessionFactory factory;

	@BeforeEach
	void setUp() {
		factory = new Configuration().configure().buildSessionFactory();
		session = factory.openSession();
		transaction = session.beginTransaction();

		// Truncate tables
		session.createSQLQuery("DROP table student_course;").executeUpdate();
		session.createSQLQuery("truncate table student;").executeUpdate();
		session.createSQLQuery("truncate table course;").executeUpdate();

		// Populate course table
		addCourse(session, 1, "English", "Anderea Scamaden");
		addCourse(session, 2, "Mathematics", "Eustace Niemetz");
		addCourse(session, 3, "Anatomy", "Reynolds Pastor");
		addCourse(session, 4, "Organic Chemistry", "Odessa Belcher");
		addCourse(session, 5, "Physics", "Dani Swallow");
		addCourse(session, 6, "Digital Logic", "Glenden Reilingen");
		addCourse(session, 7, "Object Oriented Programming", "Giselle Ardy");
		addCourse(session, 8, "Data Structures", "Carolan Stoller");
		addCourse(session, 9, "Politics", "Carmita De Maine");
		addCourse(session, 10, "Art", "Kingsly Doxsey");

		// Populate student table
		addStudent(session, "hluckham0@google.ru", "Hazel Luckham", "X1uZcoIh0dj");
		addStudent(session, "sbowden1@yellowbook.com", "Sonnnie Bowden", "SJc4aWSU");
		addStudent(session, "qllorens2@howstuffworks.com", "Quillan Llorens", "W6rJuxd");
		addStudent(session, "cstartin3@flickr.com", "Clem Startin", "XYHzJ1S");
		addStudent(session, "tattwool4@biglobe.ne.jp", "Thornie Attwool", "Hjt0SoVmuBz");
		addStudent(session, "hguerre5@deviantart.com", "Harcourt Guerre", "OzcxzD1PGs");
		addStudent(session, "htaffley6@columbia.edu", "Holmes Taffley", "xowtOQ");
		addStudent(session, "aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j");
		addStudent(session, "ljiroudek8@sitemeter.com", "Laryssa Jiroudek", "bXRoLUP");
		addStudent(session, "cjaulme9@bing.com", "Cahra Jaulme", "FnVklVgC6r6");

		transaction.commit();
		session.close();
		factory.close();
	}

	private void addCourse(Session session, int cId, String cName, String cInstructorName) {
		Course course = new Course(cId, cName, cInstructorName);
		session.save(course);
	}

	private void addStudent(Session session, String email, String name, String password) {
		Student student = new Student(email, name, password, new ArrayList<>());
		session.save(student);
	}

	@Test
	public void getStudentByEmail_should_return_correct_student_given_valid_email() {
		Student expectedStudent = new Student(
				"tattwool4@biglobe.ne.jp", "Thornie Attwool",
				"Hjt0SoVmuBz", new ArrayList<>());

		StudentDAO studentService = new StudentService();
		Student actualStudent = studentService.getStudentByEmail(expectedStudent.getsEmail());
		assertEquals(expectedStudent.getsEmail(), actualStudent.getsEmail());

//		Assertions.assertThat(actualStudent).isEqualTo(expectedStudent);
	}

	@Test
	public void getStudentByEmail_should_return_null_given_invalid_email() {
		StudentDAO studentService = new StudentService();
		Student actualStudent = studentService.getStudentByEmail("randomEmail");

		Assertions.assertThat(actualStudent).isNull();
	}

	@Test
	public void validateStudent_should_return_true_given_correct_email_and_password() {
		String email = "tattwool4@biglobe.ne.jp";
		String password = "Hjt0SoVmuBz";

		StudentDAO studentService = new StudentService();
		boolean actualResult = studentService.validateStudent(email, password);

		Assertions.assertThat(actualResult).isTrue();
	}

	@Test
	public void validateStudent_should_return_false_given_correct_email_and_incorrect_password() {
		String email = "tattwool4@biglobe.ne.jp";
		String password = "test";

		StudentDAO studentService = new StudentService();
		boolean actualResult = studentService.validateStudent(email, password);

		Assertions.assertThat(actualResult).isFalse();
	}

	@Test
	public void validateStudent_should_return_false_given_incorrect_email_and_correct_password() {
		String email = "tattwool4@biglobe.ne.j";
		String password = "Hjt0SoVmuBz";

		StudentDAO studentService = new StudentService();
		boolean actualResult = studentService.validateStudent(email, password);

		Assertions.assertThat(actualResult).isFalse();
	}

	@Test
	void registerStudentToCourse_should_register_successfully_given_a_new_course() {

		String email = "tattwool4@biglobe.ne.jp";
		String password = "Hjt0SoVmuBz";
		int courseId = 2;

		StudentDAO studentService = new StudentService();
		int beforeSize = studentService.getStudentCourses(email).size();

		studentService.registerStudentToCourse(email, courseId);

		int afterSize = studentService.getStudentCourses(email).size();
		Assertions.assertThat(afterSize).isEqualTo(beforeSize + 1);
	}

	@Test
	void registerStudentToCourse_should_not_register_given_already_registered_course() {
		String email = "tattwool4@biglobe.ne.jp";
		String password = "Hjt0SoVmuBz";
		int courseId = 3;

		StudentDAO studentService = new StudentService();
		int beforeSize = studentService.getStudentCourses(email).size();

		// this should register successfully
		studentService.registerStudentToCourse(email, courseId);

		int afterSize = studentService.getStudentCourses(email).size();
		Assertions.assertThat(afterSize).isEqualTo(beforeSize+1);

		// this should not register
		studentService.registerStudentToCourse(email, courseId);

		int afterSize2 = studentService.getStudentCourses(email).size();
		Assertions.assertThat(afterSize2).isEqualTo(afterSize);
	}

}