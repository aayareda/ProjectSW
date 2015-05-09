<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>send  message</title>
</head>
<body>

<form  action="/social/message" method="post">
 
 userMail : <input type="text" name="email" /> <br>
friendemail : <input type="text" name="friendmail" /> <br>
message : <input type="text" name="message" /> <br>
 
 <input type="submit" value="send message">
 
 
 

</form>


</body>

</html>