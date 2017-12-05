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
				$("#status").val("");
				$("#selType").val("");
				$("#s2id_selType").find("span:eq(0)").text("全部");
				$("#s2id_status").find("span:eq(0)").text("全部");
				$("#selName").val("");
			});
		});
		function page(n, s) {
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action", "${ctx}/points/selected").submit();
	    	return false;
	    }
		
		//禁用
		function disable(selId){
	    	var url="${ctx}/points/selected/disable?selId="+selId;
			return confirmx('确认要禁用精选吗？',url);
		}
		//启用
		function enable(selId){
			var url="${ctx}/points/selected/enable?selId="+selId;
			return confirmx('确认要启用吗？',url);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/selected">精选查询</a></li>
					<li><a href="${ctx}/points/selected/form">新增精选</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="selected"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="row-fluid">
						<div class="span4">
							<div class="control-group">
								<label class="control-label">精选类型：</label>
								<div class="controls">
									<select id="selType" name="selType" class="input-medium">
										<option value="">全部</option>
										<option <c:if test="${selected.selType == '2'}">selected="selected"</c:if> value="2">女装</option>
										<option <c:if test="${selected.selType == '3'}">selected="selected"</c:if> value="3">男装</option>
										<option <c:if test="${selected.selType == '4'}">selected="selected"</c:if> value="4">家居</option>
										<option <c:if test="${selected.selType == '5'}">selected="selected"</c:if> value="5">食品</option>
										<option <c:if test="${selected.selType == '6'}">selected="selected"</c:if> value="6">母婴</option>
										<option <c:if test="${selected.selType == '7'}">selected="selected"</c:if> value="7">家电</option>
										<option <c:if test="${selected.selType == '8'}">selected="selected"</c:if> value="8">美妆</option>
										<option <c:if test="${selected.selType == '9'}">selected="selected"</c:if> value="9">鞋包</option>
										<option <c:if test="${selected.selType == '10'}">selected="selected"</c:if> value="10">户外</option>
										<option <c:if test="${selected.selType == '11'}">selected="selected"</c:if> value="11">生活服务</option>
									<select>
								</div>
							</div>
						</div>
					   <div class="span4">
							<div class="control-group">
								<label class="control-label">精选名称：</label>
								<div class="controls">
									<form:input path="selName" htmlEscape="false" maxlength="50" class="input-medium"/>
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">精选状态：</label>
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
							<th>精选编号</th>
							<th>精选名称</th>
							<th>精选图片</th>
							<th>商品类型</th>
							<th>商品金额</th>
							<th>状态</th>
							<th>优先级</th>
							<th>录入时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.selId}">
							<td width="15%">
								<c:if test="${item.status eq '1'}">
									<a href="#" onclick="disable('${item.selId}')">【禁用】</a>&nbsp;&nbsp;
								</c:if>
								<c:if test="${item.status eq '0'}">
									<a href="#" onclick="enable('${item.selId}')">【启用】</a>&nbsp;&nbsp;
									<a href="${ctx}/points/selected/form?selId=${item.selId}">【修改】</a>&nbsp;&nbsp;
								</c:if>
							</td>
							<td>${item.selId}</td>
							<td>${item.selName}</td>
							<td><img height="50px" width="50px" src="${fns:getMainPic(item.selPics)}"/></td>
							<td>
							<c:choose>
								<c:when test="${item.selType eq '2'}">女装</c:when>
								<c:when test="${item.selType eq '3'}">男装</c:when>
								<c:when test="${item.selType eq '4'}">家居</c:when>
								<c:when test="${item.selType eq '5'}">食品</c:when>
								<c:when test="${item.selType eq '6'}">母婴</c:when>
								<c:when test="${item.selType eq '7'}">家电</c:when>
								<c:when test="${item.selType eq '8'}">美妆</c:when>
								<c:when test="${item.selType eq '9'}">鞋包</c:when>
								<c:when test="${item.selType eq '10'}">户外</c:when>
								<c:otherwise>生活服务</c:otherwise>
							</c:choose>
							</td>
							<td>${item.selPrice}</td>
							<td>
								<c:if test="${item.status eq '1'}">
									已启用
								</c:if>
								<c:if test="${item.status eq '0'}">
									已禁用
								</c:if>
							</td>
							<td>${item.orderSec}</td>
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