<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="center_right" id="MemberCenterCtrl" style="display: block;">
<div class="order_namelist">
<span <c:if test="${type=='1'}">class="active"</c:if>><a href="userMain.page?type=1" class="red">所有订单</a></span>
<span <c:if test="${type=='2'}">class="active"</c:if>><a href="userMain.page?type=2" class="red">待支付订单</a></span>
<span <c:if test="${type=='3'}">class="active"</c:if>><a href="userMain.page?type=3" class="red">待发货订单</a></span>
<span <c:if test="${type=='4'}">class="active"</c:if>><a href="userMain.page?type=4" class="red">待确认订单</a></span>
</div>
	
<form action="orderServlet.java" method="post" name="delform">
        
	  <div class="good-canshu"><span class="good-name">商品名称</span><span class="good-price">售价</span><span class="good-num">数量</span> <span class="good-action">商品操作</span><span class="good-total">总金额(元)</span>
	  <span class="good-status">交易状态</span> <span class="deal-action">交易操作</span></div>

		<!-- 订单循环开始 -->
		<c:forEach items="${PageDate.data }" var="ordrList">
          <div class="single" onmouseover="this.className='singlehover'" onmouseout="this.className='single'">
		 <div class="order-detail">订单号：<span>${ordrList.id }</span>&nbsp;&nbsp;&nbsp;下单时间：<span>${ordrList.time }&nbsp;
					   </span></div>
         <!-- 列表详情区域开始 -->  
		  <div class="goodlist-wrap">   
		<!-- 左边商品区域开始 -->  
		  <div class="three-area">
		  <!-- 订单商品循环go -->
		  <c:forEach items="${ordrList.itemList }" var="goods">
			  <div class="cos">
			  <span class="c1"><a href="goodsPageServlet?id=${goods.goodsId }" class="dl"> <img src="${goods.proPic }" width="70" height="70"></a>
			  <span class="dd"><a href="goodsPageServlet?id=${goods.goodsId }" class="dd"> ${goods.goodsName }</a></span>
			 <span class="dd"></span>
			  </span>
			  <span class="c2">￥${goods.goodsPrice }</span>
			  <span class="c2">${goods.sum }</span> 
			    <span class="c2">  无 </span>
			  </div>
		  </c:forEach>
		  <!-- 订单商品循环end --> 
		  </div>
		  <!-- 左边商品结束 -->  
		  
		  
 <div class="total-area"><p>￥${ordrList.total }</p><p>(货到付邮)</p> 
		    </div>
		     <div class="deal-area">
			<p class="red"> 
			 <c:choose>
			 	<c:when test="${ordrList.state==6 }">货到付款</c:when>
			 	<c:when test="${ordrList.state==1 }">等待付款</c:when>
			 	<c:when test="${ordrList.state==2 }">等待发货</c:when>
			 	<c:when test="${ordrList.state==3 }">已发货</c:when>
			 	<c:when test="${ordrList.state==4 }">交易关闭</c:when>
			 	<c:when test="${ordrList.state==5 }">交易成功</c:when>
			 </c:choose>
			 </p>
			<p><a href="orderDetail.page?id=${ordrList.id }">订单详情</a></p>
			  </div>
			   <div class="act-area"> 
			   <c:if test="${ordrList.state==1 }">
				  <p><a href="javascript:cancelOrder(${ordrList.id })" >取消订单</a> </p>		
				  <p><a class="confirm" href="zfdd.order?orderId=${ordrList.id }">前去支付</a> </p>
			  </c:if>
			  <c:if test="${ordrList.state==3 }">
			  	<p><a class="confirm" href="qdsh.order?orderId=${ordrList.id }">确定收货</a> </p>
			  </c:if>
		    </div> 
		  </div>
       <!-- 列表详情区域结束 -->
	   </div>
	   </c:forEach>
	   <!-- 订单循环结束 -->
	   	</form>
	   	<script type="text/javascript">
	   	function cancelOrder(id){
			//layer.alert(id,{icon:2});
			layer.confirm('确定要取消此条订单吗？', {title:'取消订单',icon: 3,shift:4,
			    btn: ['确定','取消'] //按钮
			}, function(){
				location.href="qxdd.order?orderId="+id;
			});
		}
	   	</script>
	   	<style>
			.layui-layer-btn .layui-layer-btn0 {
			    border-color: #f60;
			    background-color: #f40;
			    color: #fff;
			}
		</style>
   <!-- 分页 --> 
       <c:if test="${PageDate.count!=0 }">
       <div class="page"> 
        <div> 
        	<c:if test="${p!=1  }">
        		<a class="prev" href="userMain.page?type=${type }&p=${p-1 }">上一页</a>
        	</c:if>
        	<c:forEach begin="1" end="${PageDate.pagetotal }" varStatus="sta">
        		<c:if test="${p==sta.count }">
        			<span class="current">${p }</span>
        		</c:if>
        		<c:if test="${p!=sta.count }">
        			<a class="num" href="userMain.page?type=${type }&p=${sta.count }">${sta.count }</a>
        		</c:if>
        	</c:forEach>
        	<c:if test="${p!=PageDate.pagetotal}">
        		<a class="next" href="userMain.page?type=${type }&p=${p+1 }">下一页</a>
        	</c:if>
         	<span class="rows">共${PageDate.count } 条记录</span>
        </div>
       </div>
       </c:if>
</div>