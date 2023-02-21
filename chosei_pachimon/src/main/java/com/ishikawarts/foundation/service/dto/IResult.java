package com.ishikawarts.foundation.service.dto;

import java.util.List;

public interface IResult {

	ResultType resultType();

	String resultMessage();

	List<ResultDetail> resultDetails();

	default boolean isSuccess() {
		return ResultType.SUCCESS.equals(resultType());
	}

	default boolean hasWarning() {
		return ResultType.WARNING.equals(resultType());
	}

	default boolean hasError() {
		return ResultType.WARNING.equals(resultType());
	}
}
