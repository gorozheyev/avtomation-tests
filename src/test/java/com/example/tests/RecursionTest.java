package com.example.tests;

/**
 * Created by gorozheyevd on 14.09.2017.
 */
// Изучение Java
public class RecursionTest {
    public static void main(String[] args) {
       print(0);
    }

    public static void print(int i) {
        if (i < 5) {
            System.out.println(" " + i);
            i ++;
            print(i);
        }
    }
}