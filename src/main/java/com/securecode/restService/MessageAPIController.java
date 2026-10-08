package com.securecode.restService;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.securecode.Controller.MessageController;
import com.securecode.restService.Request.CreateMessageRequest;
import com.securecode.restService.Request.EditMessageRequest;
import com.securecode.restService.Response.MessageResponse;
import com.securecode.restService.Response.Response;


@RestController
public class MessageAPIController {

	
    private final MessageController messageController;

    public MessageAPIController(MessageController messageController) {
        this.messageController = messageController;
    }


	@CrossOrigin(origins = "*")
	@GetMapping("/api/message")
	public ResponseEntity<Response> getMessages() {
		Alert alert = new Alert();
		List<MessageResponse> messages = this.messageController.getAllMessages(alert);

		Response response = new Response(alert, messages);
		if (response.getSuccess())
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		

	}


	@CrossOrigin(origins = "*")
	@PostMapping("/api/message/{id}")
	public ResponseEntity<Response> updateMessage(@RequestBody EditMessageRequest request, @PathVariable int id) {
		Alert alert = new Alert();
		
		MessageResponse message = this.messageController.updateMessage(alert, request, id);

		Response response = new Response(alert, message);
		if (response.getSuccess())
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/api/message")
	public ResponseEntity<Response> createMessage(@RequestBody CreateMessageRequest request) {
		Alert alert = new Alert();
		MessageResponse message = this.messageController.createMessage(alert, request);

		Response response = new Response(alert, message);
		if (response.getSuccess())
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}


}