/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.controllers;

import com.databasserne.config.DatabaseEnv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

/**
 *
 * @author Vixo
 */
public class DbController {
    
    private DatabaseEnv env;
    private static final String DRIVER_MYSQL = "jdbc:mysql://localhost:3306/";
    private static final String DRIVER_NEO4J = "bolt://localhost:7687";
    
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "mk101593";
    private static final String NEO4J_USER = "neo4j";
    private static final String NEO4J_PASS = "class";
    
    public Connection getMysqlConnection(String driver, String user, String password) throws SQLException { 
        try {
            String sqlDriver = new DatabaseEnv().env("db.driverClass");
            Class.forName(sqlDriver).newInstance();
            return DriverManager.getConnection(driver, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Session getNeo4jSession() {
        Driver driver = GraphDatabase.driver( 
                "bolt://localhost:7687", 
                AuthTokens.basic(NEO4J_USER, NEO4J_PASS));
        
        return driver.session();
    }
}
