package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();
		HazelcastInstance hazelcast2 = Hazelcast.newHazelcastInstance();

		MultiMap<String, String> categorias = hazelcast.getMultiMap("categorias");
		MultiMap<String, String> categorias2 = hazelcast2.getMultiMap("categorias");

		categorias.put("Frutas", "Maçã");
		categorias.put("Frutas", "Banana");
		categorias.put("Frutas", "Manga");

		categorias.put("Verduras", "Alface");
		categorias.put("Verduras", "Couve");
		categorias2.put("Frutas", "Mamão");
		categorias2.put("Frutas", "Abacate");
		categorias2.put("Frutas", "Melancia");

		categorias2.put("Verduras", "Beterraba");
		categorias2.put("Verduras", "Couve Flor");

		System.out.println("Itens da categoria 'Frutas': " + categorias.get("Frutas"));

		System.out.println("Itens da categoria 'Verduras': " + categorias.get("Verduras"));

		System.exit(0);
	}

}
