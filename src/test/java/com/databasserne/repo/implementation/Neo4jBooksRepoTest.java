/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.models.Book;
import java.sql.SQLException;
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
        booksRepo = new Neo4jBooksRepo();
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Florence");
        
        assertThat(books, not(IsEmptyCollection.empty()));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Complete Works of William Shakespeare"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("La Fiammetta"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Divine Comedy, Longfellow's Translation, Hell"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("A Beautiful Possibility"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Mystery of the Boule Cabinet: A Detective Story"))));
    }
    
    @Test
    public void getBooksAndAuthorFromIllegalCityTest() throws SQLException {
        booksRepo = new Neo4jBooksRepo();
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Vrøvl");
        
        assertThat(books, is(empty()));
    }
}
