package com.FCI.SWE.ServicesModels;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class CreatePostEntity {
	private String post;
	private String owner;
	private String place;
	private String Privacy;
	private String likes;
	private String Feeling;
	
	public CreatePostEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public CreatePostEntity(String post,String owner,String place
			,String Privacy){
		this.owner=owner;
		this.place=place;
		this.Privacy=Privacy;
		this.post=post;
		this.likes=0+"";
	}
	public CreatePostEntity(String post,String owner,String place
			                ,String Privacy,String likes){
		this.likes=likes;
		this.owner=owner;
		this.place=place;
		this.post=post;
		this.Privacy=Privacy;
	}
	
	public String getPrivacy() {
		return Privacy;
	}
	public String getOwner() {
		return owner;
	}
	public String getPlace() {
		return place;
	}
	public String getPost() {
		return post;
	}
	public String getLikes() {
		return likes;
	}
	
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public void setPrivacy(String privacy) {
		Privacy = privacy;
	}
	
	
	public Boolean savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("CreatePost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity request = new Entity("CreatePost", list.size() + 1);

		request.setProperty("post", this.post);
		request.setProperty("owner", this.owner);
		request.setProperty("Privacy",this.Privacy);
		request.setProperty("feeling",this.Feeling);
		
		System.out.println("ppppp");

		if(this.place.equals("")){
			request.setProperty("place","0");
		}
		else{
			request.setProperty("place",this.place);
		}
		request.setProperty("likes",this.likes);
		
		
		if(datastore.put(request).isComplete()){
			String postID=Long.toString(request.getKey().getId());
			//String postID=request.getProperty("ID/Name").toString();
			ShareEntity obj=new ShareEntity(postID, 0+"");
			//String all[]=this.post.split(" ");
			for(int i=0; i<this.post.length(); i++){
				if(i==0 && this.post.charAt(i)=='#'){
					String word="";
					while( this.post.charAt(i)!=' '){
						word+=this.post.charAt(i);
						i++;
					}
					HashEntity hash=new HashEntity(word, postID);
					boolean check=hash.CreateShare();
					if(check==true){
						HashCounterEntity hashcounter=new HashCounterEntity();
						int counter=hashcounter.getcounter(word);
						hashcounter=new HashCounterEntity(word,counter+"");
						hashcounter.CreateShare();
					}
					
				}
				
				else if(this.post.charAt(i)=='#' && this.post.charAt(i-1)==' '){
					String word="";
					String d=" ";
					while(this.post.charAt(i)!='.' && i<this.post.length()){
						word+=this.post.charAt(i);
						i++;
					}
					
					HashEntity hash=new HashEntity(word, postID);
					boolean check=hash.CreateShare();
					
					if(check==true){
						HashCounterEntity hashcounter=new HashCounterEntity();
						int counter=hashcounter.getcounter(word);
						if(counter==1){
						hashcounter=new HashCounterEntity(word,counter+"");
						check=hashcounter.CreateShare();
						}
					}
					
				}
				
			}
			
			return true;
		}
		else return false;

	}
	
	public static  CreatePostEntity getjsonpost(String json){
		JSONParser parse=new JSONParser();
		try {
			JSONObject object=(JSONObject) parse.parse(json);
			CreatePostEntity post=new CreatePostEntity();
			post.setOwner(object.get("owner").toString());
			post.setPlace(object.get("place").toString());
			post.setPlace(object.get("post").toString());
			post.setLikes(object.get("like").toString());
			
			return post;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
      public Vector<CreatePostEntity>Allposts(String email){
		Vector<CreatePostEntity>post=new Vector<>();
		FriendReqEntity AllFriend=new FriendReqEntity();
		Vector<String>Friends=AllFriend.getUser(email, email);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("CreatePost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			
			if (Friends.contains(entity.getProperty("owner").toString()))
			{	
				if(entity.getProperty("Privacy").toString().equals("public")||
				  entity.getProperty("Privacy").toString().equals("private")){ 
					post.add(new CreatePostEntity(entity.getProperty("post").toString(), 
							entity.getProperty("owner").toString(), 
							entity.getProperty("place").toString(), 
							entity.getProperty("Privacy").toString(), 
							entity.getProperty("likes").toString()));
				}
				
				else{
					String All=entity.getProperty("Privacy").toString();
					String s[]=All.split(" ");
					for(int i=0;i<s.length;i++){
						if(s[i].equals(email)){
							post.add(new CreatePostEntity(entity.getProperty("post").toString(), 
									entity.getProperty("owner").toString(), 
									entity.getProperty("place").toString(), 
									entity.getProperty("Privacy").toString(), 
									entity.getProperty("likes").toString()));
							break;
						}
					}
				}
				
                   
					 
					 }
			else if (entity.getProperty("owner").toString().equals(email)){
				post.add(new CreatePostEntity(entity.getProperty("post").toString(), 
						entity.getProperty("owner").toString(), 
						entity.getProperty("place").toString(), 
						entity.getProperty("Privacy").toString(), 
						entity.getProperty("likes").toString()));
				
			}
			
					 }
		return post;
		
	}

}
