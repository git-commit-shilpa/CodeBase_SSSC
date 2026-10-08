package com.securecode.restService.Request;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EditMessageRequest {

	private final Integer messageId;
	private final Integer authorId;
	private final String message;

	@JsonCreator
	public EditMessageRequest(@JsonProperty("messageid") Integer messageId, @JsonProperty("authorid") Integer authorId, @JsonProperty("message") String message) {
		this.messageId = messageId;
		this.authorId = authorId;
		this.message = message;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public String getMessage() {
		return message;
	}

}