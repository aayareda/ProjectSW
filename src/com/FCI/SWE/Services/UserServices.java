package com.FCI.SWE.Services;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.datanucleus.store.types.sco.backed.Vector;
import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.CreatePostEntity;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.SendMsgEntity;
import com.FCI.SWE.ServicesModels.SendMsgGroEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	@POST
	@Path("/logout")
	public String SignOut() {
		User.setCurrentActiveUser();
		JSONObject object = new JSONObject();
		object.put("status","ok");
		return object.toString();

	}

@POST
    @Path("/SendFriendRequest")
    public String SendFriendRequest(@FormParam("email") String userMail,@FormParam("password") String pass,
			@FormParam("friendMial") String friendemail) {
		JSONObject object = new JSONObject();
		System.out.print(userMail+pass+friendemail);
		if (!UserEntity.SendFriendRequest(userMail,friendemail)) {
			object.put("Status", "Failed");

		} else {
			object.put("usermail",userMail );
			object.put("friendmail", friendemail);
		}
		
		return object.toJSONString();
}
@POST
@Path("/ConfirmFriendRequest")
	public String ConfirmFriendRequest(@FormParam("email") String userMail,@FormParam("password") String pass,
		@FormParam("friendMial") String friendemail) {
	JSONObject object = new JSONObject();
	
	if (!UserEntity.ConfirmFriendRequest(userMail,friendemail)) {
		object.put("Status", "Failed");
		object.put("Status", "Failed");

	} else {
		
	
		object.put("usermail",userMail );
		object.put("friendmail", friendemail);
	}
	return object.toJSONString();}


/*@POST
@Path("/sendmessage")
public String sendmessage(@FormParam("email") String userMail,
		@FormParam("friendmail") String friendemail,@FormParam("message") String MSG) {
	JSONObject object = new JSONObject();
	System.out.print(userMail+MSG+friendemail);
	
	if (!UserEntity.sendmessage(userMail,friendemail,MSG)) {
		
		object.put("Status", "Failed");

	} else {
		object.put("email",userMail );
		object.put("friendmail", friendemail);
		object.put("message", MSG);
	}
	
	return object.toJSONString();
	
}*/
@POST
@Path("/ShowMesgService")
public String ShowMesgService(@FormParam("email1") String email1)
{
	JSONObject object = new JSONObject();
	SendMsgEntity F =new SendMsgEntity();
	SendMsgGroEntity m=new SendMsgGroEntity();
	String  Email2 = F.getUsers(email1);
//	String Users=m.getUsers(email1);
	String Msg=F.GetMsg(Email2,email1);
	Boolean MsgGr=m.saveGroupid();
	Boolean MSGgr =m.saveGroupchat();
	
	System.out.println(Msg);
	
	if (Email2=="Failed" ) {
		object.put("Status", "Failed");

	} else {
		
		object.put("Status", Msg);
	}
		
	return object.toJSONString();
}
@POST
@Path("/TimeLineService")
public String TimeLineService(@FormParam("email1") String email1) {
	
	JSONObject object = new JSONObject();
	CreatePostEntity request=new CreatePostEntity();
    Vector <CreatePostEntity> all=request.Allposts(email1);
    JSONArray returnedjeson=new JSONArray();
    for(CreatePostEntity al:all){
    	JSONObject object2=new JSONObject();
    	object2.put("owner" , al.getOwner());
    	System.out.println(al.getOwner());
    	object2.put("place" , al.getPlace());
    	System.out.println(al.getPlace());
    	object2.put("post"  , al.getPost());
    	System.out.println(al.getPost());
    	object2.put("like"  , al.getLikes());
    	System.out.println(al.getLikes());
    	System.out.println("-------------------------------------------------");
    	//returnedjeson.add(object2);
    	returnedjeson.put(object2);
    }
		return returnedjeson.toString();
		
		
}

@POST
@Path("/CReatPostService")
public String CReatPostService(@FormParam("uname") String uname
		,@FormParam("email1") String email1
		,@FormParam("fname") String fname
		,@FormParam("privacy") String privacy
		,@FormParam("feeling") String feeling) {
	uname="  Feeling"+" "+feeling+"\n"+uname;
	System.out.println(uname);
	JSONObject object = new JSONObject();
	CreatePostEntity request=new CreatePostEntity(uname,email1,fname,privacy);
		
		System.out.println(privacy);
		System.out.println(fname);
		if(request.savePost()==true){
			object.put("Status", "ok");
		}
		
		else object.put("Status", "Failed");
		return object.toString();
		
		
}


@POST
    @Path("/CraetePage")
    public String CreatePage(@FormParam("name") String name,@FormParam("type") String type,
			@FormParam("category") String category,@FormParam("owner") String owner,
			@FormParam("number_of_likes") int number_of_likes) {
		JSONObject object = new JSONObject();
		//System.out.print(userMail+pass+friendemail);
		if (!PageEntity.CreatePage(name,type,category,owner,number_of_likes)) {
			object.put("Status", "Failed");

		} else {
			object.put("name",name );
			object.put("type", type);
			object.put("category", category);
			object.put("owner", owner);
			object.put("number_of_likes",number_of_likes);
		}
		
		return object.toJSONString();
}

}