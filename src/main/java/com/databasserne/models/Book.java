/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.models;

/**
 *
 * @author Vixo
 */
public class Book {
    private String name;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book() {
    }
    
    public Book(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Book)) {
            return false;
        }
        Book b = (Book) obj;
        return b.name.equals(this.name);
    }
    
    
}
