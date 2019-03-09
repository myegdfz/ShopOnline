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
	<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
	<!-- <script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script> -->
	<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript">
	$(function(){
		$('#bName').combobox({
			onSelect: function(record){
				$('#sName').combobox("reload",'../idSelList.small?bigTypeId='+record.id);
				$("#sName").combobox("setValue","");
			}
		});
	});


	var url;

	function searchProduct(){
		$('#dg').datagrid('load',{
			"name":$("#goodsName").val()
		});
	}
	
	function openProductAddDialog(){
		$('#sName').combobox("reload",'../idSelList.small?bigTypeId='); //设置小类
		$("#dlg").dialog("open").dialog("setTitle","添加商品信息");
		KindEditor.remove('#contents');  //移除之前的
		editor = KindEditor.create('#contents', {
			themeType : 'simple',
			cssPath : '../kindeditor/plugins/code/prettify.css',
			uploadJson : '../kindeditor/jsp/upload_json.jsp',
			fileManagerJson : '../kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true});  //重新添加
		prettyPrint();
		url="../add.goods";
	}
	
	function saveProduct(){
		$("#contents").val(editor.html());
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				if($('#bName').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择商品大类");
					return false;
				}
				if($('#sName').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择商品小类");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				var result = eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#name").val("");
		$("#price").val("");
		$("#stock").val("");
		$("#proPic").val("");
		$("#brand").val("");
		$("#sales").val("0");
		$("#views").val("0");
		$("#state").combobox("setValue","");
		$("#sName").combobox("setValue","");
		$("#bName").combobox("setValue","");	
	}
	
	function closeProductDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openProductModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		
		var row=selectedRows[0];
		$('#sName').combobox("reload",'../idSelList.small?bigTypeId='+row.bigTypeId); //设置小类
		
		$("#dlg").dialog("open").dialog("setTitle","编辑商品信息");
		$("#name").val(row.name);
		$("#price").val(row.price);
		$("#stock").val(row.stock);
		//$("#proPic").val(row.proPic);
		$("#brand").val(row.brand);
		$("#sales").val(row.sales);
		$("#views").val(row.views);
		$("#state").combobox("setValue",row.state);
		
		
		
		$("#sName").combobox("setValue",row.smallTypeId);
		$("#bName").combobox("setValue",row.bigTypeId);
		
		KindEditor.remove('#contents');  //移除之前的
		editor = KindEditor.create('#contents', {
			themeType : 'simple',
			cssPath : '../kindeditor/plugins/code/prettify.css',
			uploadJson : '../kindeditor/jsp/upload_json.jsp',
			fileManagerJson : '../kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true});  //重新添加
		prettyPrint();
		editor.html(row.contents);
		url="../update.goods?id="+row.id;
	}
	
	function deleteProduct(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("../del.goods",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已成功删除！");							
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function formatBigTypeName(val,row){
		return row.bigTypeName;
	}
	
	function formatBigTypeId(val,row){
		return row.bigTypeId;
	}
	
	function formatSmallTypeName(val,row){
		return row.smallTypeName;
	}
	
	function formatSmallTypeId(val,row){
		return row.smallTypeId;
	}
	
	function formatHot(val,row){
		if(val==1){
			return "是";
		}else{
			return "否";
		}
	}
	
	function formatSpecialPrice(val,row){
		if(val==1){
			return "是";
		}else{
			return "否";
		}
	}
	
	function formatProPic(val,row){
		return "<img width=100 height=100 src='${pageContext.request.contextPath}/"+val+"'/>";
	}
	
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="商品管理" class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="../sel.goods" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="50" align="center">编号</th>
    		<th field="proPic" width="150" align="center" formatter="formatProPic" >商品图片</th>
    		<th field="name" width="150" align="center" >商品名称</th>
    		<th field="price" width="50" align="center" >价格</th>
    		<th field="brand" width="50" align="center" >品牌</th>
    		<th field="sales" width="50" align="center" >销量</th>
    		<th field="views" width="50" align="center" >浏览量</th>
    		<th field="stock" width="50" align="center" >库存</th>
    		<th field="state" width="50" align="center" >状态</th>
    		<th field="smallTypeId" width="100" align="center" formatter="formatSmallTypeId" hidden="true">所属商品小类ID</th>
    		<th field="smallTypeName" width="100" align="center" formatter="formatSmallTypeName">所属商品小类</th>
    		<th field="bigTypeId" width="100" align="center" formatter="formatBigTypeId" hidden="true">所属商品大类ID</th>
    		<th field="bigTypeName" width="100" align="center" formatter="formatBigTypeName">所属商品大类</th>
    		<th field="contents" width="200" align="center" hidden=true>描述</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openProductAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openProductModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteProduct()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;商品名称：&nbsp;<input type="text" name="s_product.name"  id="goodsName"  size="20" onkeydown="if(event.keyCode==13) searchProduct()"/>
		<a href="javascript:searchProduct()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 730px;height: 500px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post"  enctype="multipart/form-data">
  	<table cellspacing="8px;">
  		<tr>
  			<td>商品名称：</td>
  			<td colspan="4"><input class="easyui-validatebox" id="name" name="name"  required="true" style="width: 300px;"/></td>
  		</tr>
  		<tr>
  			<td>商品价格：</td>
  			<td colspan="2"><input class="easyui-validatebox" id="price" name="price"  required="true"/></td>
  			<td>商品库存：</td>
  			<td colspan="2"><input class="easyui-validatebox" id="stock" name="stock"  required="true"/></td>
  		</tr>
  		<tr>
  			<td>商品品牌：</td>
  			<td colspan="2"><input class="easyui-validatebox" id="brand" name="brand"  required="true"/></td>
  			<td>商品状态：</td>
  			<td colspan="2">
  				<select class="easyui-combobox"  id="state"  name="state" style="width:154px;" editable="false" panelHeight="auto">
  				    <option value="">选择状态</option>
					<option value="正常">正常</option>
					<option value="下架">下架</option>
				</select>
  			</td>
  		</tr>
  		<tr>
  			<td>浏览量：</td>
  			<td colspan="2"><input class="easyui-validatebox" id="views" name="views"  required="true" value="0"/></td>
  			<td>商品销量：</td>
  			<td colspan="2"><input class="easyui-validatebox" id="sales" name="sales"  required="true" value="0"/></td>
  		</tr>
  	    <tr>
  			<td>所属大类：</td>
  			<td colspan="2"><input class="easyui-combobox" id="bName" name="bigTypeId"  data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'name',url:'../selList.big'"/></td>
  			<td>所属小类：</td>
  			<td colspan="2"><input class="easyui-combobox"  id="sName" name="smallTypeId" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'name',url:'../selList.small'" /></td>
  		</tr>
  		<tr>
  			<td>商品图片：</td>
  			<td colspan="4"><input id="proPic" name="proPic"  type="file"/></td>
  		</tr>
  		
  		<tr>
  			<td colspan="5">
  				<textarea rows="10" cols="50" name="contents" id="contents"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveProduct()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeProductDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>>