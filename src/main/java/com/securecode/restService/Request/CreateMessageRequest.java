package com.securecode.restService.Request;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMessageRequest {

	private final int authorId;
	private final String message;

	@JsonCreator
	public CreateMessageRequest(@JsonProperty("authorid") int authorId, @JsonProperty("message") String message) {
		this.authorId = authorId;
		this.message = message;
	}

	public int getAuthorId() {
		return authorId;
	}

	public String getMessage() {
		return message;
	}

}