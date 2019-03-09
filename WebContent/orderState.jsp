<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提交成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<link href="css/top.css" rel="stylesheet" />
	<link href="css/footer.css" rel="stylesheet" />
	<link href="css/common.css" rel="stylesheet" />
	
	<script type="text/javascript" src="js/public.js"></script>
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>

  </head>
  
  <body>
    <!-- 工具条 -->
	<!-- 顶部工具条 -->



<div class="top">
<div class="topbar">
<span class="welcome" style="float:left">欢迎光临商城系统 <a href="" class="red">${username }</a>,<a rel="nofollow" href="logout.user">退出</a> </span> 
	  <span class="operate_nav">	
		<span id="account"><a rel="nofollow">我的账号&nbsp;</a><i id="icount" class="fa fa-angle-down"></i> </span>
			<ul id="dbox" class="top_lg_info_down" style="display:none;">
				 <li><a rel="nofollow" href="userMain.page">个人中心</a></li>
				 <li><a rel="nofollow" href="MyCollect.page">我的收藏</a></li>
				 <li><a rel="nofollow" href="changePassword.page">修改密码</a></li>
			</ul>		  
  
	|</span>		  
	<span class="operate_nav">	 
 <a rel="nofollow" href="">我的订单</a>
| </span>		
 <span class="operate_nav">	 
<a href="javascript:AddToShoppingCart(0);" name="购物车" dd_name="购物车"><i class="icon_card"></i>购物车<b id="cart_items_count"></b></a> </span>
 </div>
 </div><script type="text/javascript">
//头部topbar会员中心显示与隐藏
var Account= document.getElementById('account');
            var Downmenu= document.getElementById('dbox');
            var timer = null;//定义定时器变量
            //鼠标移入div1或div2都把定时器关闭了，不让他消失
            Account.onmouseover = Downmenu.onmouseover = function ()
            {
				 //改变箭头方向
				$("i#icount").attr("class","fa fa-angle-up");
               
				 //改变背景颜色
				 Account.style.backgroundColor = '#fff';
				 //显示下拉框
                $("#dbox").show();
				//关闭定时执行
                clearTimeout(timer);
            }
			
            //鼠标移出div1或div2都重新开定时器，让他延时消失
            Account.onmouseout = Downmenu.onmouseout = function ()
            {
				$("i#icount").attr("class","fa fa-angle-down");
				Account.style.backgroundColor = '#F5F5F5';
				 //开定时器，每隔200微妙下拉框消失
                timer = setTimeout(function () { 
                    $("#dbox").hide(); }, 200);
            }
       
		
		</script>
		<!-- 顶部工具条 结束 -->
	<!-- /工具条 -->
	<!-- 头部 -->
	<div class="wrapper-cart">
	         <!-- 导航条 -->



<div class="header-wrap"><a href="http://localhost/zo_omyshop" class="logo" title="shop商城系统"><img src="images/logo.png" alt=""></a>

<div class="shopping_cart_procedure3"><span>1、我的购物车</span><span>   2、填写订单</span><span style="width:175px; padding:0;">3、完成订单</span>  </div>
</div>
	<!-- /头部 -->
	
	<!-- 主体 -->
	<div class="wrapper-order">

 
<div id="success">

<p><i class="glyphicon glyphicon-ok"></i><strong style="color:#35AA47;clear:both;font-size:18px;">提交成功！</strong></p>
<p><a class="btn_success" rel="nofollow" href="/zo_omyshop">继续购物</a><a class="btn_success" rel="nofollow" href="userMain.page">查看订单</a></p></div>

<!--脚本开始--> 
    
   

 </div>
	<!-- /主体 -->

	<!-- 底部 -->
	
    <!-- 底部-->
</div>
 <%@ include file="jsps/footer.jsp" %> 
  </body>
</html>
