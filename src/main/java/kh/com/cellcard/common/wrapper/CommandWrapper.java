package kh.com.cellcard.common.wrapper;

import an.awesome.pipelinr.Command;
import jakarta.annotation.Nullable;
import kh.com.cellcard.models.responses.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommandWrapper implements Command<Response> {
    public String accountId;
    public String methodName;
    public String requestPlan;
    public String channel;
    public CommandWrapper(
            String methodName,
            @Nullable String accountId) {
        this.methodName = methodName;
        this.accountId = accountId;
    }

    public CommandWrapper(
            String methodName,
            @Nullable String accountId,
            @Nullable String channel,
            @Nullable String requestPlan) {
        this.methodName = methodName;
        this.accountId = accountId;
        this.requestPlan = requestPlan;
        this.channel = channel;
    }
}
