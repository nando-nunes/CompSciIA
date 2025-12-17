/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.compsciia.compsciia;

/**
 *
 * @author fernandonunes
 */
public class CompSciIA {

    public static void main(String[] args) {
        Student student = DBTools.searchStudent(6);
        
        System.out.println(student.getName());
    }
}
