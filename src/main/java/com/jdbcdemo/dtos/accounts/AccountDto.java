package com.jdbcdemo.dtos.accounts;

import com.jdbcdemo.dtos.base.AResponseBase;


public class AccountDto extends AResponseBase {

    public AccountDto(){}

    public AccountDto(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long id;

    public String name;
    public int age;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge( int age ) {
        this.age = age;
    }
}
