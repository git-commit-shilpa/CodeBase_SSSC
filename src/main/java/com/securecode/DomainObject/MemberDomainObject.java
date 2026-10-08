package com.securecode.DomainObject;

import java.util.ArrayList;
import com.securecode.DataObject.MemberDataObject;
import com.securecode.restService.Request.RegisterMemberRequest;

public class MemberDomainObject {

	private int memberid;
	private String username;
	private String fullname;
	private String password;

    public int getMemberId() {
        return memberid;
    }
    public String getUsername() {
        return username;
    }
    public String getFullname() {
        return fullname;
    }
    public String getPassword() {
        return password;
    }


	public MemberDomainObject(MemberDataObject data) {
		
        try{
            this.memberid = data.memberid;
            this.username = data.username;
            this.fullname = data.fullname;
            this.password = data.password;

        }
        catch (Exception ex) {

        }
	}
	
	public static ArrayList<MemberDomainObject> MapList(ArrayList<MemberDataObject> dataList) {
		ArrayList<MemberDomainObject> domainObjectList = new ArrayList<MemberDomainObject>();
		
		for (int i=0; i<dataList.size(); i++) {
			MemberDomainObject domain = new MemberDomainObject(dataList.get(i));
			domainObjectList.add(domain);
		}

		return domainObjectList;
	}

	public MemberDomainObject(RegisterMemberRequest request) {
        try{

            this.username = request.getUsername();
            this.password = request.getPassword();
        }
        catch (Exception ex) {

        }
	}

}