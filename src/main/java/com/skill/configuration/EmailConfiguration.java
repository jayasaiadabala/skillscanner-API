package com.skill.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration {
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	public boolean sendMail(String to,String subject,String text) {
		try {
			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setSubject(subject);
			smm.setText(text);
			smm.setTo(to);
			smm.setFrom(from);
			javaMailSender.send(smm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}