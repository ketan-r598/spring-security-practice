package springSecurityPractice.eventListeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import springSecurityPractice.events.OnRegistrationCompleteEvent;
import springSecurityPractice.models.User;
import springSecurityPractice.services.IUserService;

@Component
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
		System.out.println("\nIn the onApplicationEvent method !!!\n");
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		System.out.println("\nIn the confirmRegistration method !!!\n");
		
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		
		service.createVerificationToken(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = 
				event.getAppUrl() + "/registrationConfirm?token=" + token;
//		String message = messages.getMessage("message.regSucc", null, event.getLocale());
//		String message = messages.getMessage("message success", null, event.getLocale());
		System.out.println("Line 48");
		System.out.println(confirmationUrl);
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
//		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		email.setText("http://localhost:8080/api/v1/user" + confirmationUrl);
		mailSender.send(email);
	}

}
