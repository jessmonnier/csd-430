package com.library;
/* 
 * Jess Monnier
 * CSD-430 Module 5 & 6 Assignment
 * Created 6 September 2025
 * Book JavaBean to hold data from MySQL database row
 */

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable {

    // A private variable for each DB column
    private int id;
    private String title;
    private String author;
    private int pubYear;
    private String description;
    private String genre;
    private String subgenre;
    private boolean checkedOut;
    private Date outDate;

    // JavaBean constructor
    public Book() {}

    // More useful (for this app) constructor
    public Book(int id, String title, String author, int pubYear,
                String description, String genre, String subgenre,
                boolean checkedOut, Date outDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubYear = pubYear;
        this.description = description;
        this.genre = genre;
        this.subgenre = subgenre;
        this.checkedOut = checkedOut;
        this.outDate = outDate;
    }
    
    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getPubYear() { return pubYear; }
    public void setPubYear(int pubYear) { this.pubYear = pubYear; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getSubgenre() { return subgenre; }
    public void setSubgenre(String subgenre) { this.subgenre = subgenre; }

    public boolean isCheckedOut() { return checkedOut; }
    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }

    public Date getOutDate() { return outDate; }
    public void setOutDate(Date outDate) { this.outDate = outDate; }
}
