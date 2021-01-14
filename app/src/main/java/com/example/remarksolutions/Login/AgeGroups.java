package com.example.remarksolutions.Login;

import java.util.ArrayList;
import java.util.Arrays;

public class AgeGroups {


    private String[] a;
    private String[] grp={"13 to 18","18 to 25","25 to 35","above 35"};
    private ArrayList<String> ageGroup= new ArrayList<>();

    public AgeGroups(String[] a) {
        this.a = a;
    }

    public AgeGroups() {
        a=grp;
        ageGroup.addAll(Arrays.asList(grp));
    }

    public String[] getA() {
        return a;
    }

    public ArrayList<String> getB() {
        return ageGroup;
    }

}
