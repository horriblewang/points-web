<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动查询</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate();
			$("#btnClear").bind("click",function(){
				$("#actName").val("");
			});
		});
		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action", "${ctx}/points/activity/list").submit();
	    	return false;
	    }
		//禁用
		function disable(actId){
	    	var url="${ctx}/points/activity/disable?actId="+actId;
			return confirmx('确认要禁用活动吗？',url);
		}
		//启用
		function enable(actId){
			var url="${ctx}/points/activity/enable?actId="+actId;
			return confirmx('确认要启用吗？',url);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/activity/list">活动查询</a></li>
					<li><a href="${ctx}/points/activity/form">新增活动</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="activity"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="hotSale" name="hotSale" type="hidden" value="1"/>
					<div class="row-fluid">
					   <div class="span4">
							<div class="control-group">
								<label class="control-label">活动名称：</label>
								<div class="controls">
									<form:input path="actName" htmlEscape="false" maxlength="50" class="input-medium"/>
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">活动状态：</label>
								<div class="controls">
									<select id="status"  name="status" class="input-small">
										<option value="">全部</option>
										<option <c:if test="${activity.status == '0'}">selected="selected"</c:if> value="0">禁用</option>
										<option <c:if test="${activity.status == '1'}">selected="selected"</c:if> value="1">启用</option>
									</select>
								</div>
							</div>
						</div>
						<div class="span3">
							<div class="control-group">
								<label class="control-label">运行状态：</label>
								<div class="controls">
									<select id="processStatus" name="processStatus" class="input-small">
										<option value="">全部</option>
										<option <c:if test="${activity.processStatus == '01'}">selected="selected"</c:if> value="01">待运行</option>
										<option <c:if test="${activity.processStatus == '02'}">selected="selected"</c:if> value="02">运行中</option>
										<option <c:if test="${activity.processStatus == '03'}">selected="selected"</c:if> value="03">已过期</option>
									</select>
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
							<th>活动编号</th>
							<th>活动名称</th>
							<th>活动状态</th>
							<th>运行状态</th>
							<th>活动描述</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.actId}">
							<td width="15%">
								<c:if test="${item.status == '1'}">
									<a href="#" onclick="disable('${item.actId}')">【禁用】</a>
								</c:if>
								<c:if test="${item.status == '0'}">
									<a href="#" onclick="enable('${item.actId}')">【启用】</a>
								</c:if>
								&nbsp;&nbsp;
								<a href="${ctx}/points/activity/form?actId=${item.actId}">【修改】</a>
							</td>
							<td><a href="${ctx}/points/activity/detail?actId=${item.actId}">${item.actId}</a></td>
							<td>${item.actName}</td>
							<td>
								<c:choose>
									<c:when test="${item.status eq '0'}">
									已禁用
									</c:when>
									<c:otherwise>
									已启用
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.processStatus eq '01'}">
									待运行
									</c:when>
									<c:when test="${item.processStatus eq '02'}">
									运行中
									</c:when>
									<c:otherwise>
									已过期
									</c:otherwise>
								</c:choose>
							</td>
							<td>${item.actDesc}</td>
							<td><fmt:formatDate value="${item.startTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${item.endTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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