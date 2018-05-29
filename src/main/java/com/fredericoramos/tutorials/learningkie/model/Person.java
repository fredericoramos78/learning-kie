package com.loconoco.tutorials.learningkie.model;

public class Person {

    private int age;
    private String lang;

    public Person(int age, String lang) {
        this.age = age;
        this.lang = lang;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
