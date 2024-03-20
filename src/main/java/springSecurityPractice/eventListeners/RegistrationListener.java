package springSecurityPractice.eventListeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import springSecurityPractice.dtos.UserResponseDTO;
import springSecurityPractice.events.OnRegistrationCompleteEvent;
import springSecurityPractice.services.IUserService;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private IUserService service;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {		
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserResponseDTO user = event.getUser();
		String token = UUID.randomUUID().toString();
		
//		service.createVerificationToken(user,token);
		
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = 
				event.getAppUrl() + "/registrationConfirm?token=" + token;
		String message = messages.getMessage("message.regSucc", null, event.getLocale());
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		mailSender.send(email);
	}

}
