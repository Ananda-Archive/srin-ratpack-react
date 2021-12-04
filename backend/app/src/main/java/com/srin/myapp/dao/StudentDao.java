package com.srin.myapp.dao;

import com.db.DBConnection;
import com.srin.myapp.dto.StudentDto;
import com.srin.myapp.model.Major;
import com.srin.myapp.model.Student;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            String query =  "INSERT into student (name, email, major)" +
                            " VALUES ('"+student.getName()+"','"+student.getEmail()+"',"+student.getMajorId()+")";
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
                            " SET name='"+student.getName()+"',email='"+student.getEmail()+"',major="+student.getMajorId() +
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

    public void printLog(String str) {
        System.out.println("["+new Date().toString().substring(4,19)+"]"+" " + str);
//        System.out.println("["+new Date().toString().substring(4,19)+"]"+" This Query Will be Executed: " + str);
    }

}
