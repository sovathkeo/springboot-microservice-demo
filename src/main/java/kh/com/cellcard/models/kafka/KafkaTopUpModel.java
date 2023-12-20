package kh.com.cellcard.models.kafka;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KafkaTopUpModel {

    @SerializedName("RECEIVED_TIME")
    private String receivedTime;

    @SerializedName("ACTION")
    private String action;

    @SerializedName("ACCOUNT_ID")
    private String accountId;

    @SerializedName("TOPUP_AMOUNT")
    private String topUpAmount;
}
