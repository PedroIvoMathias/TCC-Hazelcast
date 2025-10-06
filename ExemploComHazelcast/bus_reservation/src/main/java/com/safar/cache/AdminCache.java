package com.safar.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.map.IMap;
import com.safar.model.Admin;

@Service
public class AdminCache {
	
	private final IMap<Long, Admin> cacheAdmin;
	
	@Autowired
	public AdminCache(IMap<Long,Admin> cacheAdmin) {
		this.cacheAdmin = cacheAdmin;
	}

	  public Admin getAdminCache(Long id) {
	        return cacheAdmin.get(id);
	    }

	    public void addOrUpdateAdminCache(Admin admin) {
	    	if(admin != null && admin.getAdminID()!=null) {
	    		cacheAdmin.put(admin.getAdminID(),admin);
	    	}
	    }
	    
	    public boolean hasAdminCache(Long id) {
	        return cacheAdmin.containsKey(id);
	    }
	    
	    public void removeAdminCache(Long id) {
	        if (id != null) {
	            cacheAdmin.remove(id);
	        }
	    }
}
