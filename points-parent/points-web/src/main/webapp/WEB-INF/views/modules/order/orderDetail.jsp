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
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/order/detail?orderId=${orderInfo.orderId}">订单查询</a></li>
					<li><a href="${ctx}/points/order/list">订单查询</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="orderInfo"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="row-fluid">
					   <div class="span4">
							<div class="control-group">
								<label class="control-label">手机号：</label>
								<div class="controls">
									<form:input path="mobile" readonly="true" htmlEscape="false" maxlength="50" class="input-medium"/>
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">订单金额：</label>
								<div class="controls">
									<form:input path="points" readonly="true" htmlEscape="false" maxlength="50" class="input-medium"/>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<tags:message content="${message}"/>
				<table id="contentTable" class="table table-striped table-bordered table-condensed" style="font-size: 13px;">
					<thead>
						<tr>
							<th>订单明细</th>
							<th>商品名称</th>
							<th>价格</th>
							<th>数量</th>
							<th>购买时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${orderDetails}">
						<tr id="${item.detailId}">
							<td width="15%">${item.detailId}</td>
							<td width="15%">${item.goodsName}</td>
							<td>${item.goodsPoints}</a></td>
							<td>${item.goodsNum}</td>
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