package kh.com.cellcard.common.wrapper;

import an.awesome.pipelinr.Command;
import jakarta.annotation.Nullable;
import kh.com.cellcard.models.responses.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommandWrapper implements Command<Response> {
    public String methodName;
    public String accountId;
    public CommandWrapper(String methodName, @Nullable String accountId) {
        this.methodName = methodName;
        this.accountId = accountId;
    }
}
