package com.safar.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.exception.AdminException;
import com.safar.model.Admin;
import com.safar.model.CurrentAdminSession;
import com.safar.repository.AdminRepository;
import com.safar.repository.CurrentAdminSessionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

	 private final AdminRepository adminRepository;
	    private final CurrentAdminSessionRepository adminSessionRepository;
	    private final IMap<Long, Admin> adminCache; 

	    @Autowired
	    public AdminServiceImpl(AdminRepository adminRepository, 
	                            CurrentAdminSessionRepository adminSessionRepository, 
	                            HazelcastInstance hazelcastInstance) {
	        this.adminRepository = adminRepository;
	        this.adminSessionRepository = adminSessionRepository;
	        this.adminCache = hazelcastInstance.getMap("adminCache"); 
	    }
    @Override
    public Admin createAdmin(Admin admin) throws AdminException{
    	if (adminCache.values().stream().anyMatch(a -> a.getEmail().equals(admin.getEmail()))) {
            throw new AdminException("Admin already present with the given email: " + admin.getEmail());
        }
    	
        List<Admin> admins = adminRepository.findByEmail(admin.getEmail());
        
        if(!admins.isEmpty()) throw new AdminException("Admin already present with the given email: " + admin.getEmail());
        
        Admin savedAdmin = adminRepository.save(admin);
        adminCache.put(savedAdmin.getAdminID(), savedAdmin);
        
        return savedAdmin;
    }

    @Override
    public Admin updateAdmin(Admin admin, String key) throws AdminException {
        CurrentAdminSession adminSession = adminSessionRepository.findByaid(key);
        if(adminSession == null) throw new AdminException("Please enter valid key or login first!");
        if(admin.getAdminID() != adminSession.getAdminID()) throw new AdminException("Invalid admin details, please login for updating admin!");
        Admin updatedAdmin = adminRepository.save(admin);
        
        adminCache.put(updatedAdmin.getAdminID(), updatedAdmin);

        return updatedAdmin;    
    }
    	
}
