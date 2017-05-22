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
    
    public Session getNeo4jSession(String username, String password) {
        String neo4jDriver = new DatabaseEnv().env("neo4j.connectionUrl");
        Driver driver = GraphDatabase.driver( 
                neo4jDriver, 
                AuthTokens.basic(username, password));
        
        return driver.session();
    }
}
