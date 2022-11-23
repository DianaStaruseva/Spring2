package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author {
    public Author(String name, String patronymic, String surname,
                  byte age, short numberOfWorks) {

        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.age = age;
        this.numberOfWorks = numberOfWorks;
    }

    public Author() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, patronymic,surname;
    private  byte age;
    private  short numberOfWorks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public short getNumberOfWorks() {
        return numberOfWorks;
    }

    public void setNumberOfWorks(short numberOfWorks) {
        this.numberOfWorks = numberOfWorks;
    }
}
