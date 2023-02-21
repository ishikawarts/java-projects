package com.ishikawarts.service.dto;

import lombok.Builder;

@Builder
public record Event(String eventId, String title, String description) {

}
