package com.example.demo;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class DemoApplication {

	public static void main(String... args) throws IOException, InterruptedException {
		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();
		HazelcastInstance hazelcast2 = Hazelcast.newHazelcastInstance();

		IQueue<String> hzFrutas = hazelcast.getQueue("frutas");
		IQueue<String> hzFrutas2 = hazelcast2.getQueue("frutas");

		String[] frutas = { "Manga", "Maçã", "Banana", "Melancia" };
		String[] frutas2 = { "Pera", "Uva", "Mexerica"};


        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            for (String fruta : frutas) {
                System.out.println("Thread 1 produzindo: " + fruta);
                hzFrutas.add(fruta);
          
            }
        });

        executor.submit(() -> {
            for (String fruta : frutas2) {
                System.out.println("Thread 2 produzindo: " + fruta);
              	hzFrutas2.add(fruta);
               
            }
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Todos os dados foram inseridos.");
        System.exit(0);

	}

}
