/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.repo.implementation;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.databasserne.models.*;
import java.sql.SQLException;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

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
    public void getBooksAndAuthorFromCityTest() throws SQLException {
        booksRepo = new MysqlBooksRepo("jdbc:mysql://127.0.0.1/gutenberg_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Florence");
        
        for (Book book : books) {
            System.out.println("Book: " + book.getName());
        }
        
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("The Complete Works of William Shakespeare"))));
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("La Fiammetta"))));
    }
    
    @Test
    public void getBooksAndAuthorFromIllegalCityTest() throws SQLException {
        booksRepo = new MysqlBooksRepo("jdbc:mysql://127.0.0.1/gutenberg_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Vrøvl");
        
        assertThat(books, is(empty()));
    }
}
