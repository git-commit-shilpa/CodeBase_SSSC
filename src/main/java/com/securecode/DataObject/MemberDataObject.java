package com.securecode.DataObject;

public class MemberDataObject {

	public int memberid;
	public String username;
	public String fullname;
	public String password;

	public MemberDataObject (int memberid, String username, String fullname) {
		this.memberid = memberid;
		this.username = username;
		this.fullname = fullname;
	}

}