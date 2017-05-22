/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.config;

import com.databasserne.config.interfaces.IEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vixo
 */
public class DatabaseEnv implements IEnvironment {

    private InputStream input;
    private Properties prop = new Properties();
    
    public DatabaseEnv() {
    }
    
    @Override
    public String env(String key) {
        try {
            String filename = null;
            
            Map<String, String> env = System.getenv();
            if(env.containsKey("CI") && env.containsKey("CI") == true) {
                filename = "config/environment.test.properties";
            } else {
                filename = "config/environment.dev.properties";
            }
            
            input = getClass().getClassLoader().getResourceAsStream(filename);
            
            prop.load(input);
            
            String property = prop.getProperty(key);
            if(property != null) {
                return property;
            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseEnv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
