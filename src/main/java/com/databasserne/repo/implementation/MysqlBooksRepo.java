/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.controllers.DbController;
import com.databasserne.repo.interfaces.IBooksRepo;
import com.databasserne.models.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vixo
 */
public class MysqlBooksRepo implements IBooksRepo {

    private DbController dbCon;
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet result;
    
    public MysqlBooksRepo(String driver) throws SQLException {
        dbCon = new DbController();
        this.con = dbCon.getMysqlConnection(driver);
    }
    
    @Override
    public List<Book> getBooksAndAuthorFromCity(String city) {
        List<Book> books = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT authors.Name AS Author, books.Name AS Book from books "
                                        + "JOIN books_authors ON books_authors.Book_ID = books.ID "
                                        + "JOIN authors ON authors.ID = books_authors.Author_ID "
                                        + "JOIN books_cities ON books_cities.Book_ID = books.ID "
                                        + "JOIN cities ON cities.ID = books_cities.City_ID "
                                        + "WHERE cities.Name = \""+city+"\";");
            result = stmt.executeQuery();
            while(result.next()) {
                Book b = new Book(result.getString("Book"));
                b.setAuthor(new Author(result.getString("Author")));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return books;
    }
    
}
