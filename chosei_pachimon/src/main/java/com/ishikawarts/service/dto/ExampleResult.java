package com.ishikawarts.service.dto;

import java.util.List;

import com.ishikawarts.foundation.service.dto.IResult;
import com.ishikawarts.foundation.service.dto.ResultDetail;
import com.ishikawarts.foundation.service.dto.ResultType;

import lombok.Builder;

@Builder
public record ExampleResult(ResultType resultType, String resultMessage, List<ResultDetail> resultDetails, Event event) implements IResult {

} 
