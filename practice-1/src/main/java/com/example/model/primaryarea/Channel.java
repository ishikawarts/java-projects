package com.example.model.primaryarea;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Channel {
	@XmlElement
	protected String title;
	@XmlElement
	protected String link;
	@XmlElement
	protected String description;
	@XmlElement
	protected String lastBuildDate;
	@XmlElement
	protected String author;
	@XmlElement
	protected String language;
	@XmlElement
	protected String category;
	@XmlElement
	protected String generator;
	@XmlElement
	protected String copyright;
	@XmlElement
	protected Image image;
	@XmlElement(namespace = "http://weather.livedoor.com/%5C/ns/rss/2.0", name = "provider")
	protected LdWeatherProvider ldWeatherProvider;
	@XmlElement(namespace = "http://weather.livedoor.com/%5C/ns/rss/2.0", name = "source") 
	protected LdWeatherSource ldWeatherSource;
	@XmlElement
	protected Item item;
}
