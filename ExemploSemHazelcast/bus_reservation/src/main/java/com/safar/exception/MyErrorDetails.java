package com.safar.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
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
	public MyErrorDetails(LocalDateTime time, String message, String details) {
		super();
		this.time = time;
		this.message = message;
		this.details = details;
	}
	public MyErrorDetails() {
		super();
	}
    
	
    
    
    
    
    
    
}
