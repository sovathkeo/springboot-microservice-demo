package kh.com.cellcard.models.notification.sms;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SmsNotificationRequestModel {

    public String originator;

    public String accountId;
    public String message;
    public String requestId;
}
