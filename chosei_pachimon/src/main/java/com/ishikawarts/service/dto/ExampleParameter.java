package com.ishikawarts.service.dto;

import com.ishikawarts.foundation.service.dto.IParameter;

import lombok.Builder;

@Builder
public record ExampleParameter(String username, String eventId) implements IParameter {

}
