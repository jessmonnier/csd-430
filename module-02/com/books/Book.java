package com.books;
/* 
 * Jess Monnier
 * CSD-430 Module 2 Assignment
 */

public class Book {
    private int id;
    private String title;
    private String author;
    private String cover;
    private String description;

    // constructor
    public Book(int id, String title, String author, String cover, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.description = description;
    }

    // getters (and setters if needed)
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCover() { return cover; }
    public String getDescription() { return description; }
}