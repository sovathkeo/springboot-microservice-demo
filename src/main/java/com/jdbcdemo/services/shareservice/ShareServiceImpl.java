package com.jdbcdemo.services.shareservice;

import com.jdbcdemo.common.shareobject.ShareObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl {

    @Autowired
    private HttpServletRequest request;

    public void setObject( ShareObject obj) {
        request.setAttribute("shared:" + obj.getClass().getName(), obj);
    }

    public ShareObject getObject(Class<? extends ShareObject> obj){
        return (ShareObject) request.getAttribute("shared:" + obj.getName());
    }
}
