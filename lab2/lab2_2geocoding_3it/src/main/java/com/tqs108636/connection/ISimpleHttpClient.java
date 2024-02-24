package com.tqs108636.connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

/**
 *
 * @author ico
 */
public interface ISimpleHttpClient {

    public String doHttpGet(String url) throws IOException;
}
