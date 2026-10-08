package com.securecode.Model;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.securecode.DataAccess.MemberDataAccess;
import com.securecode.DataObject.MemberDataObject;
import com.securecode.DomainObject.MemberDomainObject;
import com.securecode.restService.Alert;


@Service
public class MemberModel {

    private final MemberDataAccess memberDataAccess;

    public MemberModel(MemberDataAccess memberDataAccess) {
        this.memberDataAccess = memberDataAccess;
    }
   

	public MemberDomainObject GetMemberById(Alert alert, int id) {
		MemberDataObject memberData = this.memberDataAccess.getMemberById(id);
		MemberDomainObject memberDomain = new MemberDomainObject(memberData);
		return memberDomain;
	}

	public ArrayList<MemberDomainObject> GetMembersByIds(Alert alert, Set<Integer> ids) {
		ArrayList<MemberDataObject> memberDataList = this.memberDataAccess.getMembersByIds(ids);
		ArrayList<MemberDomainObject> memberDomainList = MemberDomainObject.MapList(memberDataList);
		return memberDomainList;
	}

	public ArrayList<MemberDomainObject> GetAllMembers(Alert alert) {
		ArrayList<MemberDataObject> memberDataList = this.memberDataAccess.getAllMembers();
		ArrayList<MemberDomainObject> memberDomainList = MemberDomainObject.MapList(memberDataList);
		return memberDomainList;
	}


	public MemberDomainObject CheckLogin(Alert alert, String username, String password) {

        if (!validate(alert, username, password)) {
            return null;
        }

		MemberDataObject memberData = this.memberDataAccess.getMemberByCredentials(username, password);
		if (memberData == null) 
            return null;
        
        MemberDomainObject memberDomain = new MemberDomainObject(memberData);
		return memberDomain;
	}



	private boolean validate(Alert alert, String username, String password) {

		boolean valid = true;
		return valid;
	}



}