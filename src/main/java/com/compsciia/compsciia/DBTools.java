/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author fernandonunes
 */
public class DBTools {
    public static void addStudent(Student student) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            String sql = "INSERT INTO Students(Name, Birthdate, StudentGroup, YearofEntry, Address, PrevSchool) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getBirthdate().toString());
            stmt.setInt(3, student.getGroup());
            stmt.setInt(4, student.getEntry());
            stmt.setString(5, student.getAddress().toString());
            stmt.setString(6, student.getPrevSchool());

            stmt.execute();
            cnct.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Add Student (With Image - Polymorphism) [cite: 81]
    public static void addStudent(Student student, File imageFile) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Note: SQL query includes 'Image' column [cite: 83]
            String sql = "INSERT INTO Students(Name, Birthdate, StudentGroup, YearofEntry, Address, PrevSchool) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getBirthdate().toString());
            stmt.setInt(3, student.getGroup());
            stmt.setInt(4, student.getEntry());
            stmt.setString(5, student.getAddress().toString());
            stmt.setString(6, student.getPrevSchool());
            

            stmt.execute();
            // File handling [cite: 84-89]
            Path filePath = imageFile.toPath();
            String pathStr = filePath.toString();
            String extension = pathStr.substring(pathStr.indexOf("."));
            String fileName = "pfp_"+getLastID();
            String target = "src/main/resources/student_images/" + fileName;
            updateStudent(getLastID(),"Image",target);
            
            Path targetPath = Paths.get(target);
            // Copy file to resources [cite: 95]
            Files.copy(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            cnct.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update Student [cite: 180]
    public static void updateStudent(int id, String field, String newValue) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "UPDATE Students SET " + field + " = ? WHERE StudentID = ?"; 
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, newValue);
            stmt.setInt(2, id);

            stmt.execute();
            cnct.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Student searchStudent(int id){
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Students WHERE StudentID = ?"; 
            PreparedStatement stmt = cnct.prepareStatement(sql);
            
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery(sql);
            
            Student student = new Student();
            while(results.next()){
                student.setName(results.getString("Name"));
                Address address = new Address(results.getString("Address"));
                student.setAddress(address);
                student.setBirthdate(results.getString("Birthdate"));
                student.setGroup(results.getInt("StudentGroup"));
                student.setEntry(results.getInt("YearofEntry"));
            }
            return student;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public static int getLastID(){
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Students ORDER BY StudentID DESC LIMIT 1;"; 
            PreparedStatement stmt = cnct.prepareStatement(sql);
            ResultSet results = stmt.executeQuery(sql);
            
            while(results.next()){
                return results.getInt("StudentID");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }
}