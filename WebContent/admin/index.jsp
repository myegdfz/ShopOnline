<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/adminLogin.css" rel="stylesheet" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <script type="text/javascript">
  	window.onload=function(){
  	var state = "${state }";
	  	if(state==1){
	  		alert("验证码输入有误！");
	  	}else if(state==2){
	  		alert("密码错误或用户不存在！");
	  	}
  	}
  </script>
  <c:remove var="state" scope="session"/>
    <div class="login">
		
		<div class="input">
		<form action="adminLogin.user" method="post">
			<div class="log">
				<div class="name">
					<label>用户名</label><input type="text" class="text" id="value_1" placeholder="用户名" name="username" tabindex="1">
				</div>
				<div class="pwd">
					<label>密　码</label><input type="password" class="text" id="value_2" placeholder="密码" name="password" tabindex="2">
					<label>验证码</label><input type="text" class="text" id="value_1" placeholder="验证码" name="jyzm" tabindex="3" style="margin-top: 10px;width: 100px;"><img id="CreateCheckCode" src="genImage.yzm" onclick="changeImage()">
					<input type="submit" class="submit" tabindex="3" value="登录">
					<div class="check"></div>
				</div>
				
			</div>
		</form>
		</div>
	</div>

</div>


  </body>
</html>