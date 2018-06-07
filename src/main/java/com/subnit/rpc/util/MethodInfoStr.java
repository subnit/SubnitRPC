package com.subnit.rpc.util;


/**
 * description:
 * date : create in 10:18 2018/6/5
 * modified by :
 *
 * @author subo
 */

public class MethodInfoStr {
    private String className;
    private String methodName;
    private String parameterTypesString;
    private String argsString;

    public MethodInfoStr() {
    }

    public MethodInfoStr(String className,
                         String methodName,
                         String parameterTypesString,
                         String argsString) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypesString = parameterTypesString;
        this.argsString = argsString;
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

    public String getParameterTypesString() {
        return parameterTypesString;
    }

    public void setParameterTypesString(String parameterTypesString) {
        this.parameterTypesString = parameterTypesString;
    }

    public String getArgsString() {
        return argsString;
    }

    public void setArgsString(String argsString) {
        this.argsString = argsString;
    }
}
