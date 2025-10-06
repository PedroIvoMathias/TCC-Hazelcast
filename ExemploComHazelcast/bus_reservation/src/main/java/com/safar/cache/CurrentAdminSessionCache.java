package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.map.IMap;
import com.safar.model.CurrentAdminSession;

public class CurrentAdminSessionCache {

	@Autowired
    private IMap<String, CurrentAdminSession> cacheCurrentAdminSession;
 	


    public CurrentAdminSession getCurrentAdminSessionCache(String aid) {
        return cacheCurrentAdminSession.get(aid);
    }

    public void addOrUpdateCurrentAdminSessionCache(CurrentAdminSession cas) {
    	if(cas != null && cas.getAid()!=null) {
    		cacheCurrentAdminSession.put(cas.getAid(),cas);
    	}
    }
    
    public boolean hasCurrentAdminSessionCache(String aid) {
        return cacheCurrentAdminSession.containsKey(aid);
    }
    
    public void removeCurrentAdminSessionCache(String aid) {
        if (aid != null) {
            cacheCurrentAdminSession.remove(aid);
        }
    }
}
