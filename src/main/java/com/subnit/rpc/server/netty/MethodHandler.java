package com.subnit.rpc.server.netty;

import com.subnit.rpc.server.simple.SimpleSocketServer;
import com.subnit.rpc.util.MethodInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 * date : create in 15:41 2018/6/7
 * modified by :
 *
 * @author subo
 */
public class MethodHandler extends ChannelInboundHandlerAdapter {
    public static ConcurrentHashMap<String, Object> classMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MethodInfo methodInfo = (MethodInfo) msg;
        String className = methodInfo.getClassName();
        String methodName = methodInfo.getMethodName();
        Class<?>[] parameterTypes = methodInfo.getParameterTypes();
        Object[] arguments = methodInfo.getArguments();
        Object claszz;
        if(!SimpleSocketServer.classMap.containsKey(className)) {
            claszz = Class.forName(className).newInstance();
            SimpleSocketServer.classMap.put(className, claszz);
        } else {
            claszz = SimpleSocketServer.classMap.get(className);
        }
        Method method = claszz.getClass().getMethod(methodName, parameterTypes);
        Object result = method.invoke(claszz, arguments);
        ctx.write(result);
        ctx.flush();
        ctx.close();

    }


}
