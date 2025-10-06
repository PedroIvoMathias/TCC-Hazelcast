package com.safar.exception;


import java.time.LocalDateTime;
import java.util.Objects;




public class MyErrorDetails {
    private LocalDateTime time;
    private String message;
    private String details;
    
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public int hashCode() {
		return Objects.hash(details, message, time);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyErrorDetails other = (MyErrorDetails) obj;
		return Objects.equals(details, other.details) && Objects.equals(message, other.message)
				&& Objects.equals(time, other.time);
	}
	
	public MyErrorDetails() {
	}
	
	public MyErrorDetails(LocalDateTime time, String message, String details) {
		this.time = time;
		this.message = message;
		this.details = details;
	}
    
	
    
    
    
    
    
    
}
