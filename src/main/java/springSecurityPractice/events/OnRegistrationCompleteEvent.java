package springSecurityPractice.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import springSecurityPractice.dtos.UserResponseDTO;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private Locale locale;
	private UserResponseDTO user;
	
	public OnRegistrationCompleteEvent(UserResponseDTO user, Locale locale, String appUrl) {
		super(user);
		
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

}
