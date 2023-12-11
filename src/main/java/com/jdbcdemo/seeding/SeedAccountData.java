package com.jdbcdemo.seeding;

import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.repositories.accounts.AccountRepository;
import com.jdbcdemo.repositories.accounts.AccountRepositoryImpl;
import com.jdbcdemo.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class SeedAccountData {

    @Autowired
    AccountService accountService;


    //@EventListener
    public void seed( ContextRefreshedEvent event ) {
        seedAccount();
    }

    private void seedAccount() {
        for (var acc : getAccountList() ) {
            if (!accountService.isExistByName(acc.getName()) ) {
                accountService.CreateAccount(acc);
            }
        }
    }

    private static ArrayList<Account> getAccountList() {
        var accounts = new ArrayList<Account>();

        accounts.add(new Account("name-1",1));
        accounts.add(new Account("name-2",2));
        accounts.add(new Account("name-3",3));

        return accounts;
    }
}
