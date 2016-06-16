<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="GBK"%>
    
<% String gcd = request.getParameter("gcd");
	System.out.println(gcd);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form id="form" action="inputhidden.jsp">
<input type=hidden name=gcd value="4321">
<input type="submit" value="">
</form>
<input type="button" value="hidden_value" onclick="showValue()">


<form name="form1"> 
ÓÃ»§Ãû£º<input type=text name="username" id="username"> 
ÃÜÂë£º<input type=password name="password" id="pwd"> 
</form> 
<input type="button" value="form1" onclick="showform1()">

</body>
<script language="javascript" type="text/javascript">
function showValue(){
	var gcdValue = <%=gcd%>;
	alert(gcdValue);
}


function showform1(){
	var username = document.form1.username.value;
	var password = document.form1.password.value;
	alert(username+" "+password);
}

</script>
</html>