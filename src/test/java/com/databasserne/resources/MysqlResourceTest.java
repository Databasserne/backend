/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.resources;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author alexander
 */
public class MysqlResourceTest {
    
    public MysqlResourceTest() {
    }
    
    @Before
    public void setUp() {
        RestAssured.port = 8080;
        RestAssured.basePath = "/web/api/mysql";
    }
    
    @Test
    public void searchCity(){
        when()
            .get("city/London")
        .then()
            .assertThat()
                .body("", hasSize(greaterThan(0)))
                .body("[0].name", equalTo("The Complete Works of William Shakespeare"))
                .body("[0].author.name", equalTo("Shakespeare, William"));
    }
    
    @Test
    public void searchBook(){
        when()
            .get("book/The Warriors")
        .then()
            .assertThat()
                .body("", hasSize(greaterThan(0)))
                .body("[0].name", equalTo("Tyre"))
                .body("[0].geolat", equalTo(33.27333f))
                .body("[0].geolng", equalTo(35.19389f));
    }
    
    @Test
    public void searchAuthor(){
        when()
            .get("author/Shakespeare, William")
        .then()
            .assertThat()
                .body("", hasSize(greaterThan(0)))
                .body("[0].name", equalTo("The Complete Works of William Shakespeare"));
    }
    
    @Test
    public void searchGeolocation(){
        when()
            .get("location/33.27333/35.19389")
        .then()
            .assertThat()
                .body("", hasSize(greaterThan(0)))
                .body("[0].name", equalTo("The King James Version of the Bible"));
    }
}