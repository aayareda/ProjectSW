package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HashEntity {
	private String HashName;
	private String PostID;
	
public String getHashName() {
	return HashName;
}
public String getPostID() {
	return PostID;
}
public HashEntity() {
	// TODO Auto-generated constructor stub
}

public HashEntity(String HashName,String PostID) {
	this.HashName=HashName;
	this.PostID=PostID;
}
	  
	public Boolean CreateShare() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashTag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity request = new Entity("HashTag", list.size() + 1);

		request.setProperty("HashName", this.HashName);
		request.setProperty("PostID", this.PostID);
		if(datastore.put(request).isComplete())
			return true;
		else return false;

	}


}
