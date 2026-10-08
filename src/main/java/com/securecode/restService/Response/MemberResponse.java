package com.securecode.restService.Response;


import java.util.ArrayList;

import com.securecode.DomainObject.MemberDomainObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MemberResponse {

	private final int memberId;
	private final String username;

	@JsonCreator
	public MemberResponse(int memberId, String username) {
		this.memberId = memberId;
		this.username = username;
	}

	public MemberResponse(MemberDomainObject domainObject) {
		this.memberId = domainObject.getMemberId();
		this.username = domainObject.getUsername();
	}
	public static ArrayList<MemberResponse> MapList(ArrayList<MemberDomainObject> domainList) {
		ArrayList<MemberResponse> responseList = new ArrayList<MemberResponse>();
		
		for (int i=0; i<domainList.size(); i++) {
			MemberResponse response = new MemberResponse(domainList.get(i));
			responseList.add(response);
		}

		return responseList;
	}

	@JsonProperty("memberid")
	public int getMemberId() {
		return memberId;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}
}