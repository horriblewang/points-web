<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员查询</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate();
			$.jBox.closeTip();
			$("#btnClear").bind("click",function(){
				$("#searchForm :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");//核心
			});
		});
		function page(n, s) {
			$("#pageNo").val(n);	
			$("#pageSize").val(s);	
			$("#searchForm").attr("action", "${ctx}/points/member").submit();
	    	return false;
	    }
		
		function addPoints(id,mo){
			var html = "<div style='padding:10px;'>积分量：<input type='text' id='points' name='points' /></div>";
			var submit = function (v, h, f) {
				if(v == true){
					if (f.points == '') {
						top.$.jBox.tip("请输入增加积分数量。", 'error', { focusId: "points" }); // 关闭设置 yourname 为焦点
				        return false;
				    }
					var g = /^[1-9]*[1-9][0-9]*$/;
					if(!g.test(f.points)){
						top.$.jBox.tip("积分请输入正整数", 'error'); // 关闭设置 yourname 为焦点
				        return false;
					}
					$("#memberId").val(id);
					$("#points").val(f.points);
					top.$.jBox.tip("正在加载，请稍后...",'loading');
					$("#addPoint").submit();
				    return true;
				}
			};
			top.$.jBox(html, { title: mo + "积分增加", submit: submit,buttons: { '确定': true, '关闭': false}});
		}
		
		//转移积分
		function transPoints(id,mo){
			var html = "<div style='padding:10px;'><label class='control-label'>积分量：</label><input type='text' id='points' name='points' /><p><label class='control-label'>转移给：</label><input type='text' id='mobile' name='mobile' /></div>";
			var submit = function (v, h, f) {
				if(v == true){
					$("#transMember").val(id);
					if (f.points == '') {
				        top.$.jBox.tip("请输入转移积分数量。", 'error', { focusId: "points" }); // 关闭设置 yourname 为焦点
				        return false;
				    }
					var g = /^[1-9]*[1-9][0-9]*$/;
					if(!g.test(f.points)){
						top.$.jBox.tip("积分请输入正整数", 'error');
				        return false;
					}
					if (f.mobile == '') {
				        top.$.jBox.tip("请输入转移人手机号。", 'error');
				        return false;
				    }
					var pa = /^1[345678]\d{9}$/;
					if(!pa.test(f.mobile)){
						top.$.jBox.tip("手机号码格式错误", 'error');
				        return false;
					}
					$("#transPoints").val(f.points);
					$("#transMobile").val(f.mobile);
					top.$.jBox.tip("正在加载，请稍后...",'loading');
					$("#transPoint").submit();
				    return true;
				}
			};
			top.$.jBox(html, { title: mo + "积分转移", submit: submit,buttons: { '确定': true, '关闭': false}});
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/member">会员查询</a></li>
				</ul>
				<form id="transPoint" action="${ctx}/points/transPoints" method="post">
					<input type="hidden" name="memberId" id="transMember"/>
					<input type="hidden" name="mobile" id="transMobile"/>
					<input type="hidden" name="points" id="transPoints"/>
				</form>
				<form id="addPoint" action="${ctx}/points/addPoints" method="post">
					<input type="hidden" name="memberId" id="memberId"/>
					<input type="hidden" name="points" id="points"/>
				</form>
				<form:form id="searchForm" modelAttribute="memberInfo"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="planId" name="planId" type="hidden" value=""/>
					<div class="row-fluid">
					   <div class="span6">
							<div class="control-group">
								<label class="control-label">会员手机号：</label>
								<div class="controls">
									<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-large"/>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">推荐人：</label>
								<div class="controls">
									<form:input path="referTo" htmlEscape="false" maxlength="50" class="input-large"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div style="margin-left:180px;">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询" onclick="return page();"/>
							<input id="btnClear" class="btn btn-primary" type="button" value="清空" />
						</div>	
					</div>
				</form:form>
				
				<tags:message content="${message}"/>
				<table id="contentTable" class="table table-striped table-bordered table-condensed" style="font-size: 13px;">
					<thead>
						<tr>
							<th>操作</th>
							<th>会员号</th>
							<th>会员手机号</th>
							<th>会员积分</th>
							<th>推荐人</th>
							<th>已推荐人数</th>
							<th>注册时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.memberId}">
							<td width="25%">
								<a href="#" onclick="addPoints('${item.memberId}','${item.mobile}')">【增加积分】</a>
								<a href="${ctx}/points/redeem?memberId=${item.memberId}">【积分兑换】</a>
								<a href="${ctx}/points/order/form?memberId=${item.memberId}">【新增订单】</a>
								<a href="#" onclick="transPoints('${item.memberId}','${item.mobile}')">【转移积分】</a>
							</td>
							<td>${item.memberId}</td>
							<td>${item.mobile}</td>
							<td>${item.points}</td>
							<td>${item.referTo}</td>
							<td>${item.referNum}</td>
							<td><fmt:formatDate value="${item.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination">${page}</div>
			</div>
		</div>
	</div>
</body>
</html>