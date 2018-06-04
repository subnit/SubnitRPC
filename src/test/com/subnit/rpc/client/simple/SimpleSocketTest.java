package com.subnit.rpc.client.simple;


import com.subnit.rpc.server.simple.SimpleSocketServer;
import com.subnit.rpc.service.HelloService;
import com.subnit.rpc.service.impl.HelloServiceImpl;
import org.junit.Test;

/**
 * 描述:
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

    @Test
    public void ClientTest() {
        HelloService helloRpc = new HelloServiceImpl();
        SimpleSocketClient.ip = "127.0.0.1";
        SimpleSocketClient.port = 8888;
        helloRpc = SimpleSocketClient.create(helloRpc);
        System.out.println(helloRpc.hello("rpc"));
    }


}
