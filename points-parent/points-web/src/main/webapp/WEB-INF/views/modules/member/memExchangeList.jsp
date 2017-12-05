<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>兑换记录查询</title>
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
			$("#searchForm").attr("action", "${ctx}/points/exchangeList").submit();
	    	return false;
	    }
		
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/exchangeList">兑换查询</a></li>
				</ul>
				
				<form:form id="searchForm" modelAttribute="memberInfo"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="planId" name="planId" type="hidden" value=""/>
					<div class="row-fluid">
					   <div class="span6">
							<div class="control-group">
								<label class="control-label">手机号：</label>
								<div class="controls">
									<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-large"/>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">商品名称：</label>
								<div class="controls">
									<input type="text" id="goodsName" name="goodsName" maxlength="50" class="input-large"> 
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
							<th>会员号</th>
							<th>手机号</th>
							<th>商品编号</th>
							<th>商品名称</th>
							<th>商品积分</th>
							<th>商品数量</th>
							<th>兑换时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.memberId}">
							<td>${item.memberId}</td>
							<td>${item.mobile}</td>
							<td>${item.goodsId}</td>
							<td>${item.goodsName}</td>
							<td>${item.goodsPoints}</td>
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