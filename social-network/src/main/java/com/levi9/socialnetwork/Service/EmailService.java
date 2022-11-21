package com.levi9.socialnetwork.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;


import com.levi9.socialnetwork.Model.User;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



public interface EmailService  {


	@Async
	public void sendNotificaitionAsync(User user) throws MailException, InterruptedException;
	
	
}
