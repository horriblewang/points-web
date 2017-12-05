<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动管理</title>
	<meta name="decorator" content="default" />
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(function(){
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
					$("#inputForm").submit();
				}else{
					return;
				}
			});
		})
		
		
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
				  		<c:if test="${not empty ruleInfo.ruleId}">
				  			<li class="active"><a href="${ctx}/points/rule/form?ruleId=${ruleInfo.ruleId}">修改规则</a></li>
				  		</c:if>
				  		<c:if test="${empty ruleInfo.ruleId}">
				  			<li class="active"><a href="${ctx}/points/rule/form?ruleId=${ruleInfo.ruleId}">新建规则</a></li>
				  		</c:if>
						<li><a href="${ctx}/points/rule/list">规则列表</a></li>
				</ul>
				<form:form id="inputForm" modelAttribute="ruleInfo" action="${ctx}/points/rule/save" method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="ruleId"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">规则名称：</label>
								<div class="controls">
									<form:input path="ruleName" maxlength="50"  class="input-large required"/>							
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">每日赠送积分：</label>
								<div class="controls">
									<form:input path="dayPoints"  class="input-large required isZhengshu"/>							
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">邀请注册奖励（%）：</label>
								<div class="controls">
									<form:input path="firstRate"  class="input-large required isZhengshu"/>							
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">间接推荐奖励（%）：</label>
								<div class="controls">
									<form:input path="secRate"  class="input-large required isZhengshu"/>							
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
		</div>
	</div>
</body>
</html>