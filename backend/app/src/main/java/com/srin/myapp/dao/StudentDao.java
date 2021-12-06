package com.srin.myapp.dao;

import com.db.DBConnection;
import com.srin.myapp.dto.StudentDto;
import com.srin.myapp.model.Major;
import com.srin.myapp.model.Student;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class StudentDao {

    Connection connection = null;

    //Init Connection on Constructor
    public StudentDao() {
        connection = DBConnection.getConnection();
    }

    public Promise<List<Student>> findAllStudents() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM student";
            this.printLog(query);
            ResultSet res = statement.executeQuery(query);

            List<Student> students = new ArrayList<>();
            while(res.next()) {
                Student student = new Student();
                student.setId(res.getLong("id"));
                student.setName(res.getString("name"));
                student.setEmail(res.getString("email"));
                student.setSemester(res.getInt("semester"));
                if(res.getObject("major")!=null) {
                    Long majorId = ((Number) res.getObject("major")).longValue();
                    MajorDao majorDao = new MajorDao();
                    student.setMajor(majorDao.findMajorById(majorId));
                }
                students.add(student);
            }
            printLog("Read Data Success!");
            return Blocking.get(()->students);
        } catch (SQLException e) {
            printLog("SQLException: " + e.getMessage());
            return null;
        }
    }

    public Promise<Student> findStudentById(Long id) {
        Student student = new Student();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM student WHERE id="+id;
            this.printLog(query);
            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                student.setId(res.getLong("id"));
                student.setName(res.getString("name"));
                student.setEmail(res.getString("email"));
                student.setSemester(res.getInt("semester"));
                if(res.getObject("major")!=null) {
                    Long majorId = ((Number) res.getObject("major")).longValue();
                    MajorDao majorDao = new MajorDao();
                    student.setMajor(majorDao.findMajorById(majorId));
                }
                if(student.getId()!=null) {
                    printLog("Read Data Success!");
                }
            }
            if(student.getId()==null) {
                printLog("Data not Found!");
            }
            return Blocking.get(()-> student);
        } catch (SQLException e) {
            printLog("SQLException: " + e.getMessage());
        }
        printLog("Read Data Failed!");
        return null;
    }

    public Promise<Long> save(StudentDto student) {
        PreparedStatement statement = null;
        try {
            String query =  "INSERT into student (name, email, semester, major)" +
                            " VALUES ('"+student.getName()+"','"+student.getEmail()+"',"+student.getSemester()+","+student.getMajorId()+")";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            this.printLog(query);
            if(statement.executeUpdate() > 0) {
                printLog("Insert Success!");
            } else {
                printLog("Insert Failed!");
            }
        } catch (SQLException e) {
            printLog("SQLException: " + e.getMessage());
            return null;
        } finally {
            if(statement!=null) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        student.setId(generatedKeys.getLong(1));
                        return Blocking.get(()-> student.getId());
                    }
                } catch (SQLException e) {
                    printLog("(Scope - Finally) SQLException: " + e.getMessage());
                }
            } return null;
        }
    }

    public Promise<Long> update(StudentDto student) {
        try {
            String query =  "UPDATE student" +
                            " SET name='"+student.getName()+"',email='"+student.getEmail()+"',major="+student.getMajorId()+",semester="+student.getSemester() +
                            " WHERE id="+student.getId()+"";
            this.printLog(query);
            PreparedStatement statement = connection.prepareStatement(query);
            int exec = statement.executeUpdate(query);
            if(exec > 0) {
                printLog("Update Success!");
                statement.close();
                return Blocking.get(()-> student.getId());
            } else {
                printLog("Update Failed!");
            }
        } catch (SQLException e) {
            printLog("SQLException: " + e.getMessage());
        } return null;
    }

    public Promise<Long> delete(Long id) {
        try {
            String query =  "DELETE FROM student WHERE id = "+id;
            this.printLog(query);
            PreparedStatement statement = connection.prepareStatement(query);
            int exec = statement.executeUpdate(query);
            if(exec > 0) {
                printLog("Delete Success!");
                statement.close();
                return Blocking.get(()-> id);
            } else {
                printLog("Delete Failed!");
            }
        } catch (SQLException e) {
            printLog("SQLException: " + e.getMessage());
        } return Blocking.get(()-> ((Number)0).longValue());
    }

    public Promise<Long> seedRandomStudent(Long num) {
        Long count = 0L;
        List<String> randomName = Arrays.asList("Owen", "Patrick", "Spike", "Penelope", "Lilianna", "Alfred", "Dexter", "Hailey", "April", "Marcus", "Kimberly", "Audrey", "Lydia", "Dale", "Gianna", "Brad", "Lily", "Tara", "Penelope", "Brianna", "Ryan", "Sydney", "Oliver", "Sabrina", "Aldus", "Sam", "Jared", "Charlotte", "Dainton", "Adrianna", "Adele", "Sienna", "John", "Aida", "Andrew", "Aston", "Patrick", "Martin", "Alfred", "Andrew", "Samantha", "Briony", "Sofia", "Maria", "Richard", "Chester", "Eric", "Eleanor", "Valeria", "Frederick", "Kimberly", "Myra", "Kevin", "Catherine", "Kelvin", "Wilson", "Olivia", "Sarah", "Reid", "William", "Tyler", "Daryl", "Melanie", "Alford", "Jared", "Deanna", "Alan", "Harold", "Edgar", "James", "Byron", "Brad", "Penelope", "Joyce", "Ned", "Clark", "Jacob", "Dexter", "Tara", "Connie", "Jacob", "Florrie", "Dominik", "Aida", "Maria", "Alberta", "Violet", "Catherine", "Oliver", "Cherry", "Alberta", "Albert", "Victoria", "Paige", "Emma", "Kelsey", "Abigail", "Aldus", "Savana", "Bruce", "Heather", "Tiana", "Freddie", "Adam", "Kirsten", "Isabella", "Aiden", "Stella", "Kirsten", "Aldus", "Anna", "Belinda", "William", "George", "Kristian", "Anna", "Julia", "Ned", "Charlie", "Blake", "Rebecca", "Jack", "Daniel", "Adelaide", "Audrey", "Deanna", "Naomi", "Kate", "Amy", "Lydia", "Catherine", "Isabella", "Frederick", "Jasmine", "Harold", "Martin", "Alen", "Julian", "Nicholas", "Adison", "Kristian", "Alberta", "Alen", "Brianna", "Patrick", "Aiden", "Penelope", "Adele", "Valeria", "Catherine", "Oscar", "Alan", "Aiden", "Carl", "Jasmine", "Sarah", "Cadie", "Sienna", "Sydney", "Kirsten", "April", "Victoria", "Mike", "Michelle", "Victoria", "Alan", "Frederick", "Kevin", "Gianna", "Alissa", "Victoria", "Briony", "Paul", "Alen", "Miller", "Camila", "Lucas", "Jacob", "Freddie", "Leonardo", "Lyndon", "Elise", "Jessica", "Lucia", "Eleanor", "Fenton", "Natalie", "Lenny", "Miller", "Fiona", "Antony", "Adele", "Jessica", "Penelope", "Connie", "Paige", "Honey", "Arthur", "Antony", "Kristian");
        for(int i=0; i<num; i++) {
            Random random = new Random();
            StudentDto student = new StudentDto();
            student.setName(randomName.get(random.nextInt(randomName.size())) +" "+ randomName.get(random.nextInt(randomName.size())));
            student.setEmail(student.getName().trim() + "@gmail.com");
            student.setSemester((int) ((Math.random()*(9-1)) + 1));
            student.setMajorId( ((Number) (Math.random()*(8-1)+1)).longValue() );
            try {
                String query =  "INSERT into student (name, email, semester, major)" +
                        " VALUES ('"+student.getName()+"','"+student.getEmail()+"',"+student.getSemester()+","+student.getMajorId()+")";
                this.printLog(query);
                PreparedStatement statement = connection.prepareStatement(query);
                int exec = statement.executeUpdate(query);
                if(exec > 0) {
                    printLog("Insert Success!");
                    count++;
                } else {
                    printLog("Insert Failed!");
                }
            } catch (SQLException e) {
                printLog("SQLException: " + e.getMessage());
            }
        }
        Long finalCount = count;
        return Blocking.get(()-> finalCount);
    }

    public void printLog(String str) {
        System.out.println("["+new Date().toString().substring(4,19)+"]"+" " + str);
//        System.out.println("["+new Date().toString().substring(4,19)+"]"+" This Query Will be Executed: " + str);
    }

}
