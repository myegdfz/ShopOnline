<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>购物车</title>

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
	<%@ include file="jsps/top.jsp"  %>
	<div class="wrapper-cart">
		<!-- 导航条 -->
		<div class="header-wrap">
			<a href="#" class="logo" title="商城"><img
				src="images/logo.png" alt=""></a>

			<div class="shopping_cart_procedure">
				<span>1、我的购物车</span><span> 2、填写订单</span><span
					style="width:175px; padding:0;">3、完成订单</span>
			</div>
		</div>
		<!-- /头部 -->

		<!-- 主体 -->
		<div class="wrapper-order">
			<div class="cartname">
				全部商品&nbsp;&nbsp;
			</div>
			<form action="tijiao"
				method="post" name="myform" id="form">
				
				<c:if test="${gwcGoodsList.size()==0 }">
					<div class="shopcart_main_none">
					<div class="shopcart_main_none_img"></div>
					<div class="shopcart_main_none_main">
					<p>你的购物车还是空的哦赶紧行动吧!</p>
					<a href="./index.jsp">马上购物</a>
					</div>
					</div>
				</c:if>
				<c:if test="${gwcGoodsList.size()!=0 }">				
<table id="table" class="gridtable">
	<thead>
		<tr>
			<th class="row-selected"><input id="selectAll" class="checkbox check-all"
				checked="checked" type="checkbox">全选</th>
			<th>商品名</th>
			<th>单价</th>
			<th>数量</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>		
<!-- 购物车循环go -->
<c:forEach items="${gwcGoodsList }" var="g">
<tr class="cart_bottom" id="ceng${g.id }">
	<td><input class="row-selected" value="${g.id }" type="checkbox" name="checkboxBtn" checked="checked"/></td>
	<td><span class="c5">
	<a href="goodsPageServlet?id=${g.id }" class="dl"> 
	<img src="${g.proPic }" width="70" height="70">
	</a>
	<span class="dd"><a href="goodsPageServlet?id=${g.id }" class="dd"> ${g.name }</a></span>
	<span class="dd"></span> </span>
	</td>

	<td align="center" id="${g.id }Subtotal">${g.price }</td> <!-- 单价 -->
	<td align="center">
		<div class="quantity-form">
			<a class="jia" gid="${g.id }">+</a>  <!-- 加一件商品 -->
			<input type="text" class="goodnum" readonly="readonly" id="${g.id }num" name="num[]" value="${g.num }">
			<a class="jian" gid="${g.id }">-</a> <!-- 减一件商品 -->
		</div>
	</td>

	<td><a class="del" gid="${g.id }" >删除</a>&nbsp;</td>
