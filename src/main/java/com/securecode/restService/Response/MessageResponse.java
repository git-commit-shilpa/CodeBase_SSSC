package com.securecode.restService.Response;

import java.util.ArrayList;
import com.securecode.DomainObject.MessageDomainObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MessageResponse {

	private final int messageId;
	private final int authorId;
	private final String authorName;
	private final String message;


	@JsonCreator
	public MessageResponse(int messageId, int authorId, String authorName, String message) {
		this.messageId = messageId;
		this.authorId = authorId;
		this.authorName = authorName;
		this.message = message;
	}

	public MessageResponse(MessageDomainObject domainObject) {
		this.messageId = domainObject.messageId;
		this.authorId = domainObject.authorId;
		if (domainObject.member != null)
			this.authorName = domainObject.member.getFullname();
		else 
			this.authorName = "";
		this.message = domainObject.message;
	}


	public static ArrayList<MessageResponse> MapList(ArrayList<MessageDomainObject> domainList) {
		ArrayList<MessageResponse> responseList = new ArrayList<MessageResponse>();
		
		for (int i=0; i<domainList.size(); i++) {
			MessageResponse response = new MessageResponse(domainList.get(i));
			responseList.add(response);
		}

		return responseList;
	}

	@JsonProperty("messageid")
	public int getMessageId() {
		return messageId;
	}

	@JsonProperty("authorid")
	public int getAuthorId() {
		return authorId;
	}
	
	@JsonProperty("authorname")
	public String getAuthorName() {
		return authorName;
	}
	
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

}