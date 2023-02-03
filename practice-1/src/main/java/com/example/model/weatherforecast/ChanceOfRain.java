package com.example.model.weatherforecast;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChanceOfRain {
	@JsonProperty("T00_06")
	private String t0006;
	@JsonProperty("T06_12")
	private String t0612;
	@JsonProperty("T12_18")
	private String t1218;
	@JsonProperty("T18_24")
	private String t1824;

}
