/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import com.databasserne.controllers.DbController;
import com.databasserne.repo.implementation.Neo4jBooksRepo;
import com.databasserne.repo.interfaces.IBooksRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@Path("neo4j")
public class Neo4jResource {

    private IBooksRepo booksRepo;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Neo4jResource
     */
    public Neo4jResource() {
        booksRepo = new Neo4jBooksRepo(new DbController());
    }

    @GET
    @Path("city/{city}")
    @Produces("application/json")
    public Response searchCity(@PathParam("city") String city) {
        return Response.status(Response.Status.OK).entity(gson.toJson(booksRepo.getBooksAndAuthorFromCity(city))).build();
    }
    
    @GET
    @Path("book/{book}")
    @Produces("application/json")
    public Response searchCitiesFromBookTitle(@PathParam("book") String bookTitle) {
        return Response.status(Response.Status.OK).entity(gson.toJson(booksRepo.getCitiesFromBookTitle(bookTitle))).build();
    }
}
