package com.securecode.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.securecode.DataObject.MemberDataObject;

public class MemberMapper implements RowMapper<MemberDataObject> {


    public MemberDataObject mapRow(ResultSet rs, int rowNum) throws SQLException {

        int memberId = rs.getInt("memberid");
        String username = rs.getString("username");
        String fullname = rs.getString("fullname");

        MemberDataObject message = new MemberDataObject(memberId, username, fullname);
        return message;
    }
}