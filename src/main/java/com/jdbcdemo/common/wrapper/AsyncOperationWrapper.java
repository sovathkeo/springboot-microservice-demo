package com.jdbcdemo.common.wrapper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class AsyncOperationWrapper {


    @Async
    public void executeAsync( Object value, CallableOfValue callback ) {
        callback.callback(value);
    }

    @Async
    public void executeAsync(CallableOfVoid callback ) {
        callback.callback();
    }
}
