package springSecurityPractice.events;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import springSecurityPractice.models.User;

@Data
public class OnPasswordResetRequestEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;

	private User user;
	private String appUrl;
	
	public OnPasswordResetRequestEvent(User user, String appUrl) {
		super(user);
		
		this.user = user;
		this.appUrl = appUrl;
	}

}
