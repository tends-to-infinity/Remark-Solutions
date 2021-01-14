package com.example.remarksolutions.Login;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopCategories
{

    private String[] a;
    private String[] cat={"Salon","Cafe","Restaurants","Service","Cloth Retail"};
    private ArrayList<String> b= new ArrayList<>();

    public ShopCategories(String[] a) {
        this.a = a;
    }

    public ShopCategories() {
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
