package com.subnit.rpc.server.simple;


import org.junit.Test;

/**
 * description:
 * date : create in 22:19 2018/6/4
 * modified by :
 *
 * @author subo
 */


public class SimpleSocketTest {

    @Test
    public void ServerTest() {
        SimpleSocketServer.startServer(8888);
    }



}
