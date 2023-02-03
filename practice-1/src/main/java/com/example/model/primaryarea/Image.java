package com.example.model.primaryarea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Image {
	@XmlElement
	protected String title;
	@XmlElement
	protected String link;
	@XmlElement
	protected String url;
	@XmlElement
	protected String width;
	@XmlElement
	protected String height;

}