</tr>
</c:forEach>
<!-- 购物车循环end -->		
				</tbody>
				</table>
				<table class="cart_info">
					<tbody>
						<tr>
							<td colspan="4"><a name="62"
								rel="del"
								href="index.jsp">继续购物</a>
							</td>
							<td align="right"></td>
						</tr>
						<!-- <tr>
							<td colspan="5" align="right">种类：<span id="count">2</span>种
							</td>
						</tr> -->
						<tr>
							<td colspan="5" align="right">总计（不含运费）：¥<em class="price"
								id="total"> 0.00</em></td>
						</tr>
					</tbody>
					<tbody>
						<tr>
							<td colspan="5" align="right"><a id="jiesuan" class="btn_submit_pay"
								href="javascript:">去结算</a>
							</td>
						</tr>
					</tbody>
				</table>
				</c:if>
			</form>
			<!-- jQuery 遮罩层 begin -->
			<div id="fullbg"></div>
			<!-- end jQuery 遮罩层 -->
			<!-- 对话框 -->
			<div id="dialog">
				<!-- Modal 对话框内容 -->
				<div id="myModal" class="modal">

					<div class="modal-header">
						<a class="close" onclick="closeBg();"><img
							src="images/close.png"></a>
						<h3 id="myModalLabel">用户登录</h3>
					</div>
					<div class="m_img">
						<a class=""
							href="#"><img
							src="images/qq.png">&nbsp;用QQ账号登录</a><br>
						<br> <a class=""
							href="#"><img
							src="images/weibo.png">&nbsp;用微博账号登录</a>
					</div>
					<form class="form-horizontal" id="loginform" name="user"
						method="post">

						<div class="form_login">
							<input type="text" id="inputusername" name="username"
								placeholder="用户名">

						</div>
						<div class="form_login">
							<input class="v_yerhop" type="password" id="inputpassword"
								name="password" placeholder="密码">
						</div>
						<span class="tips"></span>
						<div class="form_login">
							<a id="login_btn" value="">登录</a>
						</div>
						<div class="control-group">
							<p>
								<span class="pull-right"><span>还没有账号? <a
										href="rege.jsp"
										class="now">立即注册</a></span> </span>
							</p>
						</div>
					</form>
				</div>
				<!-- Modal 对话框内容 -->
			</div>
			<!-- 对话框 结束-->
			<!-- jQuery 遮罩层上方的对话框 -->
			<script type="text/javascript">
			$(function() {
				$("#jiesuan").click(checklogin); //添加点击事件
				showTotal();//计算总计
				/*
				给全选添加click事件
				*/
				$("#selectAll").click(function() {
					/*
					1. 获取全选的状态
					*/
					var bool = $("#selectAll").attr("checked");
					/*
					2. 让所有条目的复选框与全选的状态同步
					*/
					setItemCheckBox(bool);
					/*
					3. 让结算按钮与全选同步
					*/
					setJieSuan(bool);
					/*
					4. 重新计算总计
					*/
					showTotal();
				});
				/*
				给所有条目的复选框添加click事件
				*/
				$(":checkbox[name=checkboxBtn]").click(function() {
					var all = $(":checkbox[name=checkboxBtn]").length;//所有条目的个数
					var select = $(":checkbox[name=checkboxBtn][checked=true]").length;//获取所有被选择条目的个数

					if(all == select) {//全都选中了
						$("#selectAll").attr("checked", true);//勾选全选复选框
						setJieSuan(true);//让结算按钮有效
					} else if(select == 0) {//谁都没有选中
						$("#selectAll").attr("checked", false);//取消全选
						setJieSuan(false);//让结算失效
					} else {
						$("#selectAll").attr("checked", false);//取消全选
						setJieSuan(true);//让结算有效				
					}
					showTotal();//重新计算总计
				});
				$(".del").click(function(){
					// 获取cartItemId
					var id = $(this).attr("gid");
					$.ajax({
						type:'post', //传送的方式,get/post
						url:'delCart.goods', //发送数据的地址
						data:{sort:id},
						 dataType: "json",
						success:function(data)
						{
							if(data.st==1){
								var html="<div class='shopcart_main_none'><div class='shopcart_main_none_img'></div><div class='shopcart_main_none_main'><p>你的购物车还是空的哦赶紧行动吧!</p><a  href='/index.php?s=/Home/Index/index.html'>马上购物</a></div>";
								if(data.sum==0){$(".text").remove();$("#form").html(html);}else{
									$("#ceng"+id).remove();
								}
							}
						},
						error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
						alert(XMLHttpRequest+thrownError); }
								});
					
				});
				/*
				给减号添加click事件
				*/
				$(".jian").click(function() {
					// 获取cartItemId
					var id = $(this).attr("gid");
					// 获取输入框中的数量
					var num = $("#" + id + "num").val();
					// 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
					if(num == 1) {
						//最低为1
					} else {
						//减一个数量
						$.ajax({
						type:'post', //传送的方式,get/post
						url:'jianCart.goods', //发送数据的地址
						data:{sort:id},
						 dataType: "json",
						success:function(data)
						{
							if(data.st==1){
								$("#" + id + "num").val(num-1);
								showTotal();
							}
						},
						error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
						alert(XMLHttpRequest+thrownError); }
								});
					}
				});
				
				// 给加号添加click事件
				$(".jia").click(function() {
					// 获取cartItemId
					var id = $(this).attr("gid");
					// 获取输入框中的数量
					var num = $("#" + id + "num").val();
					// 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
					
					
					
					
					//加一个数量
					$.ajax({
					type:'post', //传送的方式,get/post
					url:'jiaCart.goods', //发送数据的地址
					data:{sort:id},
					 dataType: "json",
					success:function(data)
					{
						if(data.st==1){
							$("#" + id + "num").val(Number(num)+1);
							showTotal();
						}
					},
					error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
					alert(XMLHttpRequest+thrownError); }
							});
					
					
					
					
					
					
				});
			
			});
			function round(num,dec){ 
			    var strNum = num + '';/*把要转换的小数转换成字符串*/
			    var index = strNum.indexOf("."); /*获取小数点的位置*/
			    if(index < 0) {
			        return num;/*如果没有小数点，那么无需四舍五入，返回这个整数*/
			    }
			    var n = strNum.length - index -1;/*获取当前浮点数，小数点后的位数*/
			    if(dec < n){ 
			    	/*把小数点向后移动要保留的位数，把需要保留的小数部分变成整数部分，只留下不需要保留的部分为小数*/ 
			        var e = Math.pow(10, dec);
			        num = num * e;
			        /*进行四舍五入，只保留整数部分*/
			        num = Math.round(num);
			        /*再把原来小数部分还原为小数*/
			        return num / e;
			    } else { 
			        return num;/*如果当前小数点后的位数等于或小于要保留的位数，那么无需处理，直接返回*/
			    } 
			} 
			/*
			 * 统一设置所有条目的复选按钮
			 */
			function setItemCheckBox(bool) {
				$(":checkbox[name=checkboxBtn]").attr("checked", bool);
			}
			/*
			 * 计算总计
			 */
			function showTotal() {
				var total = 0;
				/*
				1. 获取所有的被勾选的条目复选框！循环遍历之
				*/
				$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
					//2. 获取复选框的值，即其他元素的前缀
					var id = $(this).val();
					//3. 再通过前缀找到小计元素，获取其文本
					var text = $("#" + id + "Subtotal").text();
					//找到数量
					var num = $("#" + id + "num").val();
					
					
					//4. 累加计算
					total += text*num;
				});
				// 5. 把总计显示在总计元素上
				$("#total").text(round(total, 2));//round()函数的作用是把total保留2位
			}
			
			/*
			 * 设置结算按钮样式
			 */
			function setJieSuan(bool) {
				if(bool) {
					$("#jiesuan").removeClass("btn_submit_pay_kill").addClass("btn_submit_pay");
					$("#jiesuan").click(checklogin);
				} else {
					$("#jiesuan").removeClass("btn_submit_pay").addClass("btn_submit_pay_kill");
					$("#jiesuan").unbind("click");//撤消当前元素止所有click事件
				}
				
			}
			/*
				结算
			*/
			function checklogin() {
				var uexist="${username }";
				if(uexist){
				// 1. 获取所有被选择的条目的id，放到数组中
				var cartItemIdArray = new Array();
				$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
					cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
				});	
				// 2. 把数组的值toString()，然后赋给表单的cartItemIds这个hidden
				$("#cartItemIds").val(cartItemIdArray.toString());
				// 把总计的值，也保存到表单中
				$("#hiddenTotal").val($("#total").text());
				// 3. 提交这个表单
				$("#jieSuanForm").submit();
				}else{
					showBg();
					
				}
					 }
				//显示灰色 jQuery 遮罩层
				function showBg() {
					var bh = $("body").height();
					var bw = $("body").width();
					$("#fullbg").css({
						height : bh,
						width : bw,
						display : "block"
					});
					$("#dialog").show();
				}
				//关闭灰色 jQuery 遮罩
				function closeBg() {
					$("#fullbg,#dialog").hide();
				}
			</script>
			<script type="text/javascript">
				//登录后刷新页面，载入数据
				$("#login_btn")
						.click(
								function() {

									var yourname = $('#inputusername').val();
	var yourword=$('#inputpassword').val();
		
	$.ajax({
		type : 'post', //传送的方式,get/post
		url : 'login.user', //发送数据的地址
		data : {
			username : yourname,
			password : yourword
		},
		dataType : "json",
		success : function(data) {
			if (data.status == "1") {
				$(".tips").html(
				data.info);
				window.location
				.reload();
				$("#uid").val(data.uid);
			} else {
				$(".tips").html(data.info);
			}

		},
		error : function(event,
			XMLHttpRequest,
			ajaxOptions,
			thrownError) {
				alert(XMLHttpRequest+ thrownError);
			}
		});
	});
	</script>


		</div>
		<!-- /主体 -->
		<!-- 底部 -->
		<!-- 底部-->
		<script type="text/javascript" charset="utf-8"
			src="js/menudown.js"></script>

	</div>
<%@ include file="jsps/footer.jsp" %>
	<!-- 隐藏的提交表单 -->
	<form id="jieSuanForm" action="tijiao.cart" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="method" value="loadCartItems"/>
	</form>
	<!-- /底部 -->
</body>
</html>