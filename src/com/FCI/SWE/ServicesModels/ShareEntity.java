package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ShareEntity {
	private String NumOfShare;
	private String PostID;
	
 public ShareEntity() {
	// TODO Auto-generated constructor stub
}
 
 public ShareEntity(String NumOfShare,String PostID) {
		this.NumOfShare=NumOfShare;
		this.PostID=PostID;
	}
 
 public String getNumOfShare() {
	return NumOfShare;
}
 public String getPostID() {
	return PostID;
}
 
	  
	public Boolean CreateShare() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Shareapost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity request = new Entity("Shareapost", list.size() + 1);

		request.setProperty("NumOfShare", this.NumOfShare);
		request.setProperty("PostID", this.PostID);
		if(datastore.put(request).isComplete())
			return true;
		else return false;

	}

}
