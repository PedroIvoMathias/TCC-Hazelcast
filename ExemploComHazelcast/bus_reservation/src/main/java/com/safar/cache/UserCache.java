package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.map.IMap;
import com.safar.model.User;

@Service
public class UserCache {

	
	 	@Autowired
	    private IMap<Long, User> cacheUser;
	 	


	    public User getUserCache(Long id) {
	        return cacheUser.get(id);
	    }

	    public void addOrUpdateUserCache(User user) {
	    	if(user != null && user.getUserID()!=null) {
	    		cacheUser.put(user.getUserID(),user);
	    	}
	    }
	    
	    public boolean hasUserCache(Long id) {
	        return cacheUser.containsKey(id);
	    }
	    
	    public void removeUserCache(Long id) {
	        if (id != null) {
	            cacheUser.remove(id);
	        }
	    }
	    
	    
	    
}
