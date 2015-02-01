package com.home.epiandroid;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties({"class", "user"})
public class History implements Serializable {
	private String title;
	private String content;
	private String date;
	private String id;
	private String visible;
	private String id_activite;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getId_activite() {
		return id_activite;
	}
	public void setId_activite(String id_activite) {
		this.id_activite = id_activite;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
