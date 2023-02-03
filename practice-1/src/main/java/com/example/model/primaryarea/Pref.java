package com.example.model.primaryarea;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Pref {
	@XmlAttribute
	protected String title;
	@XmlElement
	protected Warn warn;
	@XmlElement
	protected List<City> city;
}
