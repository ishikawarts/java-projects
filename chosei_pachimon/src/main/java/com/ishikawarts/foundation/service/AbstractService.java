package com.ishikawarts.foundation.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ishikawarts.foundation.exception.ApplicationException;
import com.ishikawarts.foundation.exception.SystemException;
import com.ishikawarts.foundation.service.dto.IParameter;
import com.ishikawarts.foundation.service.dto.IResult;


public abstract class AbstractService<P extends IParameter, R extends IResult> implements IService<P, R> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	protected LocalDateTime systemTimestamp;

	protected String username;

	@Override
	public R execute(P parameter) throws SystemException {

		try {
			logger.info("parameter=" + parameter.toString());

			begin();

			initialize(parameter);

			R result = _execute(parameter);

			commit();

			logger.info("result=" + result.toString());

			return result;

		} catch (ApplicationException e) {
			rollback();
			return createRseultWhenProcessIsAborted(e);
		} catch (Throwable t) {
			rollback();
			throw new SystemException(t);
		} finally {
			terminate();
		}
	}

	protected abstract R _execute(P parameter) throws ApplicationException;

	protected abstract R createRseultWhenProcessIsAborted(ApplicationException e);

	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	private void begin() {
		var definition = new DefaultTransactionDefinition();
		this.transactionStatus = this.transactionManager.getTransaction(definition);
	}

	private void commit() {
		Optional.of(this.transactionStatus)
				.ifPresent(status -> this.transactionManager.commit(status));
	}

	private void rollback() {
		Optional.of(this.transactionStatus)
				.ifPresent(status -> this.transactionManager.rollback(status));
	}

	private void initialize(P parameter) {

		this.systemTimestamp = LocalDateTime.now();

		this.username = parameter.username();

		this._initialize();
	}

	protected void _initialize() {

	}

	private void terminate() {
		this._terminate();
	}

	private void _terminate() {

	}

}
