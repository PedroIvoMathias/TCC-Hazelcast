package com.safar.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long routeID;

    @NotNull(message = "Start point cannot be null !")
	@NotBlank(message = "Start point cannot be blank !")
	@NotEmpty(message = "Start point cannot be empty !")
    private String routeFrom;

	@NotNull(message = "Destination point cannot be null !")
	@NotBlank(message = "Destination point cannot be blank !")
	@NotEmpty(message = "Destination point cannot be empty !")
    private String routeTo;
    private Integer distance;

    @JsonIgnore
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Bus> busList = new ArrayList<>();


    public Route(String routeFrom, String routeTo, Integer distance) {
        this.routeFrom = routeFrom;
        this.routeTo = routeTo;
        this.distance = distance;
    }


	public Long getRouteID() {
		return routeID;
	}


	public void setRouteID(Long routeID) {
		this.routeID = routeID;
	}


	public String getRouteFrom() {
		return routeFrom;
	}


	public void setRouteFrom(String routeFrom) {
		this.routeFrom = routeFrom;
	}


	public String getRouteTo() {
		return routeTo;
	}


	public void setRouteTo(String routeTo) {
		this.routeTo = routeTo;
	}


	public Integer getDistance() {
		return distance;
	}


	public void setDistance(Integer distance) {
		this.distance = distance;
	}


	public List<Bus> getBusList() {
		return busList;
	}


	public void setBusList(List<Bus> busList) {
		this.busList = busList;
	}


	@Override
	public int hashCode() {
		return Objects.hash(distance, routeFrom, routeID, routeTo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		return Objects.equals(distance, other.distance)
				&& Objects.equals(routeFrom, other.routeFrom) && Objects.equals(routeID, other.routeID)
				&& Objects.equals(routeTo, other.routeTo);
	}


	public Route() {
	}


	public Route(Long routeID,
			@NotNull(message = "Start point cannot be null !") @NotBlank(message = "Start point cannot be blank !") @NotEmpty(message = "Start point cannot be empty !") String routeFrom,
			@NotNull(message = "Destination point cannot be null !") @NotBlank(message = "Destination point cannot be blank !") @NotEmpty(message = "Destination point cannot be empty !") String routeTo,
			Integer distance, List<Bus> busList) {
		this.routeID = routeID;
		this.routeFrom = routeFrom;
		this.routeTo = routeTo;
		this.distance = distance;
		this.busList = busList;
	}
    
    
}
