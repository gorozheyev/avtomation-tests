package com.training.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadDataFromFile {


        public static void main(String[] args) throws IOException {
            File file = new File("C:\\Users\\gorozheyevd\\Desktop\\DomainsAvtopoisk.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int a = 0;
            String s;
            while((s=reader.readLine()) != null){
            a++;
                System.out.println(s);
            }
            System.out.println(a);
        }
}
