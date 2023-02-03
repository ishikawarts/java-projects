package com.example.model.primaryarea;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;


@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LdWeatherSource {
	@XmlElement
	protected List<Pref> pref;
	@XmlAttribute
	protected String title;
}
