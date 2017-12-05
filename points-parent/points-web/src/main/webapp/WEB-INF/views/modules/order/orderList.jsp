<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单查询</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate();
			$("#btnClear").bind("click",function(){
				$("#mobile").val("");
			});
		});
		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action", "${ctx}/points/order/list").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/order/list">订单查询</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="orderInfo"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="row-fluid">
					   <div class="span4">
							<div class="control-group">
								<label class="control-label">手机号：</label>
								<div class="controls">
									<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
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
							<th>订单编号</th>
							<th>会员编号</th>
							<th>手机号</th>
							<th>订单金额</th>
							<th>订单规则</th>
							<th>每日赠送积分</th>
							<th>邀请奖励</th>
							<th>间接推荐奖励</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.orderId}">
							<td width="15%">
								<a href="${ctx}/points/order/detail?orderId=${item.orderId}">${item.orderId}</a>
							</td>
							<td>${item.memberId}</a></td>
							<td>${item.mobile}</td>
							<td>${item.points}</td>
							<td>${item.ruleName}</td>
							<td>${item.dayPoints}</a></td>
							<td>${item.firstRate}%</td>
							<td>${item.secRate}%</td>
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