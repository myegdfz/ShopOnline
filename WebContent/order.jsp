<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单结算</title>
    
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
    <span class="operate_nav"> <span id="account" style="background-color: rgb(245, 245, 245);"><a rel="nofollow">我的账号&nbsp;</a><i id="icount" class="fa fa-angle-down"></i> </span> 
     <ul id="dbox" class="top_lg_info_down" style="display: none;"> 
      <li><a rel="nofollow" href="#">个人中心</a></li> 
      <li><a rel="nofollow" href="#">我的收藏</a></li> 
      <li><a rel="nofollow" href="#">优惠券</a></li> 
     </ul> |</span> 
    <span class="operate_nav"> <a rel="nofollow" href="">我的订单</a> | </span> 
    <span class="operate_nav"> <a href="javascript:AddToShoppingCart(0);" name="购物车" dd_name="购物车"><i class="icon_card"></i>购物车<b id="cart_items_count"></b></a> </span> 
   </div> 
  </div>
  <script type="text/javascript">
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
  <div class="wrapper-address"> 
   <!-- 导航条 --> 
   <div class="header-wrap">
    <a href="/zo_omyshop" class="logo" title="商城系统"><img src="images/logo.png" alt="" /></a> 
    <div class="shopping_cart_procedure2">
     <span>1、我的购物车</span>
     <span> 2、填写订单</span>
     <span style="width:175px; padding:0;">3、完成订单</span> 
    </div> 
   </div> 
   <!-- /头部 --> 
   <!-- 主体 --> 
   <div class="wrapper-order"> 
    <div id="check"> 
     <div class="mt"> 
      <h2>填写并核对订单信息</h2> 
     </div> 
     <script type="text/javascript">

	$(document).ready(function(){

  $("#show").click(function(){
  $("#formsender").toggle();

  });
});
function savemsg() { 
//判断是否是默认地址 
if (document.getElementById("isdefault").checked==true) {
       var info="1"; //是默认
    }
    else {
      var info="0"; //不是默认
    }
    var userName = "${username }";
    var pca=$("#idprovince").val()+$("#idcity").val()+$("#idarea").val();
	var address=$("#address").val();
	var orderid=$("#orderid").val();
	var username=$("#realname").val();
	var phone=$("#phone").val();
	var b=$("#youbian").val();
	
	$.ajax({
type:'post', //传送的方式,get/post
url:'add.address', //发送数据的地址
data:{province:$("#idprovince").val(),city:$("#idcity").val(),area:$("#idarea").val(),posi:address,pho:phone,rel:username,userName:userName,msg:info},
 dataType: "json",
success:function(data)
{
	if(data.success){
		if(info==1){
			var str='<p><input checked="checked" type="radio" name="sender" value="'+data.AddressId+'" id="default" />&nbsp;&nbsp;收件人：'+username+'&nbsp;&nbsp;&nbsp;联系电话：'+phone+'&nbsp;'+'&nbsp;&nbsp;&nbsp;收货地址：'+pca+address+'</p>';
			$("#senderdetail").append(str);
			$("#formsender").toggle(); //隐藏添加地址div
		}else{
			var str='<p><input type="radio" name="sender" value="'+data.AddressId+'" id="default" />&nbsp;&nbsp;收件人：'+username+'&nbsp;&nbsp;&nbsp;联系电话：'+phone+'&nbsp;'+'&nbsp;&nbsp;&nbsp;收货地址：'+pca+address+'</p>';
			$("#senderdetail").append(str);
			$("#formsender").toggle(); //隐藏添加地址div
		}
		
	}
},
error:function (event, XMLHttpRequest, ajaxOptions, thrownError) {
alert("表单错误，"+XMLHttpRequest+thrownError); }
		})
	 }
function makeorder() {
	  //给隐藏属性赋值  提交表单
  	  document.getElementById("senderid").value=getvv();
	  document.myform.submit();     
		 }
function getvv(){
	//获取单选的值
	var group = document.getElementsByName('sender');

	for(var i = 0; i< group.length; i++){
	if(group[i].checked){
	return group[i].value;
	}}
}
</script> 
     <script type="text/javascript">  
function checkcode()  //检查优惠券是否可以
{
	var str=$("input#code").val();
if(str!==""){
    $.ajax({
type:'post',
url:'/index.php?s=/Home/Fcoupon/check.html', //发送数据的地址//
data:{couponid:str},
 dataType: "json",
success:function(data)
{if(data.msg=="yes"){
	$("span.tips").html(data.info);
}
else{$("span.tips").empty();
	document.getElementById("code").value="";
	$("span.tips").html(data.info);



}
} 
});//ajax结束

}//if结束
	
	}


</script> 
     <div class="orderplace"> 
      <link type="text/css" href="css/selectpick.css" rel="stylesheet" /> 
      <div class="o_title"> 
       <h2>收货人信息 <span><a href="javascript:;" id="show">新增</a></span></h2> 
      </div> 
      <div id="formwarp" class="place"> 
       <div id="senderdetail"> 
       <c:forEach items="${addressList }" var="ord">
       	<p>
       	<c:if test="${ord.msg==1 }">
       		<input type="radio" checked="checked" id="default" name="sender" value="${ord.id }" />&nbsp;收件人：${ord.username }&nbsp;&nbsp;联系电话:${ord.phone }&nbsp;&nbsp;收货地址：${ord.province }${ord.city }${ord.area }${ord.address } 
       	</c:if>
       	<c:if test="${ord.msg!=1 }">
       		<input type="radio" id="default" name="sender" value="${ord.id }" />&nbsp;收件人：${ord.username }&nbsp;&nbsp;联系电话:${ord.phone }&nbsp;&nbsp;收货地址：${ord.province }${ord.city }${ord.area }${ord.address } 
       	</c:if>
       	</p> 
       </c:forEach>
        
       </div> 
       <div id="formsender" style="display:none"> 
        <form id="formincart" name="form"> 
         <dl> 
          <div class="infolist"> 
           <lable>
            所在地区：
           </lable> 
           <div class="liststyle"> 
            <span id="Province"> <i>请选择省份</i> 
             <ul style=""> 
              <li><a href="javascript:void(0)" alt="请选择省份">请选择省份</a></li> 
              <li><a href="javascript:void(0)" alt="北京市">北京市</a></li>
              <li><a href="javascript:void(0)" alt="天津市">天津市</a></li>
              <li><a href="javascript:void(0)" alt="上海市">上海市</a></li>
              <li><a href="javascript:void(0)" alt="重庆市">重庆市</a></li>
              <li><a href="javascript:void(0)" alt="河北省">河北省</a></li>
              <li><a href="javascript:void(0)" alt="山西省">山西省</a></li>
              <li><a href="javascript:void(0)" alt="内蒙古">内蒙古</a></li>
              <li><a href="javascript:void(0)" alt="辽宁省">辽宁省</a></li>
              <li><a href="javascript:void(0)" alt="吉林省">吉林省</a></li>
              <li><a href="javascript:void(0)" alt="黑龙江省">黑龙江省</a></li>
              <li><a href="javascript:void(0)" alt="江苏省">江苏省</a></li>
              <li><a href="javascript:void(0)" alt="浙江省">浙江省</a></li>
              <li><a href="javascript:void(0)" alt="安徽省">安徽省</a></li>
              <li><a href="javascript:void(0)" alt="福建省">福建省</a></li>
              <li><a href="javascript:void(0)" alt="江西省">江西省</a></li>
              <li><a href="javascript:void(0)" alt="山东省">山东省</a></li>
              <li><a href="javascript:void(0)" alt="河南省">河南省</a></li>
              <li><a href="javascript:void(0)" alt="湖北省">湖北省</a></li>
              <li><a href="javascript:void(0)" alt="湖南省">湖南省</a></li>
              <li><a href="javascript:void(0)" alt="广东省">广东省</a></li>
              <li><a href="javascript:void(0)" alt="广西">广西</a></li>
              <li><a href="javascript:void(0)" alt="海南省">海南省</a></li>
              <li><a href="javascript:void(0)" alt="四川省">四川省</a></li>
              <li><a href="javascript:void(0)" alt="贵州省">贵州省</a></li>
              <li><a href="javascript:void(0)" alt="云南省">云南省</a></li>
              <li><a href="javascript:void(0)" alt="西藏">西藏</a></li>
              <li><a href="javascript:void(0)" alt="陕西省">陕西省</a></li>
              <li><a href="javascript:void(0)" alt="甘肃省">甘肃省</a></li>
              <li><a href="javascript:void(0)" alt="青海省">青海省</a></li>
              <li><a href="javascript:void(0)" alt="宁夏">宁夏</a></li>
              <li><a href="javascript:void(0)" alt="新疆">新疆</a></li>
              <li><a href="javascript:void(0)" alt="香港">香港</a></li>
              <li><a href="javascript:void(0)" alt="澳门">澳门</a></li>
              <li><a href="javascript:void(0)" alt="台湾省">台湾省</a></li>
             </ul> <input id="idprovince" type="hidden" name="cho_Province" value="请选择省份" /> </span> 
            <span id="City"> <i>请选择城市</i> 
             <ul style=""> 
              <li><a href="javascript:void(0)" alt="请选择城市">请选择城市</a></li> 
             </ul> <input type="hidden" id="idcity" name="cho_City" value="请选择城市" /> </span> 
            <span id="Area"> <i>请选择地区</i> 
             <ul style=""> 
              <li><a href="javascript:void(0)" alt="请选择地区">请选择地区</a></li> 
             </ul> <input type="hidden" id="idarea" name="cho_Area" value="请选择地区" /> </span> 
           </div> 
          </div> 
         </dl> 
         <dl> 
          <dt>
           详细地址：
          </dt> 
          <dd>
           <input type="text" class="cart_long" id="address" maxlength="40" data-input="text" value="" name="area" />
           <font class="ml10 cleb6100" style="display: none;">详细地址不能为空</font>
          </dd> 
         </dl> 
         <dl> 
          <dt>
           <span>*</span>收 货 人：
          </dt> 
          <dd>
           <input type="text" class="cart_long" id="realname" maxlength="20" data-input="text" value="" />
           <font class="ml10 cleb6100" style="display: none;">收货人不能为空</font>
          </dd> 
         </dl> 
         <dl> 
          <dt>
           <span></span>手&nbsp;&nbsp;&nbsp;&nbsp;机：
          </dt> 
          <dd>
           <input type="text" class="cart_long" id="phone" maxlength="11" data-msg="收货手机号码格式不正确" data-input="text" data-type="cellphone" value="" />&nbsp;用于接收发货通知及送货前确认
          </dd> 
         </dl> 
         <dl> 
          <dd>
           <input id="isdefault" checked="checked" name="default" type="checkbox" class="cart_n_box" />设为默认地址
          </dd> 
         </dl> 
         <dl> 
          <dd>
           <a href="javascript:void(0)" class="ncart_btn_on" onclick="savemsg();">保存</a>
          </dd> 
         </dl> 
        </form>
       </div>
       <script type="text/javascript" src="js/city.js"></script> 
       <script type="text/javascript">
	

	</script> 
      </div> 
     </div> 
     <!--收货信息 结束--> 
     <!--订单支付 开始--> 
     <form action="tjdd.order" method="post" name="myform" id="myform"> 
      <div class="orderplace"> 
       <div class="o_title"> 
        <h2>支付</h2> 
       </div> 
       <div id="formwarp"> 
        <dl> 
         <dt>
          支付方式：
         </dt> 
         <dd> 
          <input type="hidden" name="tag" id="orderid" value="${addressId }" /> 
          <input type="hidden" name="sender" id="senderid" /> 
          <input type="radio" name="PayType" id="huo" checked="checked" value="1" />货到付款 
          <input type="radio" name="PayType" id="pay" value="2" />在线支付 
         </dd> 
        </dl> 
       </div> 
      </div> 
      <!--订单支付 结束--> 
      <!--商品信息 开始--> 
      <div class="orderplace"> 
       <div class="o_title"> 
        <h2>商品信息</h2>
       </div> 
       <table border="0" cellspacing="0" cellpadding="0" class="gridtable cart-2" width="100%"> 
        <tbody>
         <tr class="com_list_tit"> 
          <th>商品名称</th> 
          <th>单价(元)</th> 
          <th>数量</th> 
          <th>合计</th> 
         </tr> 
         <c:forEach items="${gwcGoodsList }" var="g">
         	<tr> 
	          <td><a href="goodsPageServlet?id=${g.id }" target="_blank"> ${g.name }</a></td> 
	          <td align="center">${g.price }</td> 
	          <td align="center">${g.num }</td> 
	          <td align="center">${g.total }</td> 
	        </tr>
         </c:forEach>
		 <!-- 订单商品循环go -->
          
		 <!-- 订单商品循环end -->
        </tbody>
       </table> 
      </div> 
      <!--商品信息 结束--> 
      <!--发票信息 开始--> 
      <div class="orderplace fapiao"> 
       <h2> 留言信息</h2> 
       <textarea name="liuyan" rows="4" cols="60"></textarea>
      </div> 
      <!--发票信息 结束--> 
      <!--提交信息 开始--> 
      <div class="orderplace trans"> 
       <p><b><span  class="red">${gwcGoodsList.size() }</span></b>件商品</p> 
       <p class="jiesuan">应付总额 <span id="TotalNeedPay" class="red">￥${ze }</span>元 <a class="btn_submit_pay" onclick="makeorder();return false">提交订单</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p> 
       <!--提交信息 结束--> 
      </div> 
     </form> 
    </div> 
    <!-- /主体 --> 
   </div>
  </div> 
  <%@ include file="jsps/footer.jsp" %> 
  <!-- /底部 --> 
  </body>
</html>
