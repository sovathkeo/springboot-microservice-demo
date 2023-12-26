package kh.com.cellcard.models.base;

import com.google.gson.annotations.SerializedName;

public class MicroserviceResponseData {

    @SerializedName("error_code")
    public String errorCode;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("error_description")
    public String errorDescription;

}
