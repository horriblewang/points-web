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
			var selPics = $("#selPics").val();
			if(selPics != null && selPics != ''){
				var pics = selPics.split(";");
				var htm = '';
				$(pics).each(function(i){
					htm += '<div><a class="btn btn-medium" href="javascript:void(0);" onclick="delBan(this)"><i class="icon-minus share"></i></a>';
					htm += '<img name="banDetailPic" with="100px" height="auto" src="' + pics[i] + '"/></div>';
				})
				$("#detailDiv").append(htm);
			};
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
					$("#selPics").val(bans.join(";"));
					$("#inputForm").submit();
				}else{
					return;
				}
			});
			
			$("#btnPic").click(function(){
				$("#picdialog_upload").contents().find("#publicattachmentadminform").append('<input type="file" name="fileName" id="fileName"  onchange="changeFile(this)" >');
				$("#picdialog_upload").contents().find("#fileName").click();
			})
		})
		function setSelectedPicUrl(url){
			top.$.jBox.tip('上传成功', 'success');
			var htm = '<div><a class="btn btn-medium" href="javascript:void(0);" onclick="delBan(this)"><i class="icon-minus share"></i></a>';
			htm += '<img name="banDetailPic" with="100px" height="auto" src="' + url + '"/></div>';
			$("#detailDiv").append(htm);
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
					<c:if test="${not empty selected.selId}">
						<li class="active"><a href="${ctx}/points/selected/form?selId=${selected.selId}">修改精选</a></li>
					</c:if>
					<c:if test="${empty selected.selId}">
						<li class="active"><a href="${ctx}/points/selected/form">新建精选</a></li>
					</c:if>
				</ul>
				<form:form id="inputForm" modelAttribute="selected" action="${ctx}/points/selected/save" method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="selId"/>
					<form:hidden path="selPics"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">精选名称：</label>
							<div class="controls">
							  <form:input path="selName"  htmlEscape="false" maxlength="50" class="input-large required"/>
							</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">精选类型：</label>
								<div class="controls">
									<select id="selType" name="selType" class="input-large required">
										<option value="2" <c:if test="${selected.selType eq '2'}">selected="selected"</c:if>>女装</option>
										<option value="3" <c:if test="${selected.selType eq '3'}">selected="selected"</c:if>>男装</option>
										<option value="4" <c:if test="${selected.selType eq '4'}">selected="selected"</c:if>>家居</option>
										<option value="5" <c:if test="${selected.selType eq '5'}">selected="selected"</c:if>>食品</option>
										<option value="6" <c:if test="${selected.selType eq '6'}">selected="selected"</c:if>>母婴</option>
										<option value="7" <c:if test="${selected.selType eq '7'}">selected="selected"</c:if>>家电</option>
										<option value="8" <c:if test="${selected.selType eq '8'}">selected="selected"</c:if>>美妆</option>
										<option value="9" <c:if test="${selected.selType eq '9'}">selected="selected"</c:if>>鞋包</option>
										<!-- <option value="10">户外</option>
										<option value="11">生活服务</option> -->
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品金额：</label>
								<div class="controls">
									<input type="text" id="selPrice" name="selPrice" value="${selected.selPrice}" maxlength="10" class="input-large required isZhengshu"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">优先级：</label>
								<div class="controls">
									<input type="text" id="orderSec" name="orderSec" value="${selected.orderSec}" maxlength="20" class="input-large required isZhengshu"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">精选图片：</label>
								<div class="controls" id="detailDiv">
									<input id="btnPic"  class="btn btn-primary" type="button" value="添加图片"/>&nbsp;&nbsp;
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
						src="${ctx}/points/selected/uploadFile">
				</iframe>
			</div>
		</div>
	</div>
</body>
</html>