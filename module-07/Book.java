package com.library;
/* 
 * Jess Monnier
 * CSD-430 Module 7 Assignment
 * Created 6 September 2025
 * Modified 14 September 2025
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
    private boolean requested;
    private Date firstRequest;
    private int timesRequested;

    // JavaBean constructor
    public Book() {}

    // More useful (for this app) constructor
    public Book(int id, String title, String author, int pubYear,
                String description, String genre, String subgenre,
                boolean checkedOut, Date outDate, boolean requested,
                Date firstRequest, int timesRequested) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pubYear = pubYear;
        this.description = description;
        this.genre = genre;
        this.subgenre = subgenre;
        this.checkedOut = checkedOut;
        this.outDate = outDate;
        this.requested = requested;
        this.firstRequest = firstRequest;
        this.timesRequested = timesRequested;
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

    public boolean isRequested() { return requested; }
    public void setRequested(boolean requested) { this.requested = requested; }

    public Date getFirstRequest() { return firstRequest; }
    public void setFirstRequest(Date firstRequest) { this.firstRequest = firstRequest; }

    public int getTimesRequested() { return timesRequested; }
    public void setTimesRequested(int timesRequested) { this.timesRequested = timesRequested; }
}
