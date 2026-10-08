package com.securecode.restService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.securecode.Controller.MemberController;
import com.securecode.restService.Response.MemberResponse;
import com.securecode.restService.Response.Response;

@RestController
public class MemberAPIController {

    private final MemberController memberController;

    public MemberAPIController(MemberController memberController) {
        this.memberController = memberController;
    }

	@CrossOrigin(origins = "*")
	@GetMapping("/api/member")
	public ResponseEntity<Response> getMembers() {
		Alert alert = new Alert();
		List<MemberResponse> members = this.memberController.getAllMembers(alert);
		
		Response response = new Response(alert, members);
		if (response.getSuccess())
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/member/{id}")
	public ResponseEntity<Response> getMember(@PathVariable int id) {
		Alert alert = new Alert();
		MemberResponse member = this.memberController.getMemberById(alert, id);

		Response response = new Response(alert, member);
		if (response.getSuccess())
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}


}