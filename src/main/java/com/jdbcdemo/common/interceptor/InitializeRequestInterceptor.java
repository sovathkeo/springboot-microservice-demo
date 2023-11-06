package com.jdbcdemo.common.interceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class InitializeRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler ) throws Exception {

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView ) throws Exception {


    }

    @Override
    public void afterCompletion(
        @Nullable  HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable Object handler,
        Exception ex ) throws Exception {



    }
}
