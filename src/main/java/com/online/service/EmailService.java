package com.online.service;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${spring.mail.messagefrom.name}")
	private String MESSAGE_FROM_NAME;
	
	@Value("${base.url}")
	private String BASE_URL;

	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendRegistrationMessage(String email, String generatedKey) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setSubject("Online-nyelvtanulás - Sikeres regisztrálás");

			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(MESSAGE_FROM_NAME);
			helper.setTo(email);
			
			String msg = "Kedves " + email
					+ "! <br/><br/> Köszönjük, hogy regisztráltál az oldalunkra!<a href='" + BASE_URL + "/activation/"
					+ generatedKey + "' >Aktiválás</a>";
			
			helper.setText(msg, true);
			
			javaMailSender.send(message);

		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + email + "  " + e);
		}
	}
	
	public void sendForgottenPasswordMessage(String email, String generatedKey) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setSubject("Online-nyelvtanulás - Elfelejtett jelszó");

			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(MESSAGE_FROM_NAME);
			helper.setTo(email);
			
			String msg = "Kedves " + email
					+ "! <br/><br/> Az oldalunkon az új jelszó megadásához látogasson el az <a href='" + BASE_URL + "/forgotten_password_activation/"
					+ generatedKey + "' >új jelszó megadása</a> címre.";
			
			helper.setText(msg, true);
			
			javaMailSender.send(message);

		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + email + "  " + e);
		}
	}
}
