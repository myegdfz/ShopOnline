<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;

	function search(){
		$('#dg').datagrid('load',{
			"s_order.orderNo":$("#s_orderNo").val(),
			"oname":$("#s_userName").val()
		});
	}
	
	function openOrderDetailDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要查看的数据！");
			return;
		}
		var row=selectedRows[0];
		$('#dg2').datagrid('load', {
		    orderNo: row.id
		});
		 $("#orderNo").val(row.id);
		 $("#user").val(row.trueName);
		 $("#cost").val(row.total);
		 var v = row.state;
		 if(v==1){
			 $("#status").val("待付款");
		 }else if(v==2){
			 $("#status").val("已付款");
		 }else if(v==3){
			 $("#status").val("交易成功");
		 }else if(v==4){
			 $("#status").val("交易取消");
		 }else if(v==6){
			 $("#status").val("货到付款");
		 }
		 if(row.province=="未填写"){
			 $("#Xaddress").val(row.province);
		 }else{
			 $("#Xaddress").val(row.province+row.city+row.area+row.address+"  手机号："+row.phone+"  收货人姓名："+row.username);
		 }
		 if(row.remarks==""){
			 $("#liuyan").val("未填写");
		 }else{
			 $("#liuyan").val(row.remarks);
		 }
		$("#dlg").dialog("open").dialog("setTitle","订单详情");
	}
	
	function closeOrderDetailDialog(){
		$("#dlg").dialog("close");
	}
	
	
	function formatUserId(val,row){
		return row.user.id;
	}
	
	function formatUserName(val,row){
		return row.user.userName;
	}
	
	function formatStatus(val,row){
		if(val==1){
			return "未付款";
		}else if(val==2){
			return "已付款";
		}else if(val==3){
			return "已发货";
		}else if(val==4){
			return "订单取消";
		}else if(val==5){
			return "交易完成";
		 }else if(val==6){
			return "货到付款";
		}
		return "";
	}
	
	function formatProPic(val,row){
		return "<img width=100 height=100 src='${pageContext.request.contextPath}/"+val+"'/>";
	}
	
	// 修改订单状态
	function modifyOrderStatus(status){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要处理的数据！");
			return;
		}
		var orderNosStr=[];
		for(var i=0;i<selectedRows.length;i++){
			orderNosStr.push(selectedRows[i].id);
		}
		var orderNos=orderNosStr.join(",");
		$.messager.confirm("系统提示","您确认要处理这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("../ddfh.order",{orderNos:orderNos,status:status},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已成功处理！");							
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
	
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="订单管理" class="easyui-datagrid" fitColumns="true"  pagination="true" rownumbers="true" url="../sel.order" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="50" align="center">订单编号</th>
    		<th field="userId" width="50" align="center" >订单人ID</th>
    		<th field="userName" width="100" align="center" >订单人用户名</th>
    		<th field="time" width="100" align="center"  >创建时间</th>
    		<th field="total" width="50" align="center" >总金额</th>
    		<th field="state" width="100" align="center"  formatter="formatStatus" >订单状态</th>
    		<th field="remarks" hidden="true">留言</th>
    		<th field="trueName" hidden="true">真实姓名</th>
    		<th field="province" hidden="true">省</th>
    		<th field="city" hidden="true">市</th>
    		<th field="area" hidden="true">县</th>
    		<th field="address" hidden="true">详细地址</th>
    		<th field="phone" hidden="true">手机号</th>
    		<th field="username" hidden="true">收货人姓名</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openOrderDetailDialog()" class="easyui-linkbutton" iconCls="icon-detail" plain="true">查看订单详情</a>
		<a href="javascript:modifyOrderStatus(3)" class="easyui-linkbutton" iconCls="icon-fahuo" plain="true">已发货</a>
	</div>
	<div>
		&nbsp;订单号：&nbsp;<input type="text" name="s_order.orderNo"  id="s_orderNo"  size="20" />
		&nbsp;订单人：&nbsp;<input type="text" name="s_order.user.userName"  id="s_userName"  size="20" />
		<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 750px;height: 400px;padding: 10px 30px" closed="true" buttons="#dlg-buttons">
  	<table cellspacing="8px;">
  		<tr>
  			<td>订单号：</td>
  			<td><input type="text" id="orderNo"  readonly="readonly" /></td>
  			<td>&nbsp;</td>
  			<td>买家姓名：</td>
  			<td><input type="text" id="user"  readonly="readonly" /></td>
  		</tr>
  		<tr>
  			<td>总金额：</td>
  			<td><input type="text" id="cost"  readonly="readonly" /></td>
  			<td>&nbsp;</td>
  			<td>订单状态：</td>
  			<td><input type="text" id="status"  readonly="readonly" /></td>
  		</tr>
  		<tr>
  			<td>收货地址：</td>
  			<td colspan="4"><input type="text" id="Xaddress"  readonly="readonly" style="width: 100%;" /></td>
  		</tr>
  		<tr>
  			<td>买家留言：</td>
  			<td colspan="4"><input type="text" id="liuyan"  readonly="readonly" style="width: 100%;" /></td>
  		</tr>
  	</table>
	<br/>
	<table id="dg2" title="订单商品列表" class="easyui-datagrid" fitColumns="true" rownumbers="true" url="../oidSel.order" >
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="goodsName" width="100" align="center">商品名称</th>
    		<th field="proPic" width="100" align="center"  formatter="formatProPic" >商品图片</th>
    		<th field="goodsPrice" width="50" align="center"  >商品价格</th>
    		<th field="sum" width="50" align="center"  >商品数量</th>
    		<th field="subTotal" width="50" align="center" >小计</th>
    	</tr>
    </thead>
</table>
</div>

<div id="dlg-buttons">
	<a href="javascript:closeOrderDetailDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>