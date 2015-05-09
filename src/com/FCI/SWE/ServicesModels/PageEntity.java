package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PageEntity {
private String name;
private String type;
private  String category;
private int number_of_likes;
private String owner;
private User user;
public PageEntity(String n,String t , String c , int nom, String O) {
	this.name= n;
	this.category=c;
	this.number_of_likes=nom;
	this.type=t;
	this.owner=O;
	
	
	// TODO Auto-generated constructor stub
}
	public void setType(String type) {
		this.type = type;
}
	public void setNumber_of_likes(int number_of_likes) {
		this.number_of_likes = number_of_likes;
}
	public void setCategory(String category) {
		this.category = category;
}
	public String getOwner() {
		return owner;
}	public String getName() {
		return name;
}
	public String getCategory() {
		return category;
}
	public int getNumber_of_likes() {
		return number_of_likes;
}
	public void setOwner(String owner) {
		this.owner = owner;
}
	public void setName(String name) {
		this.name = name;
}
	public String getType() {
		return type;
}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	public static Boolean CreatePage(String name, String type,String category, String owner , int number_of_likes)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		//System.out.print(mail + friendM);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Pages",list.size() + 1);

		employee.setProperty("name", name);
		employee.setProperty("type", type);
		employee.setProperty("category", category);
		employee.setProperty("owner", owner);
		employee.setProperty("number_of_likes ", "0");
		
		datastore.put(employee);

		return true;}
	public static Boolean LikePage(String name, String type,String category, String owner , int number_of_likes)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		//System.out.print(mail + friendM);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Pages",list.size() + 1);

		employee.setProperty("name", name);
		employee.setProperty("type", type);
		employee.setProperty("category", category);
		employee.setProperty("owner", owner);
		employee.setProperty("number_of_likes ", "0");
		
		datastore.put(employee);

		return true;
	

	}
}
