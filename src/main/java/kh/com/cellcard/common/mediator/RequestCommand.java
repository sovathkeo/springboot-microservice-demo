package kh.com.cellcard.common.mediator;

import com.google.gson.annotations.SerializedName;

public class RequestCommand {

    @SerializedName("")
    public String accountId;
    public String channel;
    public String methodName;
    public String requestPlan;

}
