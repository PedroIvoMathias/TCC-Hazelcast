package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.map.IMap;
import com.safar.model.Reservation;

@Service
public class ReservationCache {

	@Autowired
    private IMap<Long, Reservation> cacheReservation;
 	


    public Reservation getReservationCache(Long id) {
        return cacheReservation.get(id);
    }

    public void addOrUpdateReservationCache(Reservation reservation) {
    	if(reservation != null && reservation.getReservationID()!=null) {
    		cacheReservation.put(reservation.getReservationID(),reservation);
    	}
    }
    
    public boolean hasReservationCache(Long id) {
        return cacheReservation.containsKey(id);
    }
    
    public void removeReservationCache(Long id) {
        if (id != null) {
            cacheReservation.remove(id);
        }
    }
}
