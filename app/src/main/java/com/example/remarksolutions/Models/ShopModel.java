package com.example.remarksolutions.Models;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShopModel {


    public String name,description, shopID;
    public String src;
    public QueryDocumentSnapshot documentSnapshot;
    public ArrayList<String> images;


    public ShopModel(String name, String description, String shopID, String src, QueryDocumentSnapshot documentSnapshot, ArrayList<String> images) {

        this.name = name;
        this.description = description;
        this.shopID = shopID;
        this.src = src;
        this.documentSnapshot = documentSnapshot;
        this.images = images;
    }





    public ShopModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
