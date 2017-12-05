
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推广查询</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/dialog.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate();
			$.jBox.closeTip();
			$("#btnClear").bind("click",function(){
				$("#banName").val("");
			});
		});
		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action", "${ctx}/points/banner").submit();
	    	return false;
	    }
		
		//禁用
		function disable(banId){
	    	var url="${ctx}/points/banner/disable?banId="+banId;
			return confirmx('确认要禁用活动吗？',url);
		}
		//启用
		function enable(banId){
			var url="${ctx}/points/banner/enable?banId="+banId;
			return confirmx('确认要启用吗？',url);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/banner">推广查询</a></li>
					<li><a href="${ctx}/points/banner/form">新增推广</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="banner"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input name="banType" type="hidden" value="1"/>
					<div class="row-fluid">
					   <div class="span6">
							<div class="control-group">
								<label class="control-label">推广名称：</label>
								<div class="controls">
									<form:input path="banName" htmlEscape="false" maxlength="50" class="input-large"/>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">推广状态：</label>
								<div class="controls">
									<select id="status"  name="status" class="input-small">
										<option value="">全部</option>
										<option <c:if test="${banner.status == '0'}">selected="selected"</c:if> value="0">禁用</option>
										<option <c:if test="${banner.status == '1'}">selected="selected"</c:if> value="1">启用</option>
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
							<th>推广编号</th>
							<th>推广名称</th>
							<th>商品价格</th>
							<th>状态</th>
							<th>优先级</th>
							<th>推广详情</th>
							<th>推广图片</th>
							<th>录入时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.banId}">
							<td width="15%">
								<c:if test="${item.status eq '1'}">
									<a href="#" onclick="disable('${item.banId}')">【禁用】</a>&nbsp;&nbsp;
								</c:if>
								<c:if test="${item.status eq '0'}">
									<a href="#" onclick="enable('${item.banId}')">【启用】</a>&nbsp;&nbsp;
									<a href="${ctx}/points/banner/form?banId=${item.banId}">【修改】</a>&nbsp;&nbsp;
								</c:if>
							</td>
							<td>${item.banId}</td>
							<td>${item.banName}</td>
							<td>${item.reserve1}</td>
							<td>
								<c:if test="${item.status eq '1'}">
									已启用
								</c:if>
								<c:if test="${item.status eq '0'}">
									已禁用
								</c:if>
							</td>
							<td>${item.orderSec}</td>
							<td>${fns:abbr(item.jumpUrl,16)}</td>
							<td><img height="50" width="100" src="${item.banUrl}"/></td>
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