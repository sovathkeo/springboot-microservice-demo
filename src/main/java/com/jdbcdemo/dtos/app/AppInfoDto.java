package com.jdbcdemo.dtos.app;

import com.jdbcdemo.dtos.base.AResponseBase;

public class AppInfoDto extends AResponseBase {


    public AppInfoDto(String name) {

    }

    public String name;
    public String description;
    public String version;
}
