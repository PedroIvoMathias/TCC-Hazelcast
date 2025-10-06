package com.safar.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationID;
    private String status;
    private LocalDate date;
    private LocalTime time;

    private String source;
    private String destination;
    private LocalDate journeyDate;
    private Integer bookedSeat;
    private Integer fare;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bus bus;

	public Long getReservationID() {
		return reservationID;
	}

	public void setReservationID(Long reservationID) {
		this.reservationID = reservationID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}

	public Integer getBookedSeat() {
		return bookedSeat;
	}

	public void setBookedSeat(Integer bookedSeat) {
		this.bookedSeat = bookedSeat;
	}

	public Integer getFare() {
		return fare;
	}

	public void setFare(Integer fare) {
		this.fare = fare;
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
		return Objects.hash(bookedSeat, bus, date, destination, fare, journeyDate, reservationID, source, status, time,
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
		Reservation other = (Reservation) obj;
		return Objects.equals(bookedSeat, other.bookedSeat) && Objects.equals(bus, other.bus)
				&& Objects.equals(date, other.date) && Objects.equals(destination, other.destination)
				&& Objects.equals(fare, other.fare) && Objects.equals(journeyDate, other.journeyDate)
				&& Objects.equals(reservationID, other.reservationID) && Objects.equals(source, other.source)
				&& Objects.equals(status, other.status) && Objects.equals(time, other.time)
				&& Objects.equals(user, other.user);
	}

	public Reservation() {
	}

	public Reservation(Long reservationID, String status, LocalDate date, LocalTime time, String source,
			String destination, LocalDate journeyDate, Integer bookedSeat, Integer fare, User user, Bus bus) {
		this.reservationID = reservationID;
		this.status = status;
		this.date = date;
		this.time = time;
		this.source = source;
		this.destination = destination;
		this.journeyDate = journeyDate;
		this.bookedSeat = bookedSeat;
		this.fare = fare;
		this.user = user;
		this.bus = bus;
	}
    
    
}
