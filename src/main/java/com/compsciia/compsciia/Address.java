/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.compsciia.compsciia;

/**
 *
 * @author fernandonunes
 */
public class Address {

    private String street;
    private int number;
    private String neighborhood;
    private String city;
    private String postalCode;

    private boolean valid = false;

    public Address() {
    }

    public Address(String fullAddress) { //Used when creating an Address using the Students database
        String[] components = fullAddress.split(",");
        this.street = components[0].trim();
        this.number = Integer.parseInt(components[1].trim());
        this.postalCode = components[2].trim();
        this.neighborhood = components[3].trim();
        this.city = components[4].trim();
        this.valid = true;
    }

    public Address(String street, int number, String neighborhood, String city, String postalCode) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.postalCode = postalCode;
        this.valid = true;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return street + ", " + number + ", " + postalCode + ", " + neighborhood + ", " + city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
