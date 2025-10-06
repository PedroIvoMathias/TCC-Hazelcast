package com.safar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class CurrentAdminSession {
    @Id
    @Column(unique = true)
    private Long adminID;
    private String aid;
    private LocalDateTime time;
    
	public Long getAdminID() {
		return adminID;
	}
	public void setAdminID(Long adminID) {
		this.adminID = adminID;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public int hashCode() {
		return Objects.hash(adminID, aid, time);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentAdminSession other = (CurrentAdminSession) obj;
		return Objects.equals(adminID, other.adminID) && Objects.equals(aid, other.aid)
				&& Objects.equals(time, other.time);
	}
    
    
}
