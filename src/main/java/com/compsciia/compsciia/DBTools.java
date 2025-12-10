/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author fernandonunes
 */
public class DBTools {
    public static void addStudent(String name, String birthdate, int group, int entry) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            String sql = "INSERT INTO Students(Name, Birthdate, StudentGroup, YearofEntry) VALUES(?,?,?,?)";
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, birthdate);
            stmt.setInt(3, group);
            stmt.setInt(4, entry);

            stmt.execute();
            cnct.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Add Student (With Image - Polymorphism) [cite: 81]
    public static void addStudent(String name, String birthdate, int group, int entry, File imageFile) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Note: SQL query includes 'Image' column [cite: 83]
            String sql = "INSERT INTO Students(Name, Birthdate, StudentGroup, YearofEntry, Image) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = cnct.prepareStatement(sql);

            // File handling [cite: 84-89]
            Path filePath = imageFile.toPath();
            String fileName = imageFile.getName();
            String target = "src/main/resources/StudentImages/" + fileName;
            Path targetPath = Paths.get(target);

            stmt.setString(1, name);
            stmt.setString(2, birthdate);
            stmt.setInt(3, group);
            stmt.setInt(4, entry);
            stmt.setString(5, fileName); // Storing only filename in DB

            // Copy file to resources [cite: 95]
            Files.copy(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            stmt.execute();
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
}
