package com.training.java;

/**
 * Created by gorozheyevd on 14.09.2017.
 */
// Изучение Java
public class RecursionTest {
    public static void main(String[] args) {

       print(0);
        System.out.println();
       print2(0);
    }

//    первый вариант
    public static void print(int i) {
        if (i < 5) {
            print(i+1);
        }
        System.out.println(" " + i);
    }

//    второй вариант
    public static void print2(int k){
        System.out.println(" " + k);
    if (k < 5)
        print2(++k);
    }
}