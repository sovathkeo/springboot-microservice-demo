package com.jdbcdemo.controllers.account;

import an.awesome.pipelinr.Pipeline;
import com.jdbcdemo.common.exceptions.ApplicationException;
import com.jdbcdemo.controllers.base.BaseController;
import com.jdbcdemo.dtos.base.AResponseBase;
import com.jdbcdemo.dtos.responses.ResponseImpl;
import com.jdbcdemo.features.accounts.commands.createaccount.CreateAccountCommand;
import com.jdbcdemo.features.accounts.commands.updateaccount.UpdateAccountCommand;
import com.jdbcdemo.features.accounts.queryies.GetAccountInfoQuery;
import com.jdbcdemo.services.tracing.CorrelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "accounts", description = "provide account operations")
public class AccountController extends BaseController {

    @Autowired
    private Pipeline pipeline;

    @Autowired
    private CorrelationService correlationService;

    @Operation(description = "get account info by accountId",summary = "get account info by accountId")
    @ApiResponse(responseCode = "200", description = "account created successfully")
    @GetMapping("/accounts/{id}")
    public ResponseImpl<AResponseBase> GetAccountInfo(@PathVariable long id) throws ApplicationException {
        return mediate(new GetAccountInfoQuery(id));
    }

    @GetMapping("/accounts/{id}/async")
    public ResponseImpl<AResponseBase> GetAccountInfoAsync(@PathVariable long id) throws ApplicationException {

        return mediate(new GetAccountInfoQuery(id));
    }

    @PostMapping("/accounts")
    public ResponseImpl<AResponseBase> CreateAccount(@RequestBody CreateAccountCommand command) {
        return mediate(command);
    }

    @PutMapping("/accounts")
    public ResponseImpl<AResponseBase> updateAccount( @Valid UpdateAccountCommand command ) {
        return mediate(command);
    }
}
