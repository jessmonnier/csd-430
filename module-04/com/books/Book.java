package com.books;
/* 
 * Jess Monnier
 * CSD-430 Module 4 Assignment
 * Book JavaBean to hold data from MySQL database row
 */

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private int year;
    private String cover;
    private String description;

    // JavaBean no-argument constructor
    public Book() {

    }

    // constructor
    public Book(int id, String title, String author, int year, String cover, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.cover = cover;
        this.description = description;
    }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getCover() { return cover; }
    public String getDescription() { return description; }

    // setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setCover(String cover) { this.cover = cover; }
    public void setDescription(String description) { this.description = description; }
}