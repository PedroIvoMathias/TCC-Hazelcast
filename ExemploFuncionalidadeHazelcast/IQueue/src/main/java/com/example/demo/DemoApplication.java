package com.example.demo;

import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class DemoApplication {

	public static void main(String... args) throws IOException, InterruptedException {
		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();
		IQueue<String> hzFrutas = hazelcast.getQueue("frutas");
		String[] frutas = { "Manga", "Maçã", "Banana", "Melancia" };
		for (String fruta : frutas) {
			System.out.println("Producing: " + fruta);
			hzFrutas.add(fruta);
			Thread.sleep(1000);
		}
		System.exit(0);
	}

}
