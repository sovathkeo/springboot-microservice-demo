package kh.com.cellcard.models.billing.response;

import com.google.gson.annotations.SerializedName;

public class BillingResponseModel {

    @SerializedName("BillingSystem")
    public Data billingSystem;

    public static class Data{

        @SerializedName("request_id")
        public String requestId;
        @SerializedName("request_timestamp")
        public String requestTimestamp;

        @SerializedName("response_timestamp")
        public String responseTimestamp;

        @SerializedName("action")
        public String action;

        @SerializedName("source")
        public String source;

        @SerializedName("result_code")
        public String resultCode;

        @SerializedName("result_desc")
        public String resultDesc;

        @SerializedName("response")
        public Response response;
    }

    public static class Response {

        @SerializedName("dataset")
        public Dataset dataset;
    }
    public static class Dataset {
        @SerializedName("recordset")
        public Record[] recordset;
    }

    public static class Record {

        @SerializedName("profile_id")
        public String profileId;

        @SerializedName("profile_type")
        public String profileType;

        @SerializedName("profile_type_value")
        public String profileTypeValue;

        @SerializedName("date_of_birth")
        public String dateOfBirth;

        @SerializedName("subscriber_category")
        public String subscriberCategory;

        @SerializedName("first_name")
        public String firstName;

        @SerializedName("last_name")
        public String lastName;

        @SerializedName("gender")
        public String gender;
        @SerializedName("language_id")
        public String languageId;

        @SerializedName("language_id_value")
        public String languageIdValue;

        @SerializedName("subscriber_sub_category")
        public String subscriberSubCategory;

    }
}
