package com.jdbcdemo.common.wrapper;

import an.awesome.pipelinr.Command;
import com.jdbcdemo.dtos.responses.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommandWrapper implements Command<Response> {
    public String methodName;
    public CommandWrapper(String methodName) {
        this.methodName = methodName;
    }
    /*String getMethodName() { return methodName;}
    void setMethodName(String methodName) { this.methodName = methodName;}*/
}
