<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动详情</title>
	<meta name="decorator" content="default" />
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/exchange/list">活动列表</a></li>
				</ul>
				<form:form id="inputForm" modelAttribute="activity"  method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="actId"/>
					<input type="hidden" name="detailList" id="detailList"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">活动名称：</label>
							<div class="controls">
							  <form:input path="actName" readonly="true" htmlEscape="false" maxlength="50" class="input-medium required"/>
							</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
							<label class="control-label">活动描述：</label>
							<div class="controls">
							  <form:input path="actDesc" readonly="true" htmlEscape="false" maxlength="150" class="input-medium required"/>
							</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">开始时间：</label>
								<div class="controls">
									<input type="text" id="startTime" name="startTimeStr" readonly="readonly"  value="<fmt:formatDate value="${activity.startTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-medium Wdate required valid" />								
							</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">结束时间：</label>
								<div class="controls">
									<input type="text" id="endTime" name="endTimeStr" readonly="readonly" value="<fmt:formatDate value="${activity.endTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-medium Wdate required valid" />								
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<table style="font-size: 13px;"id="contentTable" class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>商品</th>
									<th>积分</th>
									<th>排序</th>
								</tr>
							</thead>
							<tbody id="content_body">
								<c:forEach var="item" items="${details}">
									<tr>
									<td>${item.goodsName}</td>
									<td>${item.percent}</td>
									<td>${item.orderSec}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="form-actions">
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			 </form:form>
		</div>
	</div>
</body>
</html>