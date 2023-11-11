package com.jdbcdemo.services.accounts;

import com.jdbcdemo.common.exceptions.ApplicationException;
import com.jdbcdemo.common.exceptions.ResourceNotFoundException;
import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.features.accounts.commands.updateaccount.UpdateAccountCommand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface AccountService {

    boolean isExistById(long accountId) throws ApplicationException;

    boolean isExistByName(String accountName);

    @Async
    CompletableFuture<AccountDto> GetAccountInfo(long id) throws ApplicationException;

    AccountDto CreateAccount(Account account);

    AccountDto updateAccount( UpdateAccountCommand command ) throws ApplicationException;
}
