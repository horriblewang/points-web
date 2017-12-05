<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖记录查询</title>
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
			$("#searchForm").attr("action", "${ctx}/points/awards/list").submit();
	    	return false;
	    }
		//启用
		function receive(actId){
			var url="${ctx}/points/awards/use?id="+actId;
			return confirmx('确认领取吗？',url);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/awards/list">中奖查询</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="awards"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="row-fluid">
					   <div class="span4">
							<div class="control-group">
								<label class="control-label">手机号：</label>
								<div class="controls">
									<form:input path="mobile" htmlEscape="false"  class="input-medium"/>
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">领取状态：</label>
								<div class="controls">
									<select id="status"  name="status" class="input-medium">
										<option value="">全部</option>
										<option <c:if test="${awards.status == '0'}">selected="selected"</c:if> value="0">未领取</option>
										<option <c:if test="${awards.status == '1'}">selected="selected"</c:if> value="1">已领取</option>
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
							<th>奖品编号</th>
							<th>会员编号</th>
							<th>手机号</th>
							<th>活动编号</th>
							<th>活动名称</th>
							<th>商品编号</th>
							<th>商品名称</th>
							<th>状态</th>
							<th>中奖时间</th>
							<th>领取时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.id}">
							<td width="10%">
								<c:if test="${item.status == '0'}">
									<a href="#" onclick="receive('${item.id}')">【领取】</a>
								</c:if>
							</td>
							<td>${item.id}</td>
							<td>${item.memberId}</td>
							<td>${item.mobile}</td>
							<td>${item.actId}</td>
							<td>${item.actName}</td>
							<td>${item.goodsId}</td>
							<td>${item.goodsName}</td>
							<td>	
								<c:if test="${item.status eq '0'}">未领取</c:if>
								<c:if test="${item.status eq '1'}">已领取</c:if>
							</td>
							<td><fmt:formatDate value="${item.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${item.useTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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