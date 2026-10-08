package com.securecode.restService.Response;

import java.util.ArrayList;

import com.securecode.restService.Alert;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	private boolean success;
	private ArrayList<String> errormessages;
	private ArrayList<String> infomessages;
	private Object data;

	public Response (Alert alert, Object data) {
		if (alert.getErrors().size() > 0) {
			this.success = false;
			this.errormessages = alert.getErrors();
			this.infomessages = alert.getInfos();
			this.data = data;
		} else {
			this.success = true;
			this.errormessages = alert.getErrors();
			this.infomessages = alert.getInfos();
			this.data = data;
		}
	}


	@JsonCreator
	public Response(boolean success, ArrayList<String> errormessages, ArrayList<String> infomessages, Object data) {
		this.success = success;
		this.errormessages = errormessages;
		this.infomessages = infomessages;
		this.data = data;
	}

	@JsonProperty("success")
	public boolean getSuccess() {
		return this.success;
	}

	@JsonProperty("errormessage")
	public ArrayList<String> getErrorMessages() {
		return this.errormessages;
	}

	@JsonProperty("infomessage")
	public ArrayList<String> getInfoMessages() {
		return this.infomessages;
	}

	@JsonProperty("data")
	public Object getData() {
		return this.data;
	}

}