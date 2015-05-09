package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.apphosting.api.DatastorePb.Transaction;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12c
 */
public class UserEntity {
	private String name;
	private static String email;
	private String password;
	private long id;
	private String active;
	private static String friendMial;
	private String message;
	private String Groupid;
	private String list;
	private String receiver;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public static String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("users", list.size() + 1);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);

		return true;

	}
	public static Boolean SendFriendRequest(String mail, String friendM) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		System.out.print(mail + friendM);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("requests",list.size() + 1);

		employee.setProperty("usermail", mail);
		employee.setProperty("friendmail", friendM);
		employee.setProperty("Status", "binding");
		datastore.put(employee);

		return true;

	}
	public static Boolean ConfirmFriendRequest(String mail, String friendM) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Friends");
		System.out.print(mail + friendM);
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("Friends",list.size() + 1);

		employee.setProperty("usermail", mail);
		employee.setProperty("friendmail", friendM);
		employee.setProperty("Status","friends");
		datastore.put(employee);

		return true;

	}
	/*public static Boolean sendmessage(String userMail, String friendmail,String MSG) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messages");
		System.out.print(userMail + friendmail + MSG);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("msgs",list.size() + 1);

		employee.setProperty("email", userMail);
		employee.setProperty("friendmail", friendmail);
		employee.setProperty("message", MSG);
		datastore.put(employee);

		return true;

	}*/
	public Boolean saveGroupmessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = (Transaction) datastore.beginTransaction();
		Query gaeQuery = new Query("Groupmessage");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		Entity employee = new Entity("Groupmessage", list.size() + 2);

		employee.setProperty("Sender", this.name);
		employee.setProperty("Groupid", this.Groupid);
		employee.setProperty("Status", this.password);
		
		datastore.put(employee);
		
		
		return true;
	}
	
	
	//-----------------------------------------------------------------------------------------------------------
		/**
		 * @return the groupid
		 */
	public String getGroupid() {
			return Groupid;
		}
		/**
		 * @param groupid the groupid to set
		 */
		public void setGroupid(String Groupid) {
			this.Groupid = Groupid;
		}

}
