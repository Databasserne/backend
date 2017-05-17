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

/**
 *
 * @author Vixo
 */
public class MysqlBooksRepo implements IBooksRepo {

    @Override
    public List<Book> getBooksAndAuthorFromCity(String city) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
