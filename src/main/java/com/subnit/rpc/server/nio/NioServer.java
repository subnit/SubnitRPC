package com.subnit.rpc.server.nio;

import com.alibaba.fastjson.JSONObject;
import com.subnit.rpc.server.simple.SimpleSocketServer;
import com.subnit.rpc.util.MethodDTO;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * description:
 * date : create in 14:49 2018/6/3
 * modified by :
 *
 * @author subo
 */
public class NioServer {

    public  void startServer(Integer bufferSize, Integer port, int timeOut) {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if (selector.select(timeOut) == 0) {
                    System.out.println("没有Channel就绪");
                    continue;
                }

                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        handleAccept(key, bufferSize);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable() && key.isValid()) {

                    }
                    if (key.isConnectable()) {

                    }
                    iter.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private  void handleAccept(SelectionKey key, Integer bufferSize) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel sc = channel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(bufferSize));
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        StringBuffer res = new StringBuffer();
            sc.read(buf);
            buf.flip();
            while (buf.hasRemaining()) {
                res.append((char)buf.get());
            }

        MethodDTO methodDTO = JSONObject.parseObject(res.toString(), MethodDTO.class);
        String className = methodDTO.getClassName();
        String methodName = methodDTO.getMethodName();
        Object[] arguments = (Object[]) JSONObject.parseObject(methodDTO.getArgsString(),Object[].class);
        Class<?>[] parameterTypes = new Class[1];
        String[] strs = methodDTO.getParameterTypesString().replace("[","").replace("]","").split(",");
        try {
            parameterTypes[0] = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object result = null;
        try {
            Object claszz;
            if(!SimpleSocketServer.classMap.containsKey(className)) {
                claszz = Class.forName(className).newInstance();
                SimpleSocketServer.classMap.put(className, claszz);
            } else {
                claszz = SimpleSocketServer.classMap.get(className);
            }
            Method method = claszz.getClass().getMethod(methodName, parameterTypes);
            result = method.invoke(claszz, arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String resStr = JSONObject.toJSONString(result);
        buf.clear();
        buf.put(resStr.getBytes());
        while(buf.hasRemaining()) {
            sc.write(buf);
        }
        sc.close();
    }






}
