package com.safar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name cannot be blank!")
    private String firstName;
    private String lastName;

    @NotNull(message="Mobile number cannot be null!")
    @NotBlank(message= "Mobile number cannot be blank!")
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    @Size(min = 10, max = 10)
    private String mobile;

    @Email
    private String email;

    @NotNull(message="Password cannot be null!")
    @NotBlank(message= "Password cannot be blank!")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters including alphanumerics and special characters")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbackList = new ArrayList<>();

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, feedbackList, firstName, lastName, mobile, password, reservationList, userID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(feedbackList, other.feedbackList)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(mobile, other.mobile) && Objects.equals(password, other.password)
				&& Objects.equals(reservationList, other.reservationList) && Objects.equals(userID, other.userID);
	}

	public User() {
	}

	public User(Long userID,
			@NotNull(message = "Name cannot be null!") @NotBlank(message = "Name cannot be blank!") String firstName,
			String lastName,
			@NotNull(message = "Mobile number cannot be null!") @NotBlank(message = "Mobile number cannot be blank!") @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number") @Size(min = 10, max = 10) String mobile,
			@Email String email,
			@NotNull(message = "Password cannot be null!") @NotBlank(message = "Password cannot be blank!") @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters including alphanumerics and special characters") String password,
			List<Reservation> reservationList, List<Feedback> feedbackList) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.reservationList = reservationList;
		this.feedbackList = feedbackList;
	}
    
    
}
