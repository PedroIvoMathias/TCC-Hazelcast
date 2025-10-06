package com.safar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.safar.model.Admin;
import com.safar.model.Bus;
import com.safar.model.CurrentAdminSession;
import com.safar.model.CurrentUserSession;
import com.safar.model.Feedback;
import com.safar.model.Reservation;
import com.safar.model.Route;
import com.safar.model.User;

@Configuration
public class HazelcastConfig {
    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        
        
        QueueConfig reservationQueueConfig = new QueueConfig("reservationQueue");
        reservationQueueConfig.setMaxSize(1000); 
        config.addQueueConfig(reservationQueueConfig);
        
        QueueConfig userLoginQueueConfig = new QueueConfig("userLoginQueue");
        userLoginQueueConfig.setMaxSize(1000); 
        config.addQueueConfig(userLoginQueueConfig);
        
        config.addMapConfig(new MapConfig("adminCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("feedbackCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("reservationCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("busCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("currentAdminSessionCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("currentUserSessionCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("routeCache").setTimeToLiveSeconds(3600));
        config.addMapConfig(new MapConfig("userCache").setTimeToLiveSeconds(3600));

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<Long, Admin> cacheAdmin(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("adminCache");
    }

    @Bean
    public IMap<Long, Feedback> cacheFeedback(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("feedbackCache");
    }

    @Bean
    public IMap<Long, Bus> cacheBus(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("busCache");
    }

    @Bean
    public IMap<Long, CurrentAdminSession> cacheCurrentAdminSession(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("currentAdminSessionCache");
    }

    @Bean
    public IMap<Long, CurrentUserSession> cacheCurrentUserSession(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("currentUserSessionCache");
    }

    @Bean
    public IMap<Long, Reservation> cacheReservation(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("reservationCache");
    }

    @Bean
    public IMap<Long, Route> cacheRoute(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("routeCache");
    }

    @Bean
    public IMap<Long, User> cacheUser(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("userCache");
    }

    @Bean
    public IQueue<Reservation> reservationQueue(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getQueue("reservationQueue");
    }
    
    @Bean
    public IQueue<String> userLoginQueue(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getQueue("userLoginQueue");
    }
}
