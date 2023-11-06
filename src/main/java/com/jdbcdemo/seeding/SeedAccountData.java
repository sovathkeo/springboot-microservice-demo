package com.jdbcdemo.seeding;

import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.repositories.accounts.AccountRepository;

import java.util.ArrayList;

public class SeedAccountData {

    public static void SeedAccount(AccountRepository accountRepo) {
        for (var acc : GetAccountList() ) {
            if (!accountRepo.existsByName(acc.getName()) ) {
                accountRepo.save(acc);
            }
        }
    }

    private static ArrayList<Account> GetAccountList() {
        var accounts = new ArrayList<Account>();

        accounts.add(new Account("name-1",1));
        accounts.add(new Account("name-2",2));
        accounts.add(new Account("name-3",3));

        return accounts;
    }
}
