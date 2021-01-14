package com.example.remarksolutions.Login;

import java.util.ArrayList;
import java.util.Arrays;

public class Locality {



    private String[] locArray;
    private String[] localities={"Select Your Home Location","Anand Nagar","Birla Nagar","Deen Dayal Nagar","Ghosipura","Gulmohar City","Kala Saiyad","Kishan Bagh","Lashkar","Loahamandi","Madhav Ganj","MahalGaon","Model Town","Morar","Naya Bazar","Prashad Nagar","Rajeev Nagar"," Shindhe Ki Chawni","Shri Ram Colony","Thatipur","VijayNagar","Vinay Nagar","Sikroda Badori"};
    private ArrayList<String> locList= new ArrayList<>();

    public Locality(String[] a)
    {
        this.locArray = a;
    }

    public Locality() {
        locArray=localities;
        locList.addAll(Arrays.asList(localities));
    }

    public String[] getA() {
        return locArray;
    }

    public ArrayList<String> getLocList() {
        return locList;
    }

}
