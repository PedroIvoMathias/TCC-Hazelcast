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
		HazelcastInstance hazelcast2 = Hazelcast.newHazelcastInstance();
		ISet<String> hzFrutas = hazelcast.getSet("fruits");
		ISet<String> hzFrutas2 = hazelcast2.getSet("fruits");
		hzFrutas.add("Manga");
		hzFrutas2.add("Manga");
		hzFrutas.add("Maçã");
		hzFrutas.add("Banana");
		hzFrutas2.add("Banana");
		hzFrutas.add("Mexerica");
		hzFrutas2.add("Mexerica");
		hzFrutas.add("Uva");
		hzFrutas2.add("Uva");
		hzFrutas2.add("Abacate");
		hzFrutas2.add("Melão");
		System.out.println(hzFrutas.add("Apple"));
		System.out.println("Tamanho do set:" + hzFrutas.size());
		System.exit(0);
	}

}
