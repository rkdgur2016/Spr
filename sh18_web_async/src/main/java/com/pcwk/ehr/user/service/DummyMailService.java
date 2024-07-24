package com.pcwk.ehr.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.pcwk.ehr.cmn.PLog;

public class DummyMailService implements MailSender, PLog{

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ DummyMailService send()");
		log.debug("│ 반송이요~! 나가~~~ 개발소발네발에서는 메일이 전송안된다~");
		log.debug("└─────────────────────────────────────────────────────────");
		
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		log.debug("┌─────────────────────────────────────────────────────────");
		log.debug("│ DummyMailService send()");
		log.debug("│ 반송이요~! 나가~~~ 개발소발네발에서는 메일이 전송안된다~");
		log.debug("└─────────────────────────────────────────────────────────");
		
	}
}
