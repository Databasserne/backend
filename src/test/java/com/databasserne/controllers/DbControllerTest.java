package com.databasserne.controllers;

import com.databasserne.config.DatabaseEnv;
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
    
    private DatabaseEnv env;
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
        env = new DatabaseEnv();
        con = mock(Connection.class);
        when(dbCon.getMysqlConnection("jdbc:mysql://localhost:3306/gutenberg_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                env.env("db.username"),
                env.env("db.password"))).thenReturn(con);
        
        Connection mysql = dbCon.getMysqlConnection("jdbc:mysql://localhost:3306/gutenberg_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                env.env("db.username"),
                env.env("db.password"));
        assertNotNull(mysql);
    }
    
    @Test
    public void testGetNeo4jDriver() {
        session = mock(Session.class);
        when(dbCon.getNeo4jSession(
                env.env("neo4j.username"),
                env.env("neo4j.password"))).thenReturn(session);
        
        Session neo4j = dbCon.getNeo4jSession(
                env.env("neo4j.username"),
                env.env("neo4j.password"));
        assertNotNull(neo4j);
    }
    
}
