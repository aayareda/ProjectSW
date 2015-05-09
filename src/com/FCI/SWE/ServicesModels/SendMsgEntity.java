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

public class SendMsgEntity {
	private String CName;
	private String Sender;
	private String text;
//	private long id;

	
	public SendMsgEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public SendMsgEntity(String CName,String Sender,String text) {
		this.CName=CName;
		this.Sender=Sender;
		this.text=text;
	}
	
	public String getCName() {
		return CName;
	}
	
	public String getText() {
		return text;
	}
	
	public String getSender() {
		return Sender;
	}
	public static boolean SendMsg(String CName, String email, String msg) {
		boolean check=false;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
        
		Query gaeQuery = new Query("SendMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("CName").toString().equals(CName)&&
					entity.getProperty("Sender").toString().equals(email))
					{
				  String append=entity.getProperty("text")+"  "+msg;
				  entity.setProperty("text", append);
				  datastore.put(entity);
				  check=true;
				
	                }
}
		if(check == true){
			return true;
		}
		
		else if(check==false){
			SendMsgEntity request=new SendMsgEntity(CName, email, msg);	
			if(request.SaveMsg()){
				check=true;
				return true;
				
			}
		}
		
		return check;
	
}
	
	public static String GetMsg(String CName, String email) {
		boolean check=false;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
        
		Query gaeQuery = new Query("SendMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("CName").toString().equals(CName))
					{
				  String append=entity.getProperty("text").toString();
				  return append;
	                }
                          }
	
		
		return "Failed";
	
}
	
	public Boolean SaveMsg() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("SendMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity request = new Entity("SendMsg", list.size() + 1);

		request.setProperty("CName"  , this.CName);
		request.setProperty("Sender" , this.Sender);
		request.setProperty("text"   , this.text);
		if(datastore.put(request).isComplete())
			return true;
		else return false;

	}

}

