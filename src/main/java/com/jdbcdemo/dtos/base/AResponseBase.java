package com.jdbcdemo.dtos.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AResponseBase {


    @JsonIgnore
    private final boolean succeeded = true;

    public AResponseBase() {
        this.other = null;
    }

    public AResponseBase(String other) {
        this.other = other;
    }

    public static AResponseBase success(String other) {
        return new AResponseBase(other);
    }

    public static AResponseBase success(boolean bool) {
        return new AResponseBase(String.valueOf(bool));
    }

    public String other;
}
