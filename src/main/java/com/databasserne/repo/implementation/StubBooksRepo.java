/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.repo.interfaces.IBooksRepo;
import com.databasserne.models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vixo
 */
public class StubBooksRepo implements IBooksRepo {

    @Override
    public List<Book> getBooksAndAuthorFromCity(String city) {
        
        List<Book> books = new ArrayList<Book>();
        Book b = new Book("Test book");
        b.setAuthor(new Author("My author"));
        books.add(b);
        
        return books;
    }

    @Override
    public List<City> getCitiesFromBookTitle(String bookTitle) {
        
        List<City> cities = new ArrayList<City>();
        City city = new City("Lyngby", 1.23456F, 5.42362F);
        cities.add(city);
        
        return cities;
    }

    @Override
    public Map<Book, List<City>> getBooksWithCitiesFromAuthor(String author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
