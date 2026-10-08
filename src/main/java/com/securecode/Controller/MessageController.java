package com.securecode.Controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

import com.securecode.Logging;
import com.securecode.Logging.Level;
import com.securecode.DomainObject.MessageDomainObject;
import com.securecode.Model.MessageModel;
import com.securecode.restService.Alert;
import com.securecode.restService.Request.CreateMessageRequest;
import com.securecode.restService.Request.EditMessageRequest;
import com.securecode.restService.Response.MessageResponse;

@Controller 
public class MessageController {
	//The MessageController will convert the request from the REST Request and the Domain Model.
	//Then it will call the Model or Service, and convert the response from the Domain model
	//to the REST Response.
	
    private final MessageModel messageModel;

    public MessageController(MessageModel messageModel) {
        this.messageModel = messageModel;
    }
    
    public ArrayList<MessageResponse> getAllMessages(Alert alert) {
		try {
			ArrayList<MessageDomainObject> domain = this.messageModel.GetAllMessages(alert);
			ArrayList<MessageResponse> response = MessageResponse.MapList(domain);
			return response;
		} catch (Exception ex) {

            Logging.Log(Level.ERROR, "Error in GetAllMessages()");
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());

			alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}
	}

	public MessageResponse updateMessage(Alert alert, EditMessageRequest request, int id) {
		try {
			MessageDomainObject updateMessage = new MessageDomainObject(request);
			updateMessage.messageId = id;

			MessageDomainObject domain = this.messageModel.UpdateMessage(alert, updateMessage);
			MessageResponse response = new MessageResponse(domain);
			return response;
		} catch (Exception ex) {
            Logging.Log(Level.ERROR, String.format("Error for UpdateMessage on request %s and id %s", request.toString(), id));
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());


			alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}

	}


	public MessageResponse createMessage(Alert alert, CreateMessageRequest request) {
		try {
			MessageDomainObject newMessage = new MessageDomainObject(request);

			MessageDomainObject domain = this.messageModel.CreateMessage(alert, newMessage);
			MessageResponse response = new MessageResponse(domain);
			return response;
		} catch (Exception ex) {

            Logging.Log(Level.ERROR, String.format("Error in CreateMessage with request %s", request.toString()));
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());

			alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}

	}
}
