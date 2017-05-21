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
            stmt = con.prepareStatement("SELECT Authors.Name AS Author, Books.Name AS Book from Books "
                                        + "JOIN Books_Authors ON Books_Authors.Book_ID = Books.ID "
                                        + "JOIN Authors ON Authors.ID = Books_Authors.Author_ID "
                                        + "JOIN Books_Cities ON Books_Cities.Book_ID = Books.ID "
                                        + "JOIN Cities ON Cities.ID = Books_Cities.City_ID "
                                        + "WHERE Cities.Name = \""+city+"\";");
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
