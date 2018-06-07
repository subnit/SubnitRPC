package com.subnit.rpc.util;


/**
 * description:
 * date : create in 10:18 2018/6/5
 * modified by :
 *
 * @author subo
 */

public class MethodInfo {
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;

    public MethodInfo() {
    }

    public MethodInfo(String className,
                      String methodName,
                      Class<?>[] parameterTypes,
                      Object[] arguments) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
