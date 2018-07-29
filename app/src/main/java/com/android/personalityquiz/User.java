package com.android.personalityquiz;

import java.io.Serializable;

public class User implements Serializable{
    private String Name;
    private String Mob;
    private String Email;
    private String AgeGroup;
    private String Result;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String mob) {
        Mob = mob;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAgeGroup() {
        return AgeGroup;
    }

    public void setAgeGroup(String ageGroup) {
        AgeGroup = ageGroup;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
