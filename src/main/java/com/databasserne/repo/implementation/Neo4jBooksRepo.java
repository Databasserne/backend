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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 *
 * @author Vixo
 */
public class Neo4jBooksRepo implements IBooksRepo {

    private DatabaseEnv env;
    private DbController dbCon;
    private Session session;
    private PreparedStatement stmt;
    private StatementResult result;
    private Record rec;

    public Neo4jBooksRepo(DbController dbCon) {
        env = new DatabaseEnv();
        this.dbCon = dbCon;
        session = dbCon.getNeo4jSession(env.env("neo4j.username"), env.env("neo4j.password"));
    }
    
    @Override
    public List<Book> getBooksAndAuthorFromCity(String city) {
        List<Book> books = new ArrayList<>();
        try {
            result = session.run("MATCH (c:City)<-[:Mentions]-(b:Book)<-[:Authored]-(a:Author) "
                        + "WHERE c.name =~ \"(?i)"+city+"\""
                        + "RETURN DISTINCT b.name as Book, a.name as Author");
            while(result.hasNext()) {
                rec = result.next();
                
                Book b = new Book(rec.get("Book").asString());
                Author a = new Author(rec.get("Author").asString());
                b.setAuthor(a);
                books.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }

    @Override
    public List<City> getCitiesFromBookTitle(String bookTitle) {
        List<City> cities = new ArrayList<>();
        try {
            result = session.run("MATCH (c:City)<-[:Mentions]-(b:Book) "
                                + "WHERE b.name =~ \"(?i)"+bookTitle+"\" "
                                + "RETURN DISTINCT c.name as City, c.Geolat as Geolat, c.Geolng as Geolng");
            while(result.hasNext()) {
                rec = result.next();
                
                City city = new City(rec.get("City").asString(), rec.get("Geolat").asFloat(), rec.get("Geolng").asFloat());
                cities.add(city);
            }
        } catch (Exception e) {
        } finally {
            return cities;
        }
    }

    @Override
    public Map<Book, List<City>> getBooksWithCitiesFromAuthor(String author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> getBooksMentioningNearbyCity(float lat, float lng, float distance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
