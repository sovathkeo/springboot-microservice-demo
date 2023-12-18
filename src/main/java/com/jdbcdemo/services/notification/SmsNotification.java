package com.jdbcdemo.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.common.configurations.appsetting.ApplicationConfiguration;
import com.jdbcdemo.common.wrapper.WebClientWrapper;
import com.jdbcdemo.models.notification.sms.SmsNotificationRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmsNotification {
    @Autowired
    private WebClientWrapper webClientWrapper;

    @Autowired
    private ApplicationConfiguration appSetting;

    @Autowired
    private ObjectMapper mapper;

    public void push(SmsNotificationRequestModel request) {
        var baseUrl = appSetting.microservices.smsService.baseUrl;
        final String url = baseUrl.formatted(request.accountId);
        var payload = new HashMap<String, String>(){
            { put("originator", request.originator); }
            { put("message", request.message); }
            { put("request_id", request.requestId); }
        };
        String body;
        try {
            body = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        webClientWrapper.postJsonAsync(url, body);
    }
}
