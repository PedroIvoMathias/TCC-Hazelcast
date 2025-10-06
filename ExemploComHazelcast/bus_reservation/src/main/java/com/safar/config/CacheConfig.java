package com.safar.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.cache.AdminCache;
import com.safar.model.Admin;
import com.safar.model.Bus;
import com.safar.model.CurrentAdminSession;
import com.safar.model.CurrentUserSession;
import com.safar.model.Feedback;
import com.safar.model.Reservation;
import com.safar.model.Route;
import com.safar.model.User;

@Configuration
public class CacheConfig {
    public static String KEY_ADMIN = "Admin";
    public static String KEY_ROUTE = "Route";
    public static String KEY_BUS = "Bus";
    public static String KEY_FEEDBACK = "Feedback";
    public static String KEY_USER = "User";
    public static String KEY_CURRENTUSERSESSION = "CurrentUserSession";
    public static String KEY_CURRENTADMINSESSION = "CurrentAdminSessin";
    public static String KEY_RESERVATION = "Reservation";


    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(5); 
    }
    
    
    @Bean
    public IMap<String, Route> routeMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_ROUTE);
    }
    @Bean
    public IMap<String, Bus> busMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_BUS);
    }
    @Bean
    public IMap<String, Feedback> feedbackMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_FEEDBACK);
    }
    @Bean
    public IMap<String, CurrentAdminSession> currentAdminSessionMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_ADMIN);
    }
    @Bean
    public IMap<String, CurrentUserSession> currentUserSessionMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_ADMIN);
    }
    @Bean
    public IMap<String, Reservation> reservationMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_ADMIN);
    }
    @Bean
    public IMap<String, User> userMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(KEY_ADMIN);
    }
    
    
    
}
