package com.sec.service;

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

	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;

	private JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendRegistrationMessage(String email, String generatedKey) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setSubject("Sikeres regisztrálás");
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(MESSAGE_FROM);
			helper.setTo(email);
			String msg = "Kedves " + email
					+ "! <br/><br/> Köszönjük, hogy regisztráltál az oldalunkra!<br/><a href='http://localhost:8080/activation/"
					+ generatedKey + "' >Aktiválás</a>";
			helper.setText(msg, true);
			javaMailSender.send(message);

		} catch (Exception e) {
			log.error("Hiba e-mail küldéskor az alábbi címre: " + email + "  " + e);
		}

	}

}
