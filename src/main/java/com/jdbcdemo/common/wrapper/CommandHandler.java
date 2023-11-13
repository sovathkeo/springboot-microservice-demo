package com.jdbcdemo.common.wrapper;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public interface CommandHandler<C extends Command<AResponseBase>, AResponseBase> extends Command.Handler<C, AResponseBase> {

}
