package com.safar.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.exception.AdminException;
import com.safar.exception.LoginException;
import com.safar.model.Admin;
import com.safar.model.AdminLoginDTO;
import com.safar.model.CurrentAdminSession;
import com.safar.repository.AdminRepository;
import com.safar.repository.CurrentAdminSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final CurrentAdminSessionRepository adminSessionRepository;
    private final AdminRepository adminRepository;
    private final IMap<String, CurrentAdminSession> adminSessionCache;

    @Autowired
    public AdminLoginServiceImpl(CurrentAdminSessionRepository adminSessionRepository,
                                 AdminRepository adminRepository,
                                 HazelcastInstance hazelcastInstance) {
        this.adminSessionRepository = adminSessionRepository;
        this.adminRepository = adminRepository;
        this.adminSessionCache = hazelcastInstance.getMap("adminSessionCache");
    }

    @Override
    @Transactional 
    public CurrentAdminSession adminLogin(AdminLoginDTO loginDTO) throws LoginException, AdminException {
        List<Admin> admins = adminRepository.findByEmail(loginDTO.getEmail());
        if (admins.isEmpty()) {
            throw new AdminException("Please enter a valid email!");
        }

        Admin registeredAdmin = admins.get(0);

        Optional<CurrentAdminSession> loggedInAdmin = adminSessionRepository.findById(registeredAdmin.getAdminID());
        if (loggedInAdmin.isPresent()) {
            throw new LoginException("Admin is already loggedIn!");
        }

        if (registeredAdmin.getPassword().equals(loginDTO.getPassword())) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[10];
            secureRandom.nextBytes(keyBytes);
            String key = Base64.getEncoder().encodeToString(keyBytes);

            CurrentAdminSession adminSession = new CurrentAdminSession();
            adminSession.setAdminID(registeredAdmin.getAdminID());
            adminSession.setAid(key);
            adminSession.setTime(LocalDateTime.now());

            CurrentAdminSession savedSession = adminSessionRepository.save(adminSession);

            adminSessionCache.put(savedSession.getAid(), savedSession);

            return savedSession;
        } else {
            throw new LoginException("Please enter a valid password!");
        }
    }

    @Override
    @Transactional
    public String adminLogout(String key) throws LoginException {
        CurrentAdminSession currentAdminSession = adminSessionCache.remove(key);

        if (currentAdminSession == null) {
            currentAdminSession = adminSessionRepository.findByaid(key);
        }

        if (currentAdminSession == null) {
            throw new LoginException("Invalid Admin login key or already logged out!");
        }

        adminSessionRepository.delete(currentAdminSession);

        return "Admin logged out!";
    }
}