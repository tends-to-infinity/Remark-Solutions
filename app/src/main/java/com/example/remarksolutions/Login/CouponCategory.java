package com.example.remarksolutions.Login;

import java.util.ArrayList;
import java.util.Arrays;

public class CouponCategory
{

    private String[] a;
    private String[] cat={"Basic","Silver","Gold","Platinum"};
    private int[] amt={100,200,300,500};
    private ArrayList<String> b= new ArrayList<>();

    public CouponCategory(String[] a) {
        this.a = a;
    }

    public CouponCategory() {
        a=cat;
        b.addAll(Arrays.asList(cat));
    }

    public String[] getA() {
        return a;
    }

    public ArrayList<String> getB() {
        return b;
    }

    public int[] getAmt() {
        return amt;
    }

    public void setAmt(int[] amt) {
        this.amt = amt;
    }
}
