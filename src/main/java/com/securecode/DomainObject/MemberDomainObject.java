
package com.securecode.DomainObject;

import java.util.ArrayList;

import com.securecode.DataObject.MemberDataObject;
import com.securecode.restService.Request.RegisterMemberRequest;

public class MemberDomainObject {

    private int memberid;

    // Changed String fields to Domain Primitives
    private UsernameDomainPrimitive username;
    private String fullname;
    private PasswordDomainPrimitive password;

    public int getMemberId() {
        return memberid;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password == null ? null : password.getValue();
    }

    public MemberDomainObject(MemberDataObject data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Member data cannot be null"
            );
        }

        this.memberid = data.memberid;
        this.username =
            new UsernameDomainPrimitive(data.username);
        this.fullname = data.fullname;

        // Some database queries do not retrieve passwords.
        this.password = data.password == null
            ? null
            : new PasswordDomainPrimitive(data.password);
    }

    public static ArrayList<MemberDomainObject> MapList(
            ArrayList<MemberDataObject> dataList) {

        ArrayList<MemberDomainObject> domainObjectList =
            new ArrayList<>();

        for (MemberDataObject data : dataList) {
            domainObjectList.add(
                new MemberDomainObject(data)
            );
        }

        return domainObjectList;
    }

    public MemberDomainObject(RegisterMemberRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(
                "Registration request cannot be null"
            );
        }

        this.username =
            new UsernameDomainPrimitive(request.getUsername());

        this.password =
            new PasswordDomainPrimitive(request.getPassword());
    }
}
