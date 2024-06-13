package com.example.storyproject.model;

public class Story {

    private String name;
    private int age;
    private String city;
    private String college_name;
    private String profession;
    private String animal_type;
    private String pet_name;

    public Story(){
        name="";
        age=0;
        city="";
        college_name="";
        profession="";
        animal_type="";
        pet_name="";
    }
    public Story(String name, int age, String city, String college_name, String profession, String animal_type, String pet_name) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.college_name = college_name;
        this.profession = profession;
        this.animal_type = animal_type;
        this.pet_name = pet_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAnimal_type() {
        return animal_type;
    }

    public void setAnimal_type(String animal_type) {
        this.animal_type = animal_type;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String storyString() {
        return "There once was a person named " + name + " who lived in " + city + ". At the age of " + age +
                ", " + name + " went to college at " + college_name + ". " + name + " was graduated and went to work as a " +
                profession + ". Then, " + name + " adopted a " + animal_type + " named " + pet_name + ". They both lived happily ever after!";

    }
}
