package com.jdbcdemo.repositories.accounts;

import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.accounts.AccountDto;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    boolean existsByName(String name);
}
