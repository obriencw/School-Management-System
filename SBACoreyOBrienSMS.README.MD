# School Management System - SBA

## Corey O'Brien


1. Tables created in Student Class and Course Class from entitymodels package

``` 
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
    private List<Course> sCourses;;  
```
```
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
```
2. Tables populated in MySQL Workbench

```
insert into Student (email, name, password) values ('hluckham0@google.ru', 'Hazel Luckham', 'X1uZcoIh0dj');
insert into Student (email, name, password) values ('sbowden1@yellowbook.com', 'Sonnnie Bowden', 'SJc4aWSU');
insert into Student (email, name, password) values ('qllorens2@howstuffworks.com', 'Quillan Llorens', 'W6rJuxd');
insert into Student (email, name, password) values ('cstartin3@flickr.com', 'Clem Startin', 'XYHzJ1S');
insert into Student (email, name, password) values ('tattwool4@biglobe.ne.jp', 'Thornie Attwool', 'Hjt0SoVmuBz');
insert into Student (email, name, password) values ('hguerre5@deviantart.com', 'Harcourt Guerre', 'OzcxzD1PGs');
insert into Student (email, name, password) values ('htaffley6@columbia.edu', 'Holmes Taffley', 'xowtOQ');
insert into Student (email, name, password) values ('aiannitti7@is.gd', 'Alexandra Iannitti', 'TWP4hf5j');
insert into Student (email, name, password) values ('ljiroudek8@sitemeter.com', 'Laryssa Jiroudek', 'bXRoLUP');
insert into Student (email, name, password) values ('cjaulme9@bing.com', 'Cahra Jaulme', 'FnVklVgC6r6');
```
```
insert into Course (id, name, instructor) values (1, 'English', 'Anderea Scamaden');
insert into Course (id, name, instructor) values (2, 'Mathematics', 'Eustace Niemetz');
insert into Course (id, name, instructor) values (3, 'Anatomy', 'Reynolds Pastor');
insert into Course (id, name, instructor) values (4, 'Organic Chemistry', 'Odessa Belcher');
insert into Course (id, name, instructor) values (5, 'Physics', 'Dani Swallow');
insert into Course (id, name, instructor) values (6, 'Digital Logic', 'Glenden Reilingen');
insert into Course (id, name, instructor) values (7, 'Object Oriented Programming', 'Giselle Ardy');
insert into Course (id, name, instructor) values (8, 'Data Structures', 'Carolan Stoller');
insert into Course (id, name, instructor) values (9, 'Politics', 'Carmita De Maine');
insert into Course (id, name, instructor) values (10, 'Art', 'Kingsly Doxsey');
```

3. Test code included in 'Test' folder (path below).
   src/test/java/com/SMS/corey_obrien/StudentServiceTest.java

4. All tests in test code passed.
