<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="footer"> 
   <div class="footer_pic_new"> 
    <div class="footer_pic_new_inner"> 
     <a name="foot_1" href="#" target="_blank" class="footer_pic01"><span>正规渠道正品保障</span></a> 
     <a name="foot_2" class="footer_pic02" href="#" target="_blank"><span>放心购物货到付款</span></a> 
     <a name="foot_3" class="footer_pic03" href="#" target="_blank"><span>150城市次日送达</span></a> 
     <a name="foot_4" class="footer_pic04" href="#" target="_blank"><span>上门退货当场退款</span></a> 
    </div> 
   </div> 
   <div class="foot_center"> 
    <dl> 
     <dt>
       购买流程 
     </dt> 
     <dd> 
      <a rel="nofollow" href="#">发票说明</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">优惠券说明</a> 
     </dd> 
    </dl> 
    <dl> 
     <dt>
       配送方式 
     </dt> 
     <dd> 
      <a rel="nofollow" href="#">配送方式</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">配送费用</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">签收须知</a> 
     </dd> 
    </dl> 
    <dl> 
     <dt>
       服务支持 
     </dt> 
     <dd> 
      <a rel="nofollow" href="#">服务保证</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">售后服务</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">售后网点</a> 
     </dd> 
    </dl> 
    <dl> 
     <dt>
       品牌服务 
     </dt> 
     <dd> 
      <a rel="nofollow" href="#">常见问题</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">相关下载</a> 
     </dd> 
     <dd> 
      <a rel="nofollow" href="#">联系我们</a> 
     </dd> 
    </dl> 
    <dl> 
     <dt>
       在线客服 
     </dt> 
     <dd>
       周一至周五 
     </dd> 
     <dd>
       09:00-18:00 
     </dd> 
     <dd> 
      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=1135501827&amp;site=qq&amp;menu=yes"><img border="0" src="images/pa.gif" alt="点击这里给我发消息" title="点击这里给我发消息" /></a> 
     </dd> 
    </dl> 
   </div> 
   <div class="theme-footer"> 
    <p><span style=" text-align: center;"> Copyright@OnlineShop System2017-2018 </span></p> 
   </div> 
  </div> 
  <!-- 回到顶部代码 开始 -->
  <script>
$(function(){
	$(window).on('scroll',function(){
		var st = $(document).scrollTop();
		if( st>0 ){
			if( $('#main-container').length != 0  ){
				var w = $(window).width(),mw = $('#main-container').width();
				if( (w-mw)/2 > 70 )
					$('#go-top').css({'left':(w-mw)/2+mw+20});
				else{
					$('#go-top').css({'left':'auto'});
				}
			}
			$('#go-top').fadeIn(function(){
				$(this).removeClass('dn');
			});
		}else{
			$('#go-top').fadeOut(function(){
				$(this).addClass('dn');
			});
		}	
	});
	$('#go-top .go').on('click',function(){
		$('html,body').animate({'scrollTop':0},500);
	});

	$('#go-top .uc-2vm').hover(function(){
		$('#go-top .uc-2vm-pop').removeClass('dn');
	},function(){
		$('#go-top .uc-2vm-pop').addClass('dn');
	});
});
</script>