package com.luoboduner.wesync.test;

public class Student {
    public  int id;
    public  String name;
    public boolean sex;
    public  String  phone;



    /**
     *
     * @params id
     * @params name
     * @params sex
     * @params phone
     */
    public Student(int id, String name, boolean sex, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    public Student() {

    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + (sex?"男":"女")+
                ", phone='" + phone + '\'' +
                '}';
    }
}
