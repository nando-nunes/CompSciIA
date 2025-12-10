/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fernandonunes
 */
public class ConnectionFactory {
    public static Connection getConnection() {
        // [cite: 52] Connection URL
        String url = "jdbc:mysql://127.0.0.1:3306/CompSciIA?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String pass = "fernando";

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
