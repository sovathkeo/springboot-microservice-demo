package com.jdbcdemo.dtos.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AResponseBase {


    @JsonIgnore
    private final boolean succeeded = true;

    public AResponseBase() {
    }

    public static AResponseBase success() {
        return new AResponseBase();
    }
}
