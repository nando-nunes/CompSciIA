/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

import java.time.LocalDate;

/**
 *
 * @author fernandonunes
 */
public class Student {
    private String name;
    private LocalDate birthdate;
    private int group;
    private int entry;
    private String prevSchool;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrevSchool() {
        return prevSchool;
    }

    public void setPrevSchool(String prevSchool) {
        this.prevSchool = prevSchool;
    }
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Student(){
        
    }
    
    public Student(String name, LocalDate birthdate, int group, int entry, Address address) {
        this.name = name;
        this.birthdate = birthdate;
        this.group = group;
        this.entry = entry;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    
    public void setBirthdate(String birthdate) {
        this.birthdate = LocalDate.parse(birthdate);
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }
    
}
