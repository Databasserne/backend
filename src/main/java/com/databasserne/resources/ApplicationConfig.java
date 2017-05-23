/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Vixo
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.databasserne.resources.MysqlResource.class);
        resources.add(com.databasserne.resources.Neo4jResource.class);
    }
    
}
