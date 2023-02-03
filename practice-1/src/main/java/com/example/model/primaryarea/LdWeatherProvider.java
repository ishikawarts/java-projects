package com.example.model.primaryarea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LdWeatherProvider {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String link;
}
