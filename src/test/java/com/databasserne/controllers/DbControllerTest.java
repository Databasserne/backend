package com.databasserne.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.neo4j.driver.v1.Session;

/**
 *
 * @author Vixo
 */
public class DbControllerTest {
    
    private DbController dbCon;
    private Connection con;
    private Session session;
    
    public DbControllerTest() {
        dbCon = mock(DbController.class);
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
    public void testGetMysqlConnection() throws SQLException {
        con = mock(Connection.class);
        when(dbCon.getMysqlConnection()).thenReturn(con);
        
        Connection mysql = dbCon.getMysqlConnection();
        assertNotNull(mysql);
    }
    
    @Test
    public void testGetNeo4jDriver() {
        session = mock(Session.class);
        when(dbCon.getNeo4jSession()).thenReturn(session);
        
        Session neo4j = dbCon.getNeo4jSession();
        assertNotNull(neo4j);
    }
    
}
