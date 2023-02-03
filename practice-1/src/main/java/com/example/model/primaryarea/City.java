package com.example.model.primaryarea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class City {
	@XmlAttribute
	protected String title;
	@XmlAttribute
	protected String id;
}
