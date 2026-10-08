package com.securecode.DataObject;

public class MessageDataObject {

	public int messageId;
	public int authorId;
	public String message;


	public MessageDataObject (int messageId, int authorId, String message) {
		this.messageId = messageId;
		this.authorId = authorId;
		this.message = message;
	}
}