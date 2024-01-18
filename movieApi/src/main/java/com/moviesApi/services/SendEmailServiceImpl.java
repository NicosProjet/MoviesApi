package com.moviesApi.services;



import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.moviesApi.dto.UserDto;
import com.moviesApi.tools.JwtTokenUtil;
import com.moviesApi.tools.TokenSaver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class SendEmailServiceImpl implements SendEmailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${frontapp.url}")
	private String frontAppUrl;
	

    public void sendEmail(String to, String subject, String body) {
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        System.out.println(message);
        javaMailSender.send(message);
    }

	@Override
	public void sendEmailForResetPassword(UserDto uDto) throws Exception {
		// TODO Auto-generated method stub
		//générer un token
		Map<String, Object> infoUser = new HashMap<String, Object>();
		infoUser.put("user_id", uDto.getId());
		infoUser.put("user_fullName", uDto.getFirstName() + " " + uDto.getLastName());
		String token = jwtTokenUtil.doGenerateToken(infoUser , uDto.getEmail());
		TokenSaver.tokensByEmail.put(uDto.getEmail(), token);
		
		String resetLink= "<a href=\""+frontAppUrl+ "/#/fr/reset-password?token="+token+"\">Réinitialiser</a>";
		MimeMessage msg = javaMailSender.createMimeMessage();
		msg.setRecipients(Message.RecipientType.TO, uDto.getEmail());
		msg.setSubject("Réinitialisation du mot de passe");
		
		msg.setText("Bonjour " + uDto.getFirstName() + ","
				+"<br /> Ce message vous a été envoyé car vous avez oublié votre mot de passe.<br />"
				+ "Pour le réinitialiser, cliquez sur ce lien : " + resetLink, "UTF-8");
		javaMailSender.send(msg);
	}
	
}
