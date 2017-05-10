/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import io.swagger.jaxrs.config.BeanConfig;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Vixo
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        swaggerConfiguration();
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    private void swaggerConfiguration() {
        BeanConfig bean = new BeanConfig();
        bean.setVersion("1.0.0");
        bean.setSchemes(new String[]{"http"});
        bean.setHost("localhost:8084/web");
        bean.setBasePath("api");
        bean.setResourcePackage("com.databasserne.resources.TestResource.java");
        bean.setScan(true);
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
    }
    
}
