package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.map.IMap;
import com.safar.model.Route;

public class RouteCache {

	
	@Autowired
    private IMap<Long, Route> cacheRoute;
 	


    public Route getRouteCache(Long id) {
        return cacheRoute.get(id);
    }

    public void addOrUpdateRouteCache(Route route) {
    	if(route != null && route.getRouteID()!=null) {
    		cacheRoute.put(route.getRouteID(),route);
    	}
    }
    
    public boolean hasRouteCache(Long id) {
        return cacheRoute.containsKey(id);
    }
    
    public void removeRouteCache(Long id) {
        if (id != null) {
            cacheRoute.remove(id);
        }
    }
    
}
