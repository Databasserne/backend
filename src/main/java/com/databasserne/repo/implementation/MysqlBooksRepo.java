/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.config.DatabaseEnv;
import com.databasserne.controllers.DbController;
import com.databasserne.repo.interfaces.IBooksRepo;
import com.databasserne.models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vixo
 */
public class MysqlBooksRepo implements IBooksRepo {

    private DatabaseEnv env;
    private DbController dbCon;
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet result;
    
    public MysqlBooksRepo() throws SQLException {
        env = new DatabaseEnv();
        dbCon = new DbController();
        this.con = dbCon.getMysqlConnection(env.env("db.connectionUrl"), env.env("db.username"), env.env("db.password"));
    }
    
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT Books.Name AS Book FROM Books");
            result = stmt.executeQuery();
            while(result.next()) {
                Book b = new Book(result.getString("Book"));
                books.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    
    @Override
    public List<Book> getBooksAndAuthorFromCity(String city) {
        List<Book> books = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT DISTINCT Authors.Name AS Author, Books.Name AS Book from Books "
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
        } finally {
            return books;
        }
    }

    @Override
    public List<City> getCitiesFromBookTitle(String bookTitle) {
        List<City> cities = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT Cities.Name, Cities.Geolat, Cities.Geolng FROM Cities "
                                        + "JOIN Books_Cities ON Books_Cities.City_ID = Cities.ID "
                                        + "JOIN Books ON Books.ID = Books_Cities.Book_ID "
                                        + "WHERE Books.Name = \""+bookTitle+"\";");
            result = stmt.executeQuery();
            while(result.next()) {
                City city = new City(result.getString("Cities.Name"), result.getFloat("Cities.Geolat"), result.getFloat("Cities.Geolng"));
                cities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cities;
        }
    }

    @Override
    public Map<Book, List<City>> getBooksWithCitiesFromAuthor(String author) {
        Map<Book, List<City>> books = new HashMap<>();
        try {
            stmt = con.prepareStatement("SELECT DISTINCT Books.Name, Cities.Name, Cities.Geolat, Cities.Geolng FROM Cities "
                                        + "JOIN Books_Cities ON Books_Cities.City_ID = Cities.ID "
                                        + "JOIN Books ON Books.ID = Books_Cities.Book_ID "
                                        + "WHERE Books.ID IN (SELECT Books.ID FROM Books "
                                        + "JOIN Books_Authors ON Books_Authors.Book_ID = Books.ID "
                                        + "JOIN Authors ON Authors.ID = Books_Authors.Author_ID "
                                        + "WHERE Authors.Name = \""+author+"\");");
            result = stmt.executeQuery();
            while(result.next()) {
                Book b = new Book(result.getString("Books.Name"));
                final City city = new City(result.getString("Cities.Name"), result.getFloat("Cities.Geolat"), result.getFloat("Cities.Geolng"));
                if(books.containsKey(b)) {
                    books.get(b).add(city);
                } else {
                    books.put(b, new ArrayList<City>(){{
                        add(city);
                    }});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }
}
