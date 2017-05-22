/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import com.databasserne.config.DatabaseEnv;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.databasserne.models.*;
import java.sql.SQLException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsMapContaining;

/**
 *
 * @author Vixo
 */
public class MysqlBooksRepoTest {
    
    private MysqlBooksRepo booksRepo;
    
    public MysqlBooksRepoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getAllBoksTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        List<Book> books = booksRepo.getAll();
        
        assertThat(books, not(IsEmptyCollection.empty()));
    }
    
    @Test
    public void getBooksAndAuthorFromCityTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
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
        booksRepo = new MysqlBooksRepo();
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Vrøvl");
        
        assertThat(books, is(empty()));
    }
    
    @Test
    public void getCitiesFromBookTitleTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        List<City> cities = booksRepo.getCitiesFromBookTitle("La Fiammetta");
        
        assertThat(cities, not(IsEmptyCollection.empty()));
        assertThat(cities, hasItem(Matchers.<City>hasProperty("name", is("Venice"))));
        assertThat(cities, hasItem(Matchers.<City>hasProperty("name", is("Florence"))));
        assertThat(cities, hasItem(Matchers.<City>hasProperty("name", is("Naples"))));
        assertThat(cities, hasItem(Matchers.<City>hasProperty("name", is("Paris"))));
        assertThat(cities, hasItem(Matchers.<City>hasProperty("name", is("King"))));
    }
    
    @Test
    public void getCitiesFromIllegalBookTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        List<City>  cities = booksRepo.getCitiesFromBookTitle("Nonexistent");
        
        assertThat(cities, is(empty()));
    }
    
    @Test
    public void getBooksWithCitiesFromAuthorTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        Map<Book, List<City>> books = booksRepo.getBooksWithCitiesFromAuthor("Unknown");
        
        assertTrue(!books.isEmpty());
        assertThat(books, IsMapContaining.hasKey(Matchers.<Book>hasProperty("name", is("The King James Version of the Bible"))));
    }
    
    @Test
    public void getBooksWithCitiesFromIllegalAuthorTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        Map<Book, List<City>> books = booksRepo.getBooksWithCitiesFromAuthor("hahahaha");
        
        assertTrue(books.isEmpty());
    }
    
    @Test
    public void getBooksFromCityNearbyTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        List<Book> books = booksRepo.getBooksMentioningNearbyCity(37, -122, 25);
        
        assertThat(books, not(IsEmptyCollection.empty()));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Punchinello, Volume 1, No. 26, September 24, 1870"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Punchinello, Volume 1, No. 19, August 6, 1870"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Father of British Canada: a Chronicle of Carleton"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Beneath the Banner: Being Narratives of Noble Lives and Brave Deeds"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Salute to Adventurers"))));
    }
    
    @Test
    public void getBooksFromCityNotNearbyTest() throws SQLException {
        booksRepo = new MysqlBooksRepo();
        List<Book> books = booksRepo.getBooksMentioningNearbyCity(0, 0, 1);
        
        assertThat(books, is(empty()));
    }
}
