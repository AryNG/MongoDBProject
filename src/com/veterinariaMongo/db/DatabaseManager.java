package com.veterinariaMongo.db;
import com.mongodb.client.MongoClients; //Clase que regresa instancias, based of a design patter.
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient; //interface
import com.mongodb.client.MongoDatabase;
import com.veterinariaMongo.model.Dog;

public class DatabaseManager {
	public void addDog(Dog dog) {
		MongoClient client =  MongoClients.create();
		MongoDatabase myDB = client.getDatabase("veterinary");
		MongoCollection<Document> dogCollection = myDB.getCollection("dogs"); //Equals Select on JDBC
		
		Document dogDocument =  new Document ("name", dog.getName())
		.append("age",dog.getAge())
		.append("weight",dog.getWeight())
		.append("color", dog.getColor())
		.append("isAlive", dog.isAlive())
		.append("breed",dog.getBreed());
		
		//Add document
		dogCollection.insertOne(dogDocument);
		
		
		//Close
		client.close();
	}
	public List<Dog> getDog(){
		MongoClient client =  MongoClients.create();
		MongoDatabase myDB = client.getDatabase("veterinary");
		MongoCollection<Document> dogCollection = myDB.getCollection("dogs"); //Equals Select on JDBC
		
		List<Dog> listDogs = new ArrayList<>();
		MongoCursor<Document> iterator = dogCollection.find().iterator();
		while(iterator.hasNext()) {
			Document doc = iterator.next();
			Dog dog = new Dog();
			dog.setName(doc.getString("name"));
			dog.setAge(doc.getInteger("age"));
			dog.setWeight(doc.getDouble("weight").floatValue());
			dog.setColor(doc.getString("color"));
			dog.setAlive(doc.getBoolean("isAlive"));
			dog.setBreed(doc.getString("breed"));
			listDogs.add(dog);
		}
		client.close();
		return listDogs;
	} 
}
