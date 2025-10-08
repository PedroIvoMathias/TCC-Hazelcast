package com.example.demo;
import com.hazelcast.core.*;
import com.hazelcast.config.Config;
import com.hazelcast.cp.lock.FencedLock;

import java.util.ArrayList;
import java.util.List;

public class DemoApplication {
    public static void main(String[] args) throws InterruptedException {
        int numeroDeNos = 3;
        List<HazelcastInstance> cluster = new ArrayList<>();

        for (int i = 0; i < numeroDeNos; i++) {
            HazelcastInstance instance = Hazelcast.newHazelcastInstance(new Config());
            cluster.add(instance);
        }

        for (int i = 0; i < cluster.size(); i++) {
            final int nodeId = i;
            HazelcastInstance node = cluster.get(i);

            new Thread(() -> {
                FencedLock lock = node.getCPSubsystem().getLock("leader-lock");

                if (lock.tryLock()) {
                    System.out.println("Nó " + nodeId + " foi eleito como LÍDER.");
                    try {
                        while (true) {
                            System.out.println("Nó " + nodeId + " mantendo liderança...");
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Nó " + nodeId + " é SEGUIDOR.");
                    try {
                        while (true) {
                            System.out.println("Nó " + nodeId + " aguardando liderança...");
                            Thread.sleep(5000);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
    }
}