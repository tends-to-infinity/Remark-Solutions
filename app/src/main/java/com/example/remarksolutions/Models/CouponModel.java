package com.example.remarksolutions.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CouponModel  {

    String shopNo,title,description,rewardID,category;
    int quantity;
    String coupid;

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    Map<String,String> conditions = new HashMap<>();

    public String getCoupid() {
        return coupid;
    }

    public void setCoupid(String coupid) {
        this.coupid = coupid;
    }

    public CouponModel()
    {

    }
    public CouponModel(DocumentSnapshot documentSnapshot)
    {
        this.shopNo = documentSnapshot.get("shopNo").toString();
        this.title = documentSnapshot.get("title").toString();
        this.description = documentSnapshot.get("description").toString();
        this.rewardID = documentSnapshot.get("rewardID").toString();
        this.category = documentSnapshot.get("category").toString();
        this.quantity = Integer.parseInt(documentSnapshot.get("quantity").toString());
        this.coupid=documentSnapshot.getId();
    }

    public CouponModel(String shopNo, String title, String description, String rewardID, String category, int quantity, Map<String, String> conditions) {
        this.shopNo = shopNo;
        this.title = title;
        this.description = description;
        this.rewardID = rewardID;
        this.category = category;
        this.quantity = quantity;
        this.conditions = conditions;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRewardID() {
        return rewardID;
    }

    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
