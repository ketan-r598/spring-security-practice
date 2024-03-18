package springSecurityPractice.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
	
	@Column(updatable = false)
	@CreatedDate
	private Date createdAt;
	
	@LastModifiedDate
	private Date updatedAt;
	
	@PrePersist
	public void onPrePersist() {
		setCreatedAt(new Date());
	}
	
	@PreUpdate
	public void onPreUpdate() {
		setUpdatedAt(new Date());
	}
	
	@PreRemove
	public void onPreRemove() {
		setUpdatedAt(new Date());
	}
	
}
