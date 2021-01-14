package com.example.remarksolutions.Models;

public class UserModel {
    String name, location;
    int coins;
    long mob;
    int age;

    public UserModel(String name, String location, int coins, long mob, int age) {
        this.name = name;
        this.location = location;
        this.coins = coins;
        this.mob = mob;
        this.age = age;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public long getMob() {
        return mob;
    }

    public void setMob(long mob) {
        this.mob = mob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
