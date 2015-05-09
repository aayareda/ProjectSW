package com.FCI.SWE.Controller;

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

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application??
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	@GET
	@Path("/logout")
	public Response logout() {
		User.setCurrentActiveUser();
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	
	 @GET
	  
	 @Path("/search") public Response search(){ return Response.ok(new
	  Viewable("/jsp/search")).build(); }
	 

	@GET
	@Path("/SendFriendRequest")
	public Response SendFriendRequest() {

		return Response.ok(new Viewable("/jsp/sendfriendrequest")).build();

	}
	
	@POST
	@Path("/makerequest")
	public Response SendFriendRequest(@FormParam("email") String userMail,
			@FormParam("password") String pass,
			@FormParam("friendMial") String friendemail) {
		System.out.print(userMail+pass+friendemail);
		String urlParameters = "friendMial=" + friendemail + "&email=" + userMail +"&password=" +pass;
		
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/SendFriendRequest", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/entryPoint")).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		

	}

	@GET
	@Path("/ConfirmFriendRequest")
	public Response ConfirmFriendRequest() {

		
		return Response.ok(new Viewable("/jsp/addfriend")).build();

	}

	
	
	@POST
	@Path ("/Confirm")
	public Response ConfirmFriendRequest(@FormParam("email") String userMail,@FormParam("password") String pass,
			@FormParam("friendMial") String friendemail) {
String urlParameters = "FriendMial=" + friendemail + "&email=" + userMail;
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/ConfirmFriendRequest", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/home")).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
		
	}
	@GET
	@Path("/sendmessage")
	public Response sendmessage() {

		return Response.ok(new Viewable("/jsp/sendmessage")).build();

	}
	
	/*@POST
	@Path("/message")
	public Response sendmessage(@FormParam("email") String userMail,
			@FormParam("message") String MSG,
			@FormParam("friendmail") String friendemail) {
		System.out.print(userMail+MSG+friendemail);
		String urlParameters = "friendmial=" + friendemail + "&email=" + userMail +"&message=" +MSG;
		
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendmessage", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/entryPoint")).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		

	}
*/
@POST
	@Path("/ShowConvMsg")
	public Response ShowConvMsg (@FormParam("CName") String CName) throws ParseException
	{
		User user = User.getCurrentActiveUser();
		String email1=user.getEmail();
		
		String urlParameters = "email1=" + email1+"&CName=" + CName;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/ShowConvMsgService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;

			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			if (object.get("Status").toString().equals("Failed"))
			return Response.ok(new Viewable("/jsp/home2")).build();
			else 
			{
				
				Map<String, String> map = new HashMap<String, String>();

				map.put("MSG", object.get("Status").toString());
				return Response.ok(new Viewable("/jsp/ShowConvMsg", map)).build();
				
			}
	}

	

	@GET
	@Path("/notifications")
	public Response acceptFriendPage()
	{
		return Response.ok(new Viewable("/jsp/notifications")).build();
		
	}
	
	
	@GET
	@Path("/CreatePage")
	public Response CreatePage() {

		return Response.ok(new Viewable("/jsp/CreatPage")).build();

	}
	@POST
	@Path("/makepages")
	public Response SendFriendRequest(@FormParam("name") String name,@FormParam("type") String type,
			@FormParam("category") String category,@FormParam("owner") String owner,
			@FormParam("number_of_likes") int number_of_likes) {
		//System.out.print(userMail+pass+friendemail);
		String urlParameters = "name=" + name + "&type=" + type +"&category=" +category+"&number_of_likes"+number_of_likes
				+"&owner"+owner;
		
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/CreatePage", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok(new Viewable("/jsp/pagesucsess")).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		

	
	
	
	
	

	
	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {

		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
	@POST
	@Path("/CReatPost")
	public Response CReatPost (@FormParam("uname") String uname
	,@FormParam("fname") String fname
	,@FormParam("privacy") String privacy
	,@FormParam("feeling") String feeling) throws ParseException
	{

	User user = User.getCurrentActiveUser();
	String email1=user.getEmail();
	String urlParameters = "email1=" + email1+"&uname=" + uname
	+"&fname=" + fname+"&privacy="+privacy+"&feeling="+feeling;

	String retJson = Connection.connect(
	"http://localhost:8888/rest/CReatPostService", urlParameters,
	"POST", "application/x-www-form-urlencoded;charset=UTF-8");

	JSONParser parser = new JSONParser();
	Object obj;

	obj = parser.parse(retJson);
	JSONObject object = (JSONObject) obj;

	if (object.get("Status").toString().equals("Failed"))
	return Response.ok(new Viewable("/jsp/CreatePost")).build();
	else 
	{
	return Response.ok(new Viewable("/jsp/Timeline")).build();

	}
	}

	/*@POST
	@Path("/sendMessage")
	@Produces("text/html")
	public String sendMessage (@Context HttpServletRequest Message ,@FormParam("uname") String uname,@FormParam("message") String message_text) {
		String serviceUrl = "http://localhost:8888/rest/sendMessageService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = Message.getSession(true);
			
			String urlParameters = "message=" +message_text + "&uname=" + uname + "&currentUser=" + session.getAttribute("name");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "Failed";
			if (object.get("Status").equals("ok "))
				return "";
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Your message  successfully sent";

	}
	*/
	/*
	 * @POST
	 * 
	 * @Path("/checkRequests")
	 * 
	 * @Produces("text/html") public Response checkRequests(@FormParam("Fname")
	 * String Fname ) { String urlParameters2 = "Fname="+ Fname;
	 * 
	 * String retJson2 = Connection.connect(
	 * "http://localhost:8888/rest/AcceptFriendRequests", urlParameters2,
	 * "POST", "application/x-www-form-urlencoded;charset=UTF-8");
	 * 
	 * JSONParser parser2 = new JSONParser(); Object obj2 = null ; try { obj2 =
	 * parser2.parse(retJson2); JSONObject object = (JSONObject) obj2; if
	 * (object.get("Status").equals("Failed")) return null;
	 * 
	 * } catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } //JSONObject object = (JSONObject) obj2;
	 * 
	 * 
	 * return Response.ok(new Viewable("/jsp/accepted")).build();
	 * 
	 * }
	 * 
	 * @POST
	 * 
	 * @Path("/friendrequest") //@Produces("text/html") public Response
	 * friendrequest(@FormParam("FriendMail") String FMail) {
	 * 
	 * String urlParameters = "FriendMail=" + FMail;
	 * 
	 * String retJson = Connection.connect(
	 * "http://localhost:8888/rest/SendFriendRequest", urlParameters, "POST",
	 * "application/x-www-form-urlencoded;charset=UTF-8"); JSONParser parser =
	 * new JSONParser(); Object obj; try { obj = parser.parse(retJson);
	 * JSONObject object = (JSONObject) obj; if
	 * (object.get("Status").equals("Failed")) return null;
	 * 
	 * return Response.ok(new Viewable("/jsp/home")).build(); } catch
	 * (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * /* UserEntity user = new UserEntity(uname, email, pass); user.saveUser();
	 * return uname;
	 */

}