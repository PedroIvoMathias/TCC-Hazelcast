package com.safar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class CurrentUserSession {
    @Id
    @Column(unique = true)
    private Long userID;
    private String uuid;
    private LocalDateTime time;
    
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public int hashCode() {
		return Objects.hash(time, userID, uuid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentUserSession other = (CurrentUserSession) obj;
		return Objects.equals(time, other.time) && Objects.equals(userID, other.userID)
				&& Objects.equals(uuid, other.uuid);
	}
    
    
}
