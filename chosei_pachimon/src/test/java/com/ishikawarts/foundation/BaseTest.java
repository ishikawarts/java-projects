package com.ishikawarts.foundation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ishikawarts.configuration.TestConfig;

@SpringBootTest(classes = { TestConfig.class })
public abstract class BaseTest {

	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionDefinition definition;
	
	private TransactionStatus status;

	@BeforeEach
	void setUp() {

		this.definition = new DefaultTransactionDefinition();

		this.status = this.transactionManager.getTransaction(definition);

	}

	@AfterEach
	void tearDown() {

		this.transactionManager.rollback(status);

	}


}
