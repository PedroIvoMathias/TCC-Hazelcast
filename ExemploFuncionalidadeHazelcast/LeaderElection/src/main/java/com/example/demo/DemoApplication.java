package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.IAtomicLong;

@SpringBootApplication
public class DemoApplication {

private static final String LOCK_NAME = "leader-lock";
public static void main(String[] args) {
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();

        
        IAtomicLong leaderCounter = hazelcast.getCPSubsystem().getAtomicLong("leader");

        long value = leaderCounter.incrementAndGet();

        if (value == 1) {
            System.out.println("Este nó é o líder.");
            
        } else {
            System.out.println("Este nó é um seguidor.");
        }

        System.exit(0);
    }

}
