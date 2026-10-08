package com.securecode.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.securecode.DataObject.MemberDataObject;
import com.securecode.DataObject.MessageDataObject;
import com.securecode.DataAccess.MemberDataAccess;
import com.securecode.DataAccess.MessageDataAccess;
import com.securecode.DomainObject.MemberDomainObject;
import com.securecode.DomainObject.MessageDomainObject;
import com.securecode.restService.Alert;


@Service
public class MessageModel {
	
    private final MemberModel memberModel;
    private final MessageDataAccess messageDataAccess;

    public MessageModel(MemberModel memberModel, MessageDataAccess messageDataAccess) {
        this.memberModel = memberModel;
        this.messageDataAccess = messageDataAccess;
    }

	public ArrayList<MessageDomainObject> GetAllMessages(Alert alert) {
		ArrayList<MessageDataObject> messageDataList = this.messageDataAccess.getAllMessages();
		ArrayList<MessageDomainObject> messageDomainList = MessageDomainObject.MapList(messageDataList);

		HashSet<Integer> memberIdSet = new HashSet<Integer>();

		for (MessageDomainObject messageDO : messageDomainList) {
			memberIdSet.add(messageDO.authorId);
		}

		ArrayList<MemberDomainObject> members = this.memberModel.GetMembersByIds(alert, memberIdSet);

		HashMap<Integer, MemberDomainObject> memberMap = new HashMap<Integer, MemberDomainObject>();
		for (MemberDomainObject member : members) {
			memberMap.put(member.getMemberId(), member);
		}

		for (MessageDomainObject messageDO : messageDomainList) {
			messageDO.member = memberMap.get(messageDO.authorId);
		}


		return messageDomainList;
	}


	public MessageDomainObject GetMessageById(Alert alert, int id) {
		MessageDataObject messageData = this.messageDataAccess.getMessageById(id);
		MessageDomainObject messageDomain = new MessageDomainObject(messageData);
		return messageDomain;
	}


	public MessageDomainObject UpdateMessage(Alert alert, MessageDomainObject domainMessageToUpdate) {
		
		if (!validate(alert, domainMessageToUpdate))
			return null;
		
		MessageDataObject newMessage = new MessageDataObject(domainMessageToUpdate.messageId, domainMessageToUpdate.authorId, domainMessageToUpdate.message);
		MessageDataObject updatedMessage = this.messageDataAccess.updateMessage(newMessage);

        return new MessageDomainObject(updatedMessage);
	}

	public MessageDomainObject CreateMessage(Alert alert, MessageDomainObject domainMessageToCreate) {

		if (!validate(alert, domainMessageToCreate))
			return null;

		if(domainMessageToCreate.authorId == 0){
			alert.addAlert(Alert.Type.Error, "Must include all parameters.");
			return null;
		}

		//check if author is registered


		MessageDataObject newMessage = new MessageDataObject(0, domainMessageToCreate.authorId, domainMessageToCreate.message);
		MessageDataObject createdMessage = this.messageDataAccess.createMessage(newMessage);

        return new MessageDomainObject(createdMessage);
	}



	private boolean validate(Alert alert, MessageDomainObject messageDomainObject) {
		boolean valid = true;

		MemberDomainObject member = this.memberModel.GetMemberById(alert, messageDomainObject.authorId);
		if (member == null) {
			alert.addAlert(Alert.Type.Error, "AuthorId does not exist");
			valid = false;
		}

		return valid;

	}
}