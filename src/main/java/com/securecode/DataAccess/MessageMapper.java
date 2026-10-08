package com.securecode.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.securecode.DataObject.MessageDataObject;

public class MessageMapper implements RowMapper<MessageDataObject> {


    public MessageDataObject mapRow(ResultSet rs, int rowNum) throws SQLException {

        int messageId = rs.getInt("messageid");
        int authorId = rs.getInt("authorid");
        String messageValue = rs.getString("message");

        MessageDataObject message = new MessageDataObject(messageId, authorId, messageValue);
        return message;
    }
}