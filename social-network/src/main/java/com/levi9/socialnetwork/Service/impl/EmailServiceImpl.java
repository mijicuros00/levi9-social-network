package com.levi9.socialnetwork.Service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;


	@Autowired
	private Environment env;
	
	@Async
	public void sendNotificaitionAsync(User user) throws MailException, InterruptedException {
		
		Thread.sleep(1000);
		System.out.println("Sending mail...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("New post on your group");
		mail.setText("Hi " + user.getName() + ",\n\nyou have new post on your group.");
		javaMailSender.send(mail);

		System.out.println("Email sended!");
	}
	
	
	

}
