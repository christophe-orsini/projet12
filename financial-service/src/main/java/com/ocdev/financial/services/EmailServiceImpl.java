package com.ocdev.financial.services;

import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService
{
	@Value("${spring.mail.host}")
	private String _host="";
	
	@Value("${spring.mail.port}")
	private int _port;
	
	@Value("${spring.mail.username}")
	private String _username="";
	
	@Value("${spring.mail.password}")
	private String _emailPassword="";
	
	@Override
    public void sendEmailHtml(String to, String content, String subject, String from) throws MessagingException
	{
		JavaMailSender emailSender = getJavaMailSender();
				
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setText(content, true);
        helper.setSubject(subject);
        helper.setFrom(from);
        emailSender.send(message);
    }
	
	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(_host);
		mailSenderImpl.setPort(_port);
		mailSenderImpl.setUsername(_username);
		mailSenderImpl.setPassword(_emailPassword);
		return mailSenderImpl;
	}
}
