package com.subnit.rpc.server.simple;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 描述:
 * date : create in 20:40 2018/6/4
 * modified by :
 *
 * @author subo
 */
public class SocketHandler implements Runnable{
    private Socket socket;
    private ObjectOutputStream out = null;
    public SocketHandler(){}

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String className = in.readUTF();
            String methodName = in.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
            Object[] arguments = (Object[]) in.readObject();
            Object claszz;
            if(!SimpleSocketServer.classMap.containsKey(className)) {
                claszz = Class.forName(className).newInstance();
                SimpleSocketServer.classMap.put(className, claszz);
            } else {
                claszz = SimpleSocketServer.classMap.get(className);
            }
            Method method = claszz.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(claszz, arguments);
            out.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
