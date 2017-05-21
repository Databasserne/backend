/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.databasserne.config.interfaces;

/**
 *
 * @author Vixo
 */
public interface IDatabaseEnv {
    String env(String key);
    String driver();
    String user();
    String password();
}
