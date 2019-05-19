package com.choxsu;

import com.choxsu.config.ChoxsuConfig;
import com.jfinal.server.undertow.UndertowServer;

/**
 * @author choxsu
 */
public class ChoxsuApplication {


    public static void main(String[] args) {
        UndertowServer.create(ChoxsuConfig.class).start();
    }


}
