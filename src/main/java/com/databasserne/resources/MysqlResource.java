/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import com.databasserne.models.Book;
import com.databasserne.models.City;
import com.databasserne.repo.implementation.MysqlBooksRepo;
import com.databasserne.repo.interfaces.IBooksRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Vixo
 */
@Path("mysql")
public class MysqlResource {

    private IBooksRepo booksRepo;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MysqlResource
     */
    public MysqlResource() throws SQLException {
        //booksRepo = new StubBooksRepo();
        booksRepo = new MysqlBooksRepo();
    }
    
    public MysqlResource(IBooksRepo repo) {
        booksRepo = repo;
    }

    @GET
    @Path("city/{city}")
    @Produces("application/json")
    public Response searchCity(@PathParam("city") String city) {
        return Response.status(Response.Status.OK).entity(gson.toJson(booksRepo.getBooksAndAuthorFromCity(city))).build();
    }
    
    @GET
    @Path("book/{bookTitle}")
    @Produces("application/json")
    public Response searchCitiesFromBookTitle(@PathParam("bookTitle") String bookTitle) {
        return Response.status(Response.Status.OK).entity(gson.toJson(booksRepo.getCitiesFromBookTitle(bookTitle))).build();
    }
    
    @GET
    @Path("author/{authorname}")
    @Produces("application/json")
    public Response searchBooksWithCitiesFromAuthor(@PathParam("authorname") String authorname) {
        JsonArray response = new JsonArray();
        Map<Book, List<City>> books = booksRepo.getBooksWithCitiesFromAuthor(authorname);
        for (Map.Entry<Book, List<City>> entry : books.entrySet()) {
            Book key = entry.getKey();
            List<City> value = entry.getValue();
            
            // Book json
            JsonObject bookJson = new JsonObject();
            bookJson.addProperty("name", key.getName());
            
            JsonArray citiesForBook = new JsonArray();
            
            for (City city : value) {
                JsonObject cityJson = new JsonObject();
                cityJson.addProperty("name", city.getName());
                cityJson.addProperty("geolat", city.getGeolat());
                cityJson.addProperty("geolng", city.getGeolng());
                citiesForBook.add(cityJson);
            }
            
            bookJson.add("cities", citiesForBook);
            response.add(bookJson);
        }
        
        
        return Response.status(Response.Status.OK).entity(gson.toJson(response)).build();
    }
}
