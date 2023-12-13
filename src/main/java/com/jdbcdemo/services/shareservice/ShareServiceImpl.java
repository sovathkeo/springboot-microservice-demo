package com.jdbcdemo.services.shareservice;

import com.jdbcdemo.common.shareobject.ShareObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareServiceImpl {

    private static final String SHARE_OBJECT_PREFIX = "shared:";

    @Autowired
    private HttpServletRequest request;

    public void setObject( ShareObject obj) {
        request.setAttribute(SHARE_OBJECT_PREFIX + obj.getClass().getName(), obj);
    }

    public @NotNull Optional<ShareObject> getObject(@NotNull @org.jetbrains.annotations.NotNull Class<? extends ShareObject> obj){
        var o = request.getAttribute(SHARE_OBJECT_PREFIX + obj.getName());
        if (o == null) {
            return Optional.empty();
        }
        return Optional.of((ShareObject) o);
    }
}
