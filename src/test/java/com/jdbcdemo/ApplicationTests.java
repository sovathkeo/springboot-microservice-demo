package com.jdbcdemo;

import com.jdbcdemo.common.exceptions.models.ApplicationException;
import com.jdbcdemo.services.accounts.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private AccountService accountService;

	@Test
	void contextLoads() {
	}

	@Test()
	void findAccountById() {

		var isExist = false;
		try {
			isExist = accountService.isExistById(1302);

		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertTrue(isExist);
	}

	@Test()
	void findNullAccountById() {
		long id = -1;
		var isExist = false;
		try {
			isExist = accountService.isExistById(id);

		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertFalse(isExist);
	}

}
