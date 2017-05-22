/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.interfaces;

import com.databasserne.models.*;
import java.util.List;

/**
 *
 * @author Vixo
 */
public interface IBooksRepo {
    List<Book> getBooksAndAuthorFromCity(String city);
    List<City> getCitiesFromBookTitle(String bookTitle);
}
