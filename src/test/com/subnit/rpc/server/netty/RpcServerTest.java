package com.subnit.rpc.server.netty;

import org.junit.Test;

/**
 * description:
 * date : create in 18:29 2018/6/7
 * modified by :
 *
 * @author subo
 */
public class RpcServerTest {

    @Test
    public void startRpcServer() {
        try {
            new RpcServer(9999).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
