package com.jdbcdemo.repositories.accounts;

import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.accounts.AccountDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    boolean existsByName(String name);
}
