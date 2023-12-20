package kh.com.cellcard.services.notification;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    public SmsNotification sms;
}
