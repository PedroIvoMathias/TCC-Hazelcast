package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.map.IMap;
import com.safar.model.CurrentUserSession;

public class CurrentUserSessionCache {
	@Autowired
    private IMap<String, CurrentUserSession> cacheCurrentUserSession;
 	


    public CurrentUserSession getCurrentAdminSessionCache(String aid) {
        return cacheCurrentUserSession.get(aid);
    }

    public void addOrUpdateCurrentUserSessionCache(CurrentUserSession usersession) {
    	if(usersession != null && usersession.getUuid()!=null) {
    		cacheCurrentUserSession.put(usersession.getUuid(),usersession);
    	}
    }
    
    public boolean hasCurrentUserSessionCache(String aid) {
        return cacheCurrentUserSession.containsKey(aid);
    }
    
    public void removeUserSessionCache(String aid) {
        if (aid != null) {
            cacheCurrentUserSession.remove(aid);
        }
    }

}
