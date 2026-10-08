package com.securecode.Controller;

import org.springframework.stereotype.Controller;

import com.securecode.Logging;
import com.securecode.DomainObject.MemberDomainObject;
import com.securecode.Logging.Level;
import com.securecode.Model.MemberModel;
import com.securecode.restService.Alert;
import com.securecode.restService.Response.MemberResponse;

@Controller
public class LoginController {
	//The LoginController will convert the request from the REST/Web Request and the Domain Model.
	//Then it will call the Model or Service, and convert the response from the Domain model
	//to the REST Response.
	

    private final MemberModel memberModel;

    public LoginController(MemberModel memberModel) {
        this.memberModel = memberModel;
    }


	public MemberDomainObject Login(Alert alert, String username, String password) {
		try{
            MemberDomainObject member = this.memberModel.CheckLogin(alert, username, password);
            return member;

		} catch (Exception ex) {
            Logging.Log(Level.ERROR, String.format("Error for authentication user %s, password %s", username, password));
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());

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
            Logging.Log(Level.ERROR, String.format("Error for GetMemberById on memberId %s", memberId));
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());

            alert.addAlert(Alert.Type.Error, ex.getMessage());
			return null;
		}

	}

}
