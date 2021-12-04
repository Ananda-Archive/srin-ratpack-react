package com.srin.myapp.dao;

import com.db.DBConnection;
import com.srin.myapp.model.Major;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MajorDao {

    Connection connection = null;

    //Init Connection on Constructor
    public MajorDao() {
        connection = DBConnection.getConnection();
    }

    public Promise<List<Major>> findAllMajor() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM major";
            ResultSet res = statement.executeQuery(query);
            List<Major> majors = new ArrayList<>();

            while(res.next()) {
                majors.add(new Major(res.getString("name")));
            } return Blocking.get(() -> majors);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public Major findMajorById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM major WHERE id="+id;
            ResultSet res = statement.executeQuery(query);
            Major major = new Major();

            while(res.next()) {
                major.setId(res.getLong("id"));
                major.setName(res.getString("name"));
            } return major;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

}
