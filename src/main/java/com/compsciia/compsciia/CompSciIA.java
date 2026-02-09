/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.compsciia.compsciia;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fernandonunes
 */
public class CompSciIA {

    public static void main(String[] args) {
//        System.out.println(DBTools.findUser("admin"));
        Map<String,Integer> map = new HashMap<>();
        map.put("aba", 123);
        map.put("cbc", 234);
        
//        for(String key : map.keySet()){
//            System.out.println(key+ ": "+map.get(key));
//        }
        
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.parse("2012-11-08");
        System.out.println(Period.between(start,end).getYears());
    }
}
