package com.jdbcdemo.services.accounts;

import com.jdbcdemo.common.exceptions.ApplicationException;
import com.jdbcdemo.common.exceptions.ResourceNotFoundException;
import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.accounts.AccountDto;
import com.jdbcdemo.features.accounts.commands.updateaccount.UpdateAccountCommand;
import com.jdbcdemo.repositories.accounts.AccountRepository;
import com.jdbcdemo.repositories.accounts.AccountRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRepositoryImpl accountRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async
    public CompletableFuture<AccountDto> GetAccountInfo(long id) throws ApplicationException {

        var accRes = accountRepo.getById(id);

        if (accRes.isEmpty()) {
            throw new ResourceNotFoundException("404_01",String.format("account with id = '%s' not exist",id));
        }

        return CompletableFuture.completedFuture(accRes.get());
    }

    @Override
    public AccountDto CreateAccount(Account account) {
        var acc = accountRepository.save(account);
        return new AccountDto(acc.getId(),acc.getName(), acc.getAge());
    }

    @Override
    public AccountDto updateAccount( UpdateAccountCommand command ) throws ApplicationException {
        var existing = accountRepository.findById(command.id);
        if (existing.isEmpty()) {
            throw new ResourceNotFoundException("404_01",String.format("account with id = '%s' not exist",command.id));
        }
        var acc = existing.get();
        acc = new Account(acc.getId(), acc.getName(), acc.getAge());
        accountRepository.save(acc);
        return modelMapper.map(acc, AccountDto.class);
    }
}
