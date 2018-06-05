package com.subnit.rpc.server.simple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description:
 * date : create in 20:35 2018/6/4
 * modified by :
 *
 * @author subo
 */
public class SimpleSocketServer {
    public static ConcurrentHashMap<String, Object> classMap = new ConcurrentHashMap<String, Object>();


    public static void startServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                final Socket socket = server.accept();
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.submit(new SocketHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
