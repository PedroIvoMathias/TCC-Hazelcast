package com.example.demo;

import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();
		HazelcastInstance hazelcast2 = Hazelcast.newHazelcastInstance();
		IList<String> hzFrutas2 = hazelcast2.getList("frutas");
		IList<String> hzFrutas = hazelcast.getList("frutas");
		hzFrutas.add("Manga");
		hzFrutas.add("Maçã");
		hzFrutas.add("Banana");

		hzFrutas2.add("Tomate");
		hzFrutas2.add("Abacate");
		hzFrutas2.add("Uva");
		System.out.println(hzFrutas.add("Maçã"));
		System.out.println("O tamanho da lista é:" + hzFrutas.size());
		System.exit(0);
	}

}
