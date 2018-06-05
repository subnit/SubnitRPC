package com.subnit.rpc.server.nio;


import com.subnit.rpc.server.simple.SimpleSocketServer;
import org.junit.Test;

/**
 * description:
 * date : create in 22:19 2018/6/4
 * modified by :
 *
 * @author subo
 */


public class NioSocketTest {

    @Test
    public void NioServerTest() {
        new NioServer().startServer(1024, 9999, 3000);
    }



}
