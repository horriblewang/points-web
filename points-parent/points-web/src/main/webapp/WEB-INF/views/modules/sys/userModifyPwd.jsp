<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				 rules:{
					 newPassword:{
						 judgePassword:true
	                    }
				 }, 
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				}
			});
			
			$.validator.addMethod("judgePassword",function(value,element,params){
				var count = 0; //满足的规则数量
				var digital = /[0-9]/; //数字正则
				var capital = /[A-Z]/; //大写字母正则
				var lowercase =/[a-z]/; //小写字母正则
				var spec=/[,.<>{}~!@#$%^&*_]/; //特殊字符正则
				var password = value;
				//判断密码是否包含数字
				if(digital.test(password)){
					count++;
				}
				//判断密码是否包字母
				if(lowercase.test(password) || capital.test(password)){
					count++;
				}
				////判断密码是否包含大小写
				if(lowercase.test(password) && capital.test(password)){
					count++;
				}
				//判断密码是否包含特殊字符
				if(spec.test(password)){
					count++;
				}
				if(count>=3){
					$('.btn-primary').attr('disabled', false);
					return true;
				}else{
					$('.btn-primary').attr('disabled', true);
					return false;
				}
			},"大写字母，小写字母，数字，特殊字符如下划线等至少包含3种");
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="currentUser" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<tags:message content="${message}"/>
		
		<div class="control-group">
			<label class="control-label" for="oldPassword">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="newPassword">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="8" class="required" onblur="judgePassword(this.value)"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="confirmNewPassword">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="8" class="required" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>