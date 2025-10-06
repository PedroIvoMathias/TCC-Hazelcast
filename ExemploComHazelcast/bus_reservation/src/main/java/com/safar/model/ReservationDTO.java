package com.safar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;


public class ReservationDTO {
    @NotNull(message = "Source required to book a reservation")
    @NotBlank(message = "Source should not be blanked")
    private String source;

    @NotNull(message = "Destination required to book a reservation")
    @NotBlank(message = "Destination should not be blanked")
    private String destination;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate journeyDate;

    @Min(1)
    private Integer bookedSeat;

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

	@Override
	public int hashCode() {
		return Objects.hash(bookedSeat, destination, journeyDate, source);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationDTO other = (ReservationDTO) obj;
		return Objects.equals(bookedSeat, other.bookedSeat) && Objects.equals(destination, other.destination)
				&& Objects.equals(journeyDate, other.journeyDate) && Objects.equals(source, other.source);
	}

	public ReservationDTO() {
	}

	public ReservationDTO(
			@NotNull(message = "Source required to book a reservation") @NotBlank(message = "Source should not be blanked") String source,
			@NotNull(message = "Destination required to book a reservation") @NotBlank(message = "Destination should not be blanked") String destination,
			@NotNull LocalDate journeyDate, @Min(1) Integer bookedSeat) {
		this.source = source;
		this.destination = destination;
		this.journeyDate = journeyDate;
		this.bookedSeat = bookedSeat;
	}
    
    
}
