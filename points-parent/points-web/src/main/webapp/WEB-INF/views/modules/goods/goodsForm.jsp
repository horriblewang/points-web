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
			$("#inputForm").validate();
			$.validator.addMethod("isZhengshu", function(value, element) {   
				var reg = /^-?\d+$/;
				var temp = true;
			    if (!reg.test(value)) {
			    	temp = false;
			    }
			    return temp;
			}, "'值' 请输入正整数");
			$("#btnajax").click(function(){
				$.ajax({
					type : 'GET',
					url : 'http://119.23.48.99/points-web/api/activity/getLuckyDraw',
					dataType : 'jsonp',
					data: {'memberId':'1013','remark':encodeURI('你不会')},
					jsonpCallback:'jsonpCallback', 
					success:function(result){ //成功执行处理，对应后台返回的jsonpCallback(data)方法。
						console.log(result);
			        	alert(result.resCode);
						/* var i = 0;
						var bans = result.obj.banners;
						$(bans).each(function(i){
							alert(bans[i].banUrl);
						}) */
					},
			        error:function(msg){
			            alert('error');      //执行错误
			        }
				})
			});
			
			$("#btnSave").click(function(){
				if($("#inputForm").valid()){
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
		function setGoodsPicUrl(url){
			top.$.jBox.tip('上传成功', 'success');
			$("#appPicUrl").val(url);
			$("#img_goodsPic").attr('src',url);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
					<c:if test="${goods.goodsId!=null}">
						<li class="active"><a href="${ctx}/points/goods/form?goodsId=${goods.goodsId}">修改商品</a></li>
					</c:if>
					<c:if test="${goods.goodsId==null}">
						<li class="active"><a href="${ctx}/points/goods/form">新建商品</a></li>
					</c:if>
				</ul>
				<form:form id="inputForm" modelAttribute="goods" action="${ctx}/points/goods/save" method="post" class="breadcrumb form-search form-horizontal">
					<input type="hidden" id="goodsId" name="goodsId" value="${goods.goodsId}" />
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">商品名称：</label>
							<div class="controls">
							  <form:input path="goodsName"  htmlEscape="false" maxlength="50" class="input-large required"/>
							</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品积分：</label>
								<div class="controls">
									<input type="text" id="goodsPoints" name="goodsPoints" value="${goods.goodsPoints}" maxlength="20" class="input-large required isZhengshu"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品类型：</label>
								<div class="controls">
									<select id="pcPicUrl" name="pcPicUrl" class="input-large required">
										<option value="1" <c:if test="${goods.pcPicUrl eq '1'}"> selected="selected"</c:if>>商品</option>
										<option value="2" <c:if test="${goods.pcPicUrl eq '2'}"> selected="selected"</c:if>>积分</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品图片：</label>
								<div class="controls">
									<div> <img id="img_goodsPic" alt="商品图片" src="${goods.appPicUrl}"/> </div>
									<input type="hidden" name="appPicUrl" id="appPicUrl" value="${goods.appPicUrl}"/>
									<input id="btnPic"  class="btn btn-primary" type="button" value="上传图片"/>&nbsp;&nbsp;
								</div>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<input id="btnSave"  class="btn btn-primary" type="button" value="保 存"/>&nbsp;&nbsp;
						<!-- <input id="btnajax"  class="btn btn-primary" type="button" value="ajax"/>&nbsp;&nbsp; -->
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			 </form:form>
			 <div id="publicattachmentdialog_upload" icon="icon-save" style="padding:10px;display:none;width: auto;z-index: 9999;margin-top: 10">
				 <iframe name="picdialog_upload" id="picdialog_upload"  style="border:0px;" height="100px" marginwidth="0" marginheight="0" frameborder="0" width="100%;" 
						src="${ctx}/points/goods/uploadFile">
				</iframe>
			</div>
		</div>
	</div>
</body>
</html>