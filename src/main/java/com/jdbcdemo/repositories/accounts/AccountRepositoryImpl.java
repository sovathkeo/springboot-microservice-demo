package com.jdbcdemo.repositories.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbcdemo.domain.entities.Account;
import com.jdbcdemo.dtos.accounts.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<AccountDto> getById( long accountId) {

        var sql = "select id, name, age from account with(nolock) where id = ?";

        try {
            var account =  jdbcTemplate.queryForObject(
                    sql,
                    new Object[] { accountId },
                    new int[] {Types.BIGINT},
                    (rs, rowNum) -> Optional.of(new Account(rs.getLong("id"),rs.getString("name"), rs.getInt("age"))));

            return Optional.of(modelMapper.map(account, AccountDto.class));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Account> getAccountByProcedure(long accountId) {

        var request = new HashMap<String, Object>() {
            { put("accountId", accountId); }
        };

        MapSqlParameterSource inputParams = null;
        try {
            inputParams = new MapSqlParameterSource()
                    .addValue("in_request", mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PROC_GET_ACCOUNT_INFO_FILTER");

        var result = simpleJdbcCall.execute(inputParams);

        if ( result.isEmpty() || result.get("out_result") == null || result.get("out_result") == "{}") {
            return Optional.empty();
        }

        Account acc = null;
        try {
            acc = mapper.readValue(result.get("out_result").toString(), Account.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(acc);
    }
}
