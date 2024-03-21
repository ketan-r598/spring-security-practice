package springSecurityPractice.eventListeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import springSecurityPractice.events.OnPasswordResetRequestEvent;
import springSecurityPractice.models.User;
import springSecurityPractice.services.IUserService;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetRequestEvent> {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnPasswordResetRequestEvent event) {
		this.resetPassword(event);
		
	}

	private void resetPassword(OnPasswordResetRequestEvent event) {
		
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		
		userService.createPasswordResetToken(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "Password Reset";
		String confirmationUrl = 
				event.getAppUrl() + "/passwordReset?token=" + token;
		
		System.out.println("Line 40");
		System.out.println(event.getAppUrl());
		System.out.println(confirmationUrl);
		System.out.println("Line 43");
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
//		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		email.setText("http://localhost:8080/api/v1/user" + confirmationUrl);
		mailSender.send(email);
	}

}
