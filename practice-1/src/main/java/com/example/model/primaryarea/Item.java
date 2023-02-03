package com.example.model.primaryarea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Item {
	@XmlElement
	protected String title;
	@XmlElement
	protected String link;
	@XmlElement
	protected String category;
	@XmlElement
	protected String description;
	@XmlElement
	protected Image image;
	@XmlElement
	protected String pubDate;
}
