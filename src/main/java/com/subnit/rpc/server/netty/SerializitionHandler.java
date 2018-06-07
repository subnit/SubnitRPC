package com.subnit.rpc.server.netty;

import com.alibaba.fastjson.JSONObject;
import com.subnit.rpc.util.MethodInfo;
import com.subnit.rpc.util.MethodInfoStr;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * description:
 * date : create in 16:09 2018/6/7
 * modified by :
 *
 * @author subo
 */
public class SerializitionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String message = buf.toString(CharsetUtil.UTF_8);
        MethodInfoStr methodInfoStr = JSONObject.parseObject(message, MethodInfoStr.class);
        String className = methodInfoStr.getClassName();
        String methodName = methodInfoStr.getMethodName();
        Object[] arguments = (Object[]) JSONObject.parseObject(methodInfoStr.getArgsString(),Object[].class);
        String[] strs = methodInfoStr.getParameterTypesString().replace("[","").replace("]","").split(",");
        Class<?>[] parameterTypes = new Class[strs.length];
        for(int i = 0; i < strs.length; i++) {
            try {
                parameterTypes[i] = Class.forName(strs[i].replace("\"", ""));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setArguments(arguments);
        methodInfo.setClassName(className);
        methodInfo.setMethodName(methodName);
        methodInfo.setParameterTypes(parameterTypes);
        ctx.fireChannelRead(methodInfo);
    }
}
