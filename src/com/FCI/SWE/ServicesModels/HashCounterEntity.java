
package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HashCounterEntity {
	private String HashName;
	private String counter;
	
public String getHashName() {
	return HashName;
}
public String getCounter() {
	return counter;
}
public HashCounterEntity() {
	// TODO Auto-generated constructor stub
}

public HashCounterEntity(String HashName,String counter) {
	this.counter=counter;
	this.HashName=HashName;
}


public  int getcounter(String HashName) {
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
    int counter=1;
	Query gaeQuery = new Query("HashCounter");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	for (Entity entity : pq.asIterable()) {
		if (entity.getProperty("HashName").toString().equals(HashName)) {
			counter++;
			int c=Integer.parseInt(entity.getProperty("counter").toString());
		   System.out.println(counter);
		   String v=counter+"";
			entity.setProperty("counter", v);
			datastore.put(entity);
		}
	}
	
	return counter;
}
	  
	public Boolean CreateShare() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashCounter");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity request = new Entity("HashCounter", list.size() + 1);

		request.setProperty("HashName", this.HashName);
		request.setProperty("counter", this.counter);
		if(datastore.put(request).isComplete())
			return true;
		else return false;

	}


}
