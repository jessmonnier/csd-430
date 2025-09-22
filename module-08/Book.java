package com.bookstore;
/* 
 * Jess Monnier
 * CSD-430 CRUD Project
 * Created 6 September 2025
 * Modified 21 September 2025
 * Book JavaBean to hold data from MySQL database row
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class Book implements Serializable {

    // Database fields
    private int id;
    private String title;
    private String author;
    private String description;
    private String genre;
    private String subgenre;
    private int stock;
    private BigDecimal cost;

    // No-arg constructor (required for JavaBeans)
    public Book() {}

    // Full constructor
    public Book(int id, String title, String author, String description,
                String genre, String subgenre, int stock, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.subgenre = subgenre;
        this.stock = stock;
        this.cost = cost;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenre) {
        this.subgenre = subgenre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}