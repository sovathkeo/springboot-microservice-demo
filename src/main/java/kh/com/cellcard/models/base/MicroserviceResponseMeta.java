package kh.com.cellcard.models.base;

import com.google.gson.annotations.SerializedName;


public class MicroserviceResponseMeta {

    @SerializedName("correlationId")
    public String correlationId;

    @SerializedName("requestId")
    public String requestId;

    @SerializedName("datetime")
    public String datetime;
}
