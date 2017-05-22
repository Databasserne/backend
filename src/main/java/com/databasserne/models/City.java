/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.models;

/**
 *
 * @author Vixo
 */
public class City {
    
    private String name;
    private float geolat;
    private float geolng;

    public City(String name, float geolat, float geolng) {
        this.name = name;
        this.geolat = geolat;
        this.geolng = geolng;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGeolat() {
        return geolat;
    }

    public void setGeolat(float geolat) {
        this.geolat = geolat;
    }

    public float getGeolng() {
        return geolng;
    }

    public void setGeolng(float geolng) {
        this.geolng = geolng;
    }
    
    
}
