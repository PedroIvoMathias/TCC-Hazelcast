package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.map.IMap;
import com.safar.model.Bus;

public class BusCache {
	
	 @Autowired
	 private IMap<Long, Bus> busCache;
	 
	 public Bus getBusCache(Long id) {
	        return busCache.get(id);
	    }

	    public void addBusCache(Bus bus) {
	        if (bus != null && bus.getBusId() != null) {
	            busCache.put(bus.getBusId(), bus);
	        }
	    }

	    public void removeBusCache(Long id) {
	        if (id != null) {
	            busCache.remove(id);
	        }
	    }

	    public boolean hasBusCache(Long id) {
	        return busCache.containsKey(id);
	    }



}
