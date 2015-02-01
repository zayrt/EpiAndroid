package com.planning.epiandroid;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Planning implements Serializable {
	private String titlemodule;
	private String title;
	private String start;
	private String past;
	private String end;
	private String module_registered = null;
	private String event_registered;
	private String acti_title;
	private Room room;
	private String scolaryear;
	private String codemodule;
	private String codeinstance;
	private String codeacti;
	private String codeevent;
	private String rdv_group_registered;
	private String rdv_indiv_registered;
	private String allow_token;
	
	public class Room implements Serializable{
		String seats;
		String type;
		String code;
		
		public String getSeats() {
			return seats;
		}
		public void setSeats(String seats) {
			this.seats = seats;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}	
	}

	public String getTitlemodule() {
		return titlemodule;
	}

	public void setTitlemodule(String titlemodule) {
		this.titlemodule = titlemodule;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getPast() {
		return past;
	}

	public void setPast(String past) {
		this.past = past;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getModule_registered() {
		return module_registered;
	}

	public void setModule_registered(String module_registered) {
		this.module_registered = module_registered;
	}

	public String getEvent_registered() {
		return event_registered;
	}

	public void setEvent_registered(String event_registered) {
		this.event_registered = event_registered;
	}

	public String getActi_title() {
		return acti_title;
	}

	public void setActi_title(String acti_title) {
		this.acti_title = acti_title;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRdv_group_registered() {
		return rdv_group_registered;
	}

	public void setRdv_group_registered(String rdv_group_registered) {
		this.rdv_group_registered = rdv_group_registered;
	}

	public String getRdv_indiv_registered() {
		return rdv_indiv_registered;
	}

	public void setRdv_indiv_registered(String rdv_indiv_registered) {
		this.rdv_indiv_registered = rdv_indiv_registered;
	}

	public String getAllow_token() {
		return allow_token;
	}

	public void setAllow_token(String allow_token) {
		this.allow_token = allow_token;
	}

	public String getScolaryear() {
		return scolaryear;
	}

	public void setScolaryear(String scolaryear) {
		this.scolaryear = scolaryear;
	}

	public String getCodemodule() {
		return codemodule;
	}

	public void setCodemodule(String codemodule) {
		this.codemodule = codemodule;
	}

	public String getCodeinstance() {
		return codeinstance;
	}

	public void setCodeinstance(String codeinstance) {
		this.codeinstance = codeinstance;
	}

	public String getCodeacti() {
		return codeacti;
	}

	public void setCodeacti(String codeacti) {
		this.codeacti = codeacti;
	}

	public String getCodeevent() {
		return codeevent;
	}

	public void setCodeevent(String codeevent) {
		this.codeevent = codeevent;
	}
}
