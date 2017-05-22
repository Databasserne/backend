/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.interfaces;

import com.databasserne.models.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vixo
 */
public interface IBooksRepo {
    List<Book> getBooksAndAuthorFromCity(String city);
    List<City> getCitiesFromBookTitle(String bookTitle);
    Map<Book, List<City>> getBooksWithCitiesFromAuthor(String author);
    List<Book> getBooksMentioningNearbyCity(float lat, float lng, float distance);
}
