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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;

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
                        + "WHERE c.name =~ {city}"
                        + "RETURN DISTINCT b.name as Book, a.name as Author",
                    Values.parameters("city", city));
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
                                + "WHERE b.name =~ {bookTitle}"
                                + "RETURN DISTINCT c.name as City, c.Geolat as Geolat, c.Geolng as Geolng",
                    Values.parameters("bookTitle", bookTitle));
            while(result.hasNext()) {
                rec = result.next();
                Double doubleLat = rec.get("Geolat").asDouble();
                Double doubleLng = rec.get("Geolng").asDouble();
                Float lat = doubleLat.floatValue();
                Float lng = doubleLng.floatValue();
                
                City city = new City(rec.get("City").asString(), lat, lng);
                cities.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return cities;
        }
    }

    @Override
    public Map<Book, List<City>> getBooksWithCitiesFromAuthor(String author) {
        Map<Book, List<City>> books = new HashMap<>();
        try {
            result = session.run("MATCH (c:City)<-[:Mentions]-(b:Book)<-[:Authored]-(a:Author) "
                                + "WHERE a.name =~ {author}"
                                + "RETURN DISTINCT b.name as Book, c.name as City, c.Geolat as Geolat, c.Geolng as Geolng",
                    Values.parameters("author", author));
            while(result.hasNext()) {
                rec = result.next();
                
                Double doubleLat = rec.get("Geolat").asDouble();
                Double doubleLng = rec.get("Geolng").asDouble();
                Float lat = doubleLat.floatValue();
                Float lng = doubleLng.floatValue();
                
                Book b = new Book(rec.get("Book").asString());
                final City city = new City(rec.get("City").asString(), lat, lng);
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

    @Override
    public List<Book> getBooksMentioningNearbyCity(float lat, float lng, float distance) {
        List<Book> books = new ArrayList<>();
        try {
            result = session.run("MATCH (b:Book)-[:Mentions]->(c:City) "
                                + "WHERE  distance(point({longitude:c.Geolng, latitude: c.Geolat}), point({ longitude: {lng}, latitude: {lat}}))/1000 <= {distance} "
                                + "return b.Name AS Name",
                    Values.parameters("distance", distance, "lng", lng, "lat", lat));
            while(result.hasNext()) {
                rec = result.next();
                
                Book book = new Book(rec.get("Name").asString());
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }
    
}
