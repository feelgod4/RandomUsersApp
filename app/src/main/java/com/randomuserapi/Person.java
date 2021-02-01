package com.randomuserapi;

public class Person {
    private String name;
    private String email;
    private String gender;
    private String image_url;
    private String birthdate;
    private String age;

    public Person(String name, String email, String gender, String image_url, String birthdate, String age){
        this.age = age;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.image_url = image_url;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAge(){
        return age;
    }
}
