<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
	<title>商品设置</title>
	<meta name="decorator" content="default" />
	<%@include file="/WEB-INF/views/include/taglib.jsp"%>
	<%@include file="/WEB-INF/views/include/dialog.jsp"%>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(function(){
			var banpic = $("#reserve2").val();
			if(banpic != null && banpic != ''){
				var pics = banpic.split(";");
				var htm = '';
				$(pics).each(function(i){
					htm += '<div><a class="btn btn-medium" href="javascript:void(0);" onclick="delBan(this)"><i class="icon-minus share"></i></a>';
					htm += '<img name="banDetailPic" with="100px" height="auto" src="' + pics[i] + '"/></div>';
				})
				$("#detailDiv").append(htm);
			}
			$("#inputForm").validate();
			$.validator.addMethod("isZhengshu", function(value, element) {   
				var reg = /^[0-9]*[1-9][0-9]*$/;
				var temp = true;
			    if (!reg.test(value)) {
			    	temp = false;
			    }
			    return temp;
			}, "'值' 请输入正整数");
			$("#btnSave").click(function(){
				if($("#inputForm").valid()){
					var bans = [];
					$("img[name='banDetailPic']").each(function(){
						bans.push($(this).attr("src"));
					})
					$("#reserve2").val(bans.join(";"));
					$("#inputForm").submit();
				}else{
					return;
				}
			});
			
			//主图片
			$("#btnPic").click(function(){
				$("#picdialog_upload").contents().find("#restType").val("0");
				$("#picdialog_upload").contents().find("#publicattachmentadminform").append('<input type="file" name="fileName" id="fileName"  onchange="changeFile(this)" >');
				$("#picdialog_upload").contents().find("#fileName").click();
			})
			
			//详情图片
			$("#btnDetailPic").click(function(){
				$("#picdialog_upload").contents().find("#restType").val("1");
				$("#picdialog_upload").contents().find("#publicattachmentadminform").append('<input type="file" name="fileName" id="fileName"  onchange="changeFile(this)" >');
				$("#picdialog_upload").contents().find("#fileName").click();
			})
		})
		
		function setBannerPicUrl(url,imgtype){
			top.$.jBox.tip('上传成功', 'success');
			if(imgtype == '0'){
				$("#banUrl").val(url);
				$("#img_banPic").attr('src',url);	
			}else{
				var htm = '<div><a class="btn btn-medium" href="javascript:void(0);" onclick="delBan(this)"><i class="icon-minus share"></i></a>';
				htm += '<img name="banDetailPic" with="100px" height="auto" src="' + url + '"/></div>';
				$("#detailDiv").append(htm);
			}
		}
		function delBan(ths){
			$(ths).parent().remove();
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
					<c:if test="${not empty banner.banId}">
						<li class="active"><a href="${ctx}/points/banner/form?banId=${banner.banId}">修改推广</a></li>
					</c:if>
					<c:if test="${empty banner.banId}">
						<li class="active"><a href="${ctx}/points/banner/form">新建推广</a></li>
					</c:if>
				</ul>
				<form:form id="inputForm" modelAttribute="banner" action="${ctx}/points/banner/save" method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="banId"/>
					<form:hidden path="reserve2"/>
					<input type="hidden" name="banType" value="1"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">推广名称：</label>
							<div class="controls">
							  <form:input path="banName"  htmlEscape="false" maxlength="50" class="input-large required"/>
							</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品金额：</label>
								<div class="controls">
									<input type="text" id="reserve1" name="reserve1" value="${banner.reserve1}" maxlength="10" class="input-large required isZhengshu"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">优先级：</label>
								<div class="controls">
									<input type="text" id="orderSec" name="orderSec" value="${banner.orderSec}" maxlength="20" class="input-large required isZhengshu"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">推广详情：</label>
								<div class="controls">
									<textarea id="jumpUrl" name="jumpUrl" class="input-xxlarge required" style="height:150px;">${banner.jumpUrl}</textarea>
									<%-- <input type="text" id="jumpUrl" name="jumpUrl" value="${banner.jumpUrl}" maxlength="120" class="input-large required"/> --%>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">推广主图片：</label>
								<div class="controls">
									<div><img with="100px" height="auto" id="img_banPic" alt="推广图片" src="${banner.banUrl}"/> </div>
									<input type="hidden" name="banUrl" id="banUrl" value="${banner.banUrl}"/>
									<input id="btnPic"  class="btn btn-primary" type="button" value="上传图片"/>&nbsp;&nbsp;
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">推广详情图片：</label>
								<div class="controls" id="detailDiv">
									<input id="btnDetailPic"  class="btn btn-primary" type="button" value="添加图片"/>&nbsp;&nbsp;
								</div>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<input id="btnSave"  class="btn btn-primary" type="button" value="保 存"/>&nbsp;&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			 </form:form>
			 <div id="publicattachmentdialog_upload" icon="icon-save" style="padding:10px;display:none;width: auto;z-index: 9999;margin-top: 10">
				 <iframe name="picdialog_upload" id="picdialog_upload"  style="border:0px;" height="100px" marginwidth="0" marginheight="0" frameborder="0" width="100%;" 
						src="${ctx}/points/banner/uploadFile">
				</iframe>
			</div>
		</div>
	</div>
</body>
</html>