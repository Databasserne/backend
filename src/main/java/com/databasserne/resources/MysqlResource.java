/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import com.databasserne.repo.implementation.MysqlBooksRepo;
import com.databasserne.repo.implementation.StubBooksRepo;
import com.databasserne.repo.interfaces.IBooksRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
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

    @GET
    @Path("city/{city}")
    @Produces("application/json")
    public Response searchCity(@PathParam("city") String city) {
        return Response.status(Response.Status.OK).entity(gson.toJson(booksRepo.getBooksAndAuthorFromCity(city))).build();
    }
}
