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
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matchers;

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
    public void setUp() {
        booksRepo = new MysqlBooksRepo();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getBooksAndAuthorFromCityTest() {
        List<Book> books = booksRepo.getBooksAndAuthorFromCity("Florence");
        
        assertThat(books, hasItem(Matchers.<Book>hasProperty("name", is("Hej"))));
    }
}
