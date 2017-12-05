<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 7px 0 10px;}
		#header {margin:0 0 10px;position:static;} #header li {font-size:12px;_font-size:12px;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} 
	</style>
	<script type="text/javascript"> 
		
		$(document).ready(function() {
			/* console.log($.getUrlParam("app")); */
 			$("#menu a.menu").click(function(){
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
			});
 			
 			$(".accordion-heading a").click(function(){
				$('.accordion-toggle i').removeClass('icon-chevron-down');
				$('.accordion-toggle i').addClass('icon-chevron-right');
				if(!$($(this).attr('href')).hasClass('in')){
					$(this).children('i').removeClass('icon-chevron-right');
					$(this).children('i').addClass('icon-chevron-down');
				}
			});
			$(".accordion-body a").click(function(){
				$("#menu li").removeClass("active");
				$("#menu li i").removeClass("icon-white");
				$(this).parent().addClass("active");
				$(this).children("i").addClass("icon-white");
			});
			$(".accordion-body a:first i").click();
		});
		
		
		function IsNullOrEmpty(str){   
			
            if(typeof(str)=="undefined") return true;   
            if(str==null) return true;  
            if(str=="") return true;  
            if(str.replace(/(^s*)|(s*$)/g, "").length==0) return true;
            return false;  
        }

	</script>
</head>
<body>
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
	      <div class="navbar-inner">
	      	 <div class="brand">${fns:getConfig('productName')}</div>
	         <div class="nav-collapse">
	           <ul id="menu" class="nav">
					<li class="menu active"><a class="menu" href="#" >积分管理</a></li>
	           </ul>
	           <ul class="nav pull-right">
				 <li><a href="#"  title="访问网站主页"><i class="icon-home"></i></a></li>
			  	 <li id="themeSwitch" class="dropdown">
			       	<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
				    <ul class="dropdown-menu">
				     	<li><a href="#" onclick="location='${ctx}/theme/readable?url='+location.href">默认</a></li>
				        <li><a href="#" onclick="location='${ctx}/theme/default?url='+location.href">淡蓝</a></li>
				        <li><a href="#" onclick="location='${ctx}/theme/cerulean?url='+location.href">浅色</a></li>
				    </ul>
				    <!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
			     </li>
			  	 <li class="dropdown">
				    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${user.userName}</a>
				    <ul class="dropdown-menu">
				      <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
				      <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
				    </ul>
			  	 </li>
			  	 <li>&nbsp;</li>
	           </ul>
	         </div><!--/.nav-collapse -->
	      </div>
	    </div>
	    <div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left">
					<div class="accordion" id="menu">
						<div class="accordion-group">
						    <div class="accordion-heading">
						    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse1" title="会员菜单">
						    	<i class="icon-chevron-down">
						    	</i>&nbsp;会员菜单</a>
						    </div>
						    <div id="collapse1" class="accordion-body collapse in" style="height: auto;">
								<div class="accordion-inner">
									<ul class="nav nav-list">
										<li>
											<a href="${ctx}/points/member" target="mainFrame" >
											<i class="icon-circle-arrow-right"></i>&nbsp;会员管理</a>
										</li>
										<li>
											<a href="${ctx}/points/goods" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;商品管理</a>
										</li>
										<li>
											<a href="${ctx}/points/order" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;订单管理</a>
										</li>
										<li>
											<a href="${ctx}/points/address" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;地址管理</a>
										</li>
									</ul>
								</div>
						    </div>
						    
						    <div class="accordion-heading">
						    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse2" title="活动管理">
						    	<i class="icon-chevron-right">
						    	</i>&nbsp;活动管理</a>
						    </div>
						    <div id="collapse2" class="accordion-body collapse" >
								<div class="accordion-inner">
									<ul class="nav nav-list">
										<li>
											<a href="${ctx}/points/activity" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;转盘抽奖</a>
										</li>
										<li>
											<a href="${ctx}/points/awards" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;中奖记录</a>
										</li>
										<li>
											<a href="${ctx}/points/exchange" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;积分兑换</a>
										</li>
										<li>
											<a href="${ctx}/points/exchangeList" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;兑换记录</a>
										</li>
									</ul>
								</div>
						    </div>
						    
						    <div class="accordion-heading">
						    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse3" title="规则管理">
						    	<i class="icon-chevron-right">
						    	</i>&nbsp;规则管理</a>
						    </div>
						    <div id="collapse3" class="accordion-body collapse" >
								<div class="accordion-inner">
									<ul class="nav nav-list">
										<li>
											<a href="${ctx}/points/rule" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;规则管理</a>
										</li>
										<li>
											<a href="${ctx}/points/record" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;转让记录</a>
										</li>
									</ul>
								</div>
						    </div>
						    
						    <div class="accordion-heading">
						    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse4" title="系统管理">
						    	<i class="icon-chevron-right">
						    	</i>&nbsp;系统管理</a>
						    </div>
						    <div id="collapse4" class="accordion-body collapse" >
								<div class="accordion-inner">
									<ul class="nav nav-list">
										<li>
											<a href="${ctx}/points/selected" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;精选配置</a>
										</li>
										<li>
											<a href="${ctx}/points/banner" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;推广配置</a>
										</li>
										<li>
											<a href="${ctx}/points/feedBack" target="mainFrame"  >
											<i class="icon-circle-arrow-right"></i>&nbsp;系统反馈</a>
										</li>
									</ul>
								</div>
						    </div>
						</div>
					</div>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
			</div>
		    <div id="footer" class="row-fluid">
	            Copyright &copy; 2012-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备17075430号</a> ${fns:getConfig('version')}
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = "240"; // 左侧窗口大小
		function wSize(){
			var minHeight = 500, minWidth = 980;
			var strs=getWindowSize().toString().split(",");
			$("#menuFrame, #mainFrame, #openClose").height((strs[0]<minHeight?minHeight:strs[0])-$("#header").height()-$("#footer").height()-32);
			$("#openClose").height($("#openClose").height()-5);
			if(strs[1]<minWidth){
				$("#main").css("width",minWidth-10);
				$("html,body").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
			}else{
				$("#main").css("width","auto");
				$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
			}
			$("#right").width($("#content").width()-$("#left").width()-$("#openClose").width()-5);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>