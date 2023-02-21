package com.ishikawarts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishikawarts.dao.generated.entity.TEvent;
import com.ishikawarts.dao.generated.mapper.TEventMapper;
import com.ishikawarts.foundation.exception.ApplicationException;
import com.ishikawarts.foundation.service.AbstractService;
import com.ishikawarts.foundation.service.dto.ResultType;
import com.ishikawarts.service.ExampleService;
import com.ishikawarts.service.dto.Event;
import com.ishikawarts.service.dto.ExampleParameter;
import com.ishikawarts.service.dto.ExampleResult;

@Service
public class ExampleServiceImpl extends AbstractService<ExampleParameter, ExampleResult> implements ExampleService {

	private final TEventMapper tEventMapper;

	@Autowired
	public ExampleServiceImpl(TEventMapper tEventMapper) {
		this.tEventMapper = tEventMapper;
	}

	@Override
	protected ExampleResult _execute(ExampleParameter parameter) throws ApplicationException {

		Event event = getEvent(parameter.eventId());

		return ExampleResult.builder()
				.resultType(ResultType.SUCCESS)
				.event(event)
				.build();
	}

	private Event getEvent(String eventId) throws ApplicationException {
		TEvent entity = this.tEventMapper.selectByPrimaryKey(eventId);

		if (entity == null) {
			throw new ApplicationException(String.format("イベントの情報が取得できませんでした eventId=%s", eventId));
		}

		return Event.builder()
				.eventId(entity.getEventId())
				.title(entity.getTitle())
				.description(entity.getDescription())
				.build();

	}

	@Override
	protected ExampleResult createRseultWhenProcessIsAborted(ApplicationException e) {

		return ExampleResult.builder()
				.resultType(ResultType.ERROR)
				.resultMessage(e.getMessage())
				.build();
	}

}
