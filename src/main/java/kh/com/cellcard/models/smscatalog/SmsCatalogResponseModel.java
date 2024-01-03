package kh.com.cellcard.models.smscatalog;

import com.google.gson.annotations.SerializedName;
import kh.com.cellcard.models.base.microservice.MicroserviceResponseBaseModel;
import kh.com.cellcard.models.base.microservice.MicroserviceResponseData;
import lombok.Getter;
import lombok.Setter;

public class SmsCatalogResponseModel extends MicroserviceResponseBaseModel<SmsCatalogResponseModel.SmsResponseData> {

    public static class SmsResponseData extends MicroserviceResponseData {

        @SerializedName("data_info")
        public DataInfo dataInfo;

        @Setter
        @Getter
        public static class DataInfo {
            @SerializedName("id")
            public String id;

            @SerializedName("service_name")
            public String serviceName;

            @SerializedName("originator")
            public String originator;

            @SerializedName("code")
            public String code;

            @SerializedName("message")
            public String message;

            @SerializedName("description")
            public String description;

            @SerializedName("language")
            public String language;
        }
    }
}