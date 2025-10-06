package com.safar.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long feedBackId;
	private Integer driverRating;
	
	@Min(value=1, message="Rating must be in range of 1-5")  
	@Max(value=5, message="Rating must be in range of 1-5") 
	private Integer serviceRating;

	@Min(value=1, message="Rating must be in range of 1-5")
	@Max(value=5, message="Rating must be in range of 1-5")
	private Integer overallRating;
	
	private String comments;

	private LocalDateTime feedbackDateTime;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Bus bus;

	public Long getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(Long feedBackId) {
		this.feedBackId = feedBackId;
	}

	public Integer getDriverRating() {
		return driverRating;
	}

	public void setDriverRating(Integer driverRating) {
		this.driverRating = driverRating;
	}

	public Integer getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Integer serviceRating) {
		this.serviceRating = serviceRating;
	}

	public Integer getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(Integer overallRating) {
		this.overallRating = overallRating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getFeedbackDateTime() {
		return feedbackDateTime;
	}

	public void setFeedbackDateTime(LocalDateTime feedbackDateTime) {
		this.feedbackDateTime = feedbackDateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bus, comments, driverRating, feedBackId, feedbackDateTime, overallRating, serviceRating,
				user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feedback other = (Feedback) obj;
		return Objects.equals(bus, other.bus) && Objects.equals(comments, other.comments)
				&& Objects.equals(driverRating, other.driverRating) && Objects.equals(feedBackId, other.feedBackId)
				&& Objects.equals(feedbackDateTime, other.feedbackDateTime)
				&& Objects.equals(overallRating, other.overallRating)
				&& Objects.equals(serviceRating, other.serviceRating) && Objects.equals(user, other.user);
	}

	public Feedback() {
	}

	public Feedback(Long feedBackId, Integer driverRating,
			@Min(value = 1, message = "Rating must be in range of 1-5") @Max(value = 5, message = "Rating must be in range of 1-5") Integer serviceRating,
			@Min(value = 1, message = "Rating must be in range of 1-5") @Max(value = 5, message = "Rating must be in range of 1-5") Integer overallRating,
			String comments, LocalDateTime feedbackDateTime, User user, Bus bus) {
		this.feedBackId = feedBackId;
		this.driverRating = driverRating;
		this.serviceRating = serviceRating;
		this.overallRating = overallRating;
		this.comments = comments;
		this.feedbackDateTime = feedbackDateTime;
		this.user = user;
		this.bus = bus;
	}
	
	
	
}
