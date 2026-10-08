package com.securecode.DomainObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.securecode.DataObject.MessageDataObject;
import com.securecode.restService.Request.CreateMessageRequest;
import com.securecode.restService.Request.EditMessageRequest;

public class MessageDomainObject {

	public int messageId;
	public int authorId;
	public String message;

	public MemberDomainObject member;

	public MessageDomainObject(MessageDataObject data) {
		this.messageId = data.messageId;
		this.authorId = data.authorId;
		this.message = data.message;

	}

	public MessageDomainObject(EditMessageRequest data) {
		if (data.getMessageId() != null)
            this.messageId = data.getMessageId();
		this.authorId = data.getAuthorId();
		this.message = data.getMessage();

	}

	public MessageDomainObject(CreateMessageRequest data) {
		this.authorId = data.getAuthorId();
		this.message = data.getMessage();

	}

	public static ArrayList<MessageDomainObject> MapList(ArrayList<MessageDataObject> dataList) {
		ArrayList<MessageDomainObject> domainObjectList = new ArrayList<MessageDomainObject>();
		
		for (int i=0; i<dataList.size(); i++) {
			MessageDomainObject domain = new MessageDomainObject(dataList.get(i));
			domainObjectList.add(domain);
		}

		return domainObjectList;
	}


}
