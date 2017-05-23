/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.config.DatabaseEnv;
import com.databasserne.controllers.DbController;
import com.databasserne.models.Book;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.empty;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

/**
 *
 * @author Vixo
 */
public class Neo4jBooksRepoTest {
    
    private Neo4jBooksRepo booksRepo;
    
    public Neo4jBooksRepoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getBooksAndAuthorFromCityTest() {
        DatabaseEnv env = new DatabaseEnv();
        DbController dbCon = mock(DbController.class);
        Session session = mock(Session.class);
        StatementResult result = mock(StatementResult.class);
        Record rec = mock(Record.class);
        Value val = mock(Value.class);
        Value aVal = mock(Value.class);
        String city = "Florence";
        
        when(dbCon.getNeo4jSession(
                env.env("neo4j.username"),
                env.env("neo4j.password"))).thenReturn(session);
        
        when(session.run("MATCH (c:City)<-[:Mentions]-(b:Book)<-[:Authored]-(a:Author) "
                        + "WHERE c.name =~ \"(?i)"+city+"\""
                        + "RETURN DISTINCT b.name as Book, a.name as Author"))
                .thenReturn(result);
        when(result.hasNext())
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.FALSE);
        when(result.next()).thenReturn(rec);
        when(rec.get("Book")).thenReturn(val);
        when(val.asString())
                .thenReturn("The Complete Works of William Shakespeare")
                .thenReturn("La Fiammetta")
                .thenReturn("Divine Comedy, Longfellow's Translation, Hell")
                .thenReturn("A Beautiful Possibility")
                .thenReturn("The Mystery of the Boule Cabinet: A Detective Story");
        when(rec.get("Author")).thenReturn(aVal);
        when(aVal.asString())
                .thenReturn("Shakespeare, William");
        
        booksRepo = new Neo4jBooksRepo(dbCon);
        
        List<Book> books = booksRepo.getBooksAndAuthorFromCity(city);
        
        assertThat(books, not(IsEmptyCollection.empty()));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Complete Works of William Shakespeare"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("La Fiammetta"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Divine Comedy, Longfellow's Translation, Hell"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("A Beautiful Possibility"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Mystery of the Boule Cabinet: A Detective Story"))));
    }
    
    @Test
    public void getBooksAndAuthorFromIllegalCityTest() throws SQLException {
        DatabaseEnv env = new DatabaseEnv();
        DbController dbCon = mock(DbController.class);
        Session session = mock(Session.class);
        StatementResult result = mock(StatementResult.class);
        
        String city = "Vrøvl";
        when(dbCon.getNeo4jSession(
                env.env("neo4j.username"),
                env.env("neo4j.password"))).thenReturn(session);
        
        when(session.run("MATCH (c:City)<-[:Mentions]-(b:Book)<-[:Authored]-(a:Author) "
                        + "WHERE c.name =~ \"(?i)"+city+"\""
                        + "RETURN DISTINCT b.name as Book, a.name as Author"))
                .thenReturn(result);
        when(result.hasNext())
                .thenReturn(Boolean.FALSE);
        
        booksRepo = new Neo4jBooksRepo(dbCon);
        List<Book> books = booksRepo.getBooksAndAuthorFromCity(city);
        
        assertThat(books, is(empty()));
    }
}
