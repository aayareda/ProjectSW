package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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
//import com.google.appengine.repackaged.com.google.common.base.Receiver;
import com.google.apphosting.api.DatastorePb.Transaction;
public class SendMsgGroEntity {
	
	private String Groupid;
	private String receiver;
	//private Vector <receiver> receivers=new Vector<>();
	private String Sender;
	private String text;
	private String CName;
	private String list;
	public SendMsgGroEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public SendMsgGroEntity(String CName,String Sender,String text) {
		this.CName=CName;
		this.Sender=Sender;
		this.text=text;
	}public void setText(String text) {
		this.text = text;
	}public void setSender(String sender) {
		Sender = sender;
	}public void setReceiver(String receiver) {
		this.receiver = receiver;
	}public void setList(String list) {
		this.list = list;
	}public void setGroupid1(String groupid) {
		Groupid = groupid;
	}public void setCName(String cName) {
		CName = cName;
	}public String getReceiver() {
		return receiver;
	}public String getGroupid() {
		return Groupid;
	}public String getList() {
		return list;
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
	
	//------------------------------------------------------------------------------------------------------
	public Boolean saveGroupid() {
		List<com.FCI.SWE.Models.User> UserList= new ArrayList<com.FCI.SWE.Models.User>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		com.google.appengine.api.datastore.Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Groupschat");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		
		
  
        
        	for (int i=0 ; i< UserList.size(); i++)
        	{
        		Entity employee = new Entity("Groupschat", list.size() + 2);	
        		employee.setProperty("Groupid", this.Groupid);
        		employee.setProperty("Users", UserList.get(i).getName());
        		datastore.put(employee);
        	}
        
		//employee.setProperty("Groupid", this.Groupid);
		//employee.setProperty("Users", UserList.get(i).getname());
		
		
		txn.commit();
		
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	//-------------------------------------------------------------------------------------------------
	public Boolean saveGroupchat() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
	//	Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Groupschat");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		Entity employee = new Entity("Groupschat", list.size() + 2);

		employee.setProperty("Sender", this.getCName());
		employee.setProperty("receiver",employee.getKey().getId());
		employee.setProperty("message", this.text );
		
		datastore.put(employee);
	
		
	
		return true;
	}
	
}

