package com.example.remarksolutions.Login;

import java.util.ArrayList;
import java.util.Arrays;

public class Gender {


    private String[] a;
    private String[] cat={"Male","Female","Others"};
    private ArrayList<String> b= new ArrayList<>();

    public Gender(String[] a) {
        this.a = a;
    }

    public Gender() {
        a=cat;
        b.addAll(Arrays.asList(cat));
    }

    public String[] getA() {
        return a;
    }

    public ArrayList<String> getB() {
        return b;
    }
}
