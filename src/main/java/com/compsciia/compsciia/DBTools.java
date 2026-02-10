/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fernandonunes
 */
public class DBTools {

    public static void addStudent(Student student, File imageFile) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            String sql = "INSERT INTO Students(Name, Birthdate, StudentGroup, YearofEntry, Address, PrevSchool) VALUES(?,?,?,?,?,?)";
            //Using PreparedStatement to avoid SQL Injection
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getBirthdate().toString());
            stmt.setInt(3, student.getGroup());
            stmt.setInt(4, student.getEntry());
            stmt.setString(5, student.getAddress().toString());
            stmt.setString(6, student.getPrevSchool());
            stmt.execute();

            // File handling 
            // 1. Get the User's Home Directory (Works on Windows, Mac, Linux)
            String userHome = System.getProperty("user.home");

            // 2. Define a dedicated folder for your app images
            File appStorageDir = new File(userHome, "ScholarshipApp_Images");

            // 3. Create the directory if it doesn't exist
            if (!appStorageDir.exists()) {
                appStorageDir.mkdirs();
            }

            // 4. Prepare the new file path
            String fileName = "pfp_" + getLastID() + ".png"; // Force PNG for simplicity or keep dynamic extension
            File targetFile = new File(appStorageDir, fileName);

            // 5. Copy the file to this external folder
            Path sourcePath = imageFile.toPath();
            Path targetPath = targetFile.toPath();
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 6. Save the ABSOLUTE path to the database
            String savedPath = targetFile.getAbsolutePath();
            updateStudent(getLastID(), "Image", savedPath);

            //Method from this class to get the latest Student ID and add it to the Student object
            student.setId(getLastID());
            cnct.close();
        } catch (IOException | SQLException e) {

        }
    }

    // Update Student
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

    public static void updateStudent(int id, Student student, File imageFile) {
        try {
            updateStudent(id, "Name", student.getName());
            updateStudent(id, "Birthdate", student.getBirthdate().toString());
            updateStudent(id, "Address", student.getAddress().toString());
            updateStudent(id, "StudentGroup", "" + student.getGroup());
            updateStudent(id, "PrevSchool", student.getPrevSchool());
            updateStudent(id, "YearofEntry", "" + student.getEntry());

            String userHome = System.getProperty("user.home");
            File appStorageDir = new File(userHome, "ScholarshipApp_Images");
            if (!appStorageDir.exists()) {
                appStorageDir.mkdirs();
            }

            String fileName = "pfp_" + id + ".png";
            File targetFile = new File(appStorageDir, fileName);

            Path sourcePath = imageFile.toPath();
            Path targetPath = targetFile.toPath();

            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Update database with the new absolute path
            updateStudent(id, "Image", targetFile.getAbsolutePath());

        } catch (IOException ex) {
            System.getLogger(DBTools.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static void deleteStudent(int id) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "DELETE FROM Students WHERE StudentID = " + id;
            PreparedStatement stmt = cnct.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex) {
            System.getLogger(DBTools.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static Student searchStudent(int id) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Students WHERE StudentID = " + id;
            PreparedStatement stmt = cnct.prepareStatement(sql);

            ResultSet results = stmt.executeQuery(sql);

            Student student = new Student();
            while (results.next()) {
                student.setName(results.getString("Name"));
                Address address = new Address(results.getString("Address"));
                student.setAddress(address);
                student.setBirthdate(results.getString("Birthdate"));
                student.setGroup(results.getInt("StudentGroup"));
                student.setEntry(results.getInt("YearofEntry"));
                student.setId(id);
                student.setPrevSchool(results.getString("PrevSchool"));
                student.setImagePath(results.getString("Image"));
            }
            cnct.close();
            return student;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static int getLastID() {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Students ORDER BY StudentID DESC LIMIT 1;";
            PreparedStatement stmt = cnct.prepareStatement(sql);
            ResultSet results = stmt.executeQuery(sql);

            while (results.next()) {
                return results.getInt("StudentID");
            }
            return -1;
        } catch (SQLException ex) {
            return -1;
        }
    }

    public static boolean findUser(String username) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Users WHERE Username='" + username + "';";
            PreparedStatement stmt = cnct.prepareStatement(sql);
            ResultSet results = stmt.executeQuery(sql);
            return results.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean findUser(String username, String password) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            // Dynamic SQL construction [cite: 186]
            String sql = "SELECT * FROM Users WHERE Username='" + username + "' and Password= '" + password + "';";
            PreparedStatement stmt = cnct.prepareStatement(sql);
            ResultSet results = stmt.executeQuery(sql);
            return results.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void addUser(String username, String password) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            String sql = "INSERT INTO Users(Username, Password) VALUES(?,?)";
            PreparedStatement stmt = cnct.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, hash(password));

            stmt.execute();

            cnct.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String password) {
        try {
            // Create a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hashing on the password bytes
            byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));

            // Convert the bytes into a readable String (Base64)
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static void dataAnalysis(String field, Map<String, Integer> map) {
        try {
            Connection cnct = ConnectionFactory.getConnection();
            switch (field) {
                case "Age": {
                    field = "Birthdate";

                    String sql = "SELECT COUNT(StudentID)," + field + "  FROM Students GROUP BY " + field + ";";
                    PreparedStatement stmt = cnct.prepareStatement(sql);

                    ResultSet results = stmt.executeQuery(sql);
                    int[] allAges = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    while (results.next()) {
//                System.out.println(results.getString(field)+results.getInt("COUNT(StudentID)"));
                        LocalDate bDate = LocalDate.parse(results.getString(field));
                        int currentAge = Period.between(bDate, LocalDate.now()).getYears();
                        allAges[currentAge]++;
                        map.put(String.valueOf(currentAge), allAges[currentAge]);
                    }
                    cnct.close();
                    break;
                }
                case "Address": {
                    String sql = "SELECT COUNT(StudentID)," + field + "  FROM Students GROUP BY " + field + ";";
                    PreparedStatement stmt = cnct.prepareStatement(sql);

                    ResultSet results = stmt.executeQuery(sql);
                    while (results.next()) {
                        Address address = new Address(results.getString("Address"));
                        String neighborhood = address.getNeighborhood();
                        if (!address.getCity().equals("São Paulo")) {
                            neighborhood += "*";
                        }
                        if (map.containsKey(neighborhood)) {
                            int curValue = map.get(neighborhood);
                            map.put(neighborhood, curValue += results.getInt("COUNT(StudentID)"));
                        } else {
                            map.put(neighborhood, results.getInt("COUNT(StudentID)"));
                        }
                    }
                    cnct.close();
                    break;
                }
                default:
                    String sql = "SELECT COUNT(StudentID)," + field + "  FROM Students GROUP BY " + field + ";";
                    PreparedStatement stmt = cnct.prepareStatement(sql);

                    ResultSet results = stmt.executeQuery(sql);

                    while (results.next()) {
//                System.out.println(results.getString(field)+results.getInt("COUNT(StudentID)"));
                        map.put(results.getString(field), results.getInt("COUNT(StudentID)"));
                    }
                    cnct.close();
                    break;
            }
        } catch (SQLException ex) {

        }
    }
}
