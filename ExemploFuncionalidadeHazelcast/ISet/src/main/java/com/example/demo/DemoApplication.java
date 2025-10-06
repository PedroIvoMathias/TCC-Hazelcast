package com.example.demo;

import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.collection.ISet;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class DemoApplication {

	public static void main(String... args) throws IOException, InterruptedException {
		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();
		ISet<String> hzFrutas = hazelcast.getSet("fruits");
		hzFrutas.add("Manga");
		hzFrutas.add("Manga");
		hzFrutas.add("Maçã");
		hzFrutas.add("Banana");
		hzFrutas.add("Banana");
		System.out.println(hzFrutas.add("Apple"));
		System.out.println("Tamanho do set:" + hzFrutas.size());
		System.exit(0);
	}

}
