<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="center_right">
	<span class="red" style="display: block;padding:15px;font-size:17px;">订单详情查看</span>
	<table style="" id="table" class="detailtable" width="100%">
        <tbody width="990px">
		   <tr width="100%">
                <th width="50%">订单号：${orderBean.id }</th>
                <th width="50%" colspan="2">下单时间：${orderBean.time }</th> 
           </tr>
           <c:forEach items="${orderItemList }" var="item">
           <tr>
                <td>
					<span class="img_1 img1">
						<a href="goodsPageServlet?id=${item.goodsId }">
							<img src="${item.proPic }" width="70" height="70">
							<span class="img_1_h">${item.goodsName }</span>
						</a>
					</span>
				</td>
                <td align="center">单价：${item.goodsPrice }  元</td>
                <td align="center">数量： ${item.sum } 件</td> 
           </tr>
           </c:forEach>		 
		 <tr><td colspan="4" align="right">金额：<span class="red">${orderBean.total }</span>元</td></tr>	
		 <tr><td colspan="5" align="left">收货地址：<span class="red">${address }</span></td></tr>  
		 <tr><td colspan="5" align="left">留言信息：<span class="red">${orderBean.remarks }</span></td></tr>  
        </tbody>
    </table>	  
</div>