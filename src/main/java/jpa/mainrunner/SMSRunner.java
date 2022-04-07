package jpa.mainrunner;

import java.util.List;
import java.util.Scanner;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

public class SMSRunner {
    static Scanner scanInt = new Scanner(System.in);
    static Scanner scanStr = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }
    // This method creates the menu and lets the user choose an option
    // Choosing 'Student' takes the user to the student menu
    // Choosing 'quit' exits the application
    static void menu() {
        boolean on = true;

        while (on) {
            System.out.println("\nStudent Management Service\n");
            System.out.println("Are you a(n)\n"
                    + "1. Student\n"
                    + "2. quit");
            while (!scanInt.hasNextInt()){
                System.out.println("Please enter a valid option!");
                scanInt.nextLine();
            }
            int response = scanInt.nextInt();
            switch (response) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    System.out.println("Goodbye");
                    on = false;
                    break;
                default:
                    System.out.println("Please enter valid response");
            }
        }
    }
    // This method creates the student menu and lets the user choose an option
    // Choosing 'Login' requires the user to validate their login information via the 'Validate' method
    // Choosing 'Return' returns the user to the original menu
    static void studentMenu() {
        boolean on = true;

        while (on) {
            System.out.println("\nStudent Menu\n");
            System.out.println("Register or Login?\n"
                    + "1. Login\n"
                    + "2. Return\n");
            while (!scanInt.hasNextInt()){
                System.out.println("Invalid option. Please choose '1' to login or '2' to return to menu\n");
                scanInt.nextLine();
            }
            int response = scanInt.nextInt();
            switch (response) {
                case 1:
                    validate(); // Call validate method
                    break;
                case 2:
                    on = false;
                    break;
                default:
                    System.out.println("invalid response");
            }
        }
    }
    // This method checks the user's email and password are valid and compares to user information in Student table in database
    private static void validate() {

        System.out.println("Enter your email\n");
        String email = scanStr.nextLine();
        System.out.println("Enter your password\n");
        String password = scanStr.nextLine();

        StudentService ss = new StudentService();
        boolean valid = ss.validateStudent(email, password);  // Compare user email and password to database using validateStudent method from StudentService Class

        if (valid) {
            showClasses(email);
            registerMenu(email);
        }else {
            System.out.println("Email and/or Password error");
        }

    }
    // This method displays all classes the user is currently enrolled in
    static void showClasses(String email) {
        StudentDAO ss = new StudentService();
        System.out.println("My Courses:\n");
        System.out.printf("Course ID " + " Course Name %36s %n","Instructor Name");
        for (Course c :ss.getStudentCourses(email)) {
            System.out.printf(c.getcId() + "%-20s %27s %n",c.getcName(),c.getcInstructorName());
        }
    }
    // This method allows the user to choose if they want to register for a class or logout
    // Choosing 'Register' calls the registerClass method
    static void registerMenu(String email) {
        boolean on = true;

        while (on) {
            System.out.println("\nRegister Menu\n");
            System.out.println("Register for Class?\n"
                    + "1. Register\n"
                    + "2. Logout\n");
            while (!scanInt.hasNextInt()){
                System.out.println("Invalid option. Please choose '1' to register or '2' to logout.\n");
                scanInt.nextLine();
            }
            int response = scanInt.nextInt();
            switch (response) {
                case 1:
                    registerClass(email);
                    break;
                case 2:
                    System.out.println("You have logged out");
                    on = false;
                    break;
                default:
                    System.out.println("invalid response");
            }
        }
    }
    // This method displays all courses available for registration
    // If the user chooses a valid course ID, the registerStudentToCourse method from StudentService Class is called
    static void registerClass(String email) {
        StudentService ss = new StudentService();
        CourseService cs = new CourseService();

        boolean on = true;

        while (on) {
            System.out.println("All Courses:\n\n# Course Name Instructor");
            int count = 0;
            for (Course c:cs.getAllCourses()) {
                System.out.printf("%s %-30s%10s",c.getcId(),c.getcName(),"");
                System.out.printf("%5s %n",c.getcInstructorName());
                count++;
            }
            System.out.println("\nSelect class to register for!\n\nEnter 0 to exit class registration");
            while (!scanInt.hasNextInt()){
                System.out.println("Please enter a valid option!\n");
                scanInt.nextLine();
            }
            int response = scanInt.nextInt();
            if (response==0) {
                on = false;
            }else if (response > count) {
                System.out.println("Invalid course. Please choose from available courses");
                break;
            }
            StudentDAO studentService = new StudentService();
            studentService.registerStudentToCourse(email, response);
            List<Course> courses = studentService.getStudentByEmail(email).getsCourses();
            System.out.println("\n\nUpdated Course List: ");
            System.out.printf("%-20s %-30s %-40s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
            for(Course c : courses)    {
                System.out.printf("%-20s %-30s %-40s\n", c.getcId(), c.getcName(), c.getcInstructorName());

            }
            System.out.println("Goodbye");
            break;
        }
    }
}