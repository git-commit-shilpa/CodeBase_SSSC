package com.securecode.Controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

import com.securecode.DomainObject.MemberDomainObject;
import com.securecode.Model.MemberModel;
import com.securecode.restService.Alert;
import com.securecode.restService.Response.MemberResponse;

@Controller 
public class MemberController {
	//The MemberController will convert the request from the REST Request and the Domain Model.
	//Then it will call the Model or Service, and convert the response from the Domain model
	//to the REST Response.
	
    private final MemberModel memberModel;

    public MemberController(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

	public ArrayList<MemberResponse> getAllMembers(Alert alert) {
		try{
			ArrayList<MemberDomainObject> domain = this.memberModel.GetAllMembers(alert);
			ArrayList<MemberResponse> response = MemberResponse.MapList(domain);
			return response;				
		} catch (Exception ex) {
			alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}

	}
	public MemberResponse getMemberById(Alert alert, int memberId) {
		try {
			MemberDomainObject domain = this.memberModel.GetMemberById(alert, memberId);
			MemberResponse response = new MemberResponse(domain);
			return response;
		} catch (Exception ex) {
			alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}

	}
}