package ru.emelianov.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import javax.validation.constraints.Pattern;

public class Book{

    private int id;

    @NotEmpty(message = "title book must be not empty")
    private String name;

    @NotEmpty
    @Size(min=2, max=100, message = "author must be between 2 and 100")
    private String author;

    @Min(value=1500, message = "year must be greater than 1500")
    private int age;

    public Book(String name, String author, int age) {
        this.name = name;
        this.author = author;
        this.age = age;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}