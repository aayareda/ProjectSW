
 <%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<form action="/social/CReatPost" method="post">
  post         : <input type="text" name="uname" /> <br><br>
  Friend       : <input type="text" name="fname" /> <br><br>
  Post privacy : <input type="text" name="privacy" /> <br><br>
  Feeling      : <select name="feeling" multiple>
                 <option value="Happy">Happy</option>
                 <option value="Happy">loved</option>
                 <option value="Happy">sad</option>
                 <option value="Happy">sick</option>
                 <option value="Happy">proud</option>
                 <option value="Happy">angry</option>
                 <option value="Happy">hopeful</option>
                 <option value="Happy">crazy</option>
                 </select><br><br>
  <input type="submit" value=" Post ">
  </form>

</body>
</html>
 

</form>
</body>

</html>