package com.securecode.DataAccess;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.securecode.DataObject.MemberDataObject;
import com.securecode.DataObject.MessageDataObject;

@Component 
public class MessageDataAccess {


	private static String tableName = "message";
	private static String fields = "messageid, authorid, message";
	private static String idField = "messageid";


	public MessageDataAccess() {
	}


	public ArrayList<MessageDataObject> getAllMessages() {
		String sql = String.format("select %s from %s", fields, tableName);
		List<MessageDataObject> list = DBAccesser.jdbcTemplate.query(sql, new MessageMapper());

		return (ArrayList<MessageDataObject>)list;
	}

	public MessageDataObject getMessageById(int id) {

		String sql = String.format("select %s from %s where %s = %s", fields, tableName, idField, id);
		List<MessageDataObject> list = DBAccesser.jdbcTemplate.query(sql, new MessageMapper());
		
		if (list.size() < 1)
			return null;

		return list.get(0);

	}

	public MessageDataObject updateMessage(MessageDataObject data) {
		
		String sql = String.format("update %s set authorid = %s, message = '%s' where %s = %s", tableName, data.authorId, data.message, idField, data.messageId);
		DBAccesser.jdbcTemplate.update(sql);
	
		return getMessageById(data.messageId);
		
    //SAFE METHOD USING PREPARED STATEMENT
    // String sql = String.format(
    //     "update %s set authorid = ?, message = ? where %s = ?",
    //     tableName, idField
    // );

    // DBAccesser.jdbcTemplate.update(
    //     sql,
    //     data.authorId,
    //     data.message,
    //     data.messageId
    // );

    // return getMessageById(data.messageId);

	}

	public MessageDataObject createMessage(MessageDataObject data) {

		String sql = String.format("INSERT INTO %s (authorid, message) VALUES (%s,'%s');", tableName, data.authorId, data.message);

		System.out.println(sql);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Lambda function 
		int count = DBAccesser.jdbcTemplate.update(c->c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), keyHolder);
		
		if (count == 1) {
			int id = keyHolder.getKey().intValue();
			return getMessageById(id);
		}

		//Error case
		return null;

	}




}