package com.securecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;
import org.springframework.jdbc.core.JdbcTemplate;

import com.securecode.Controller.SessionManager;
import com.securecode.DataAccess.DBAccesser;


@SpringBootApplication
public class securecode implements ApplicationRunner {

    @Autowired
    public JdbcTemplate jdbctemplate;

	public static void main(String[] args) {
		
		System.out.println(SpringVersion.getVersion());
		System.out.println(JavaVersion.getJavaVersion());
		System.out.println("------------------------------------------");
		
	
	
	
        try{
		    SpringApplication.run(securecode.class, args);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

	}

	@Override
	public void run(ApplicationArguments arg) {

		SessionManager sessionManager = new SessionManager();

		DBAccesser dbAccesser = new DBAccesser(jdbctemplate);


	}



}
