<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品查询</title>
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
			$("#searchForm").attr("action", "${ctx}/points/goods").submit();
	    	return false;
	    }
		function del(goodsId){
			var submit = function (v, h, f) {
			    if (v == 'ok') {
			        $.jBox.tip("正在删除数据...", 'loading');
			        $.ajax({
			        	url  : "${ctx}/points/goods/del",
			        	type : "POST",
			        	data : {'goodsId' : goodsId},
			        	dataType: "json",
			            success: function(data){
			            	if (data != null && data.resCode == "00100000") {
			            		$("#btnSubmit").click();
			            		jBox.tip('操作成功。', 'success');
			            	}
			            }
			        })
			    }
			    return true; //close
			};
			$.jBox.confirm("确定要删除数据吗？", "提示", submit);
		}
		
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="${ctx}/points/goods">商品查询</a></li>
					<li><a href="${ctx}/points/goods/form">新增商品</a></li>
				</ul>
				<form:form id="searchForm" modelAttribute="goods"  method="post" class="breadcrumb form-search form-horizontal">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="planId" name="planId" type="hidden" value=""/>
					<div class="row-fluid">
					   <div class="span6">
							<div class="control-group">
								<label class="control-label">商品名称：</label>
								<div class="controls">
									<form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-large"/>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">商品类型：</label>
							<div class="controls">
								<select id="pcPicUrl" name="pcPicUrl" class="input-medium required">
									<option value="1">商品</option>
									<option value="2">积分</option>
								</select>
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
							<th>商品编号</th>
							<th>商品名称</th>
							<th>商品类型</th>
							<th>商品积分</th>
							<th>录入时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var= "item" items="${page.list}">
						<tr id="${item.goodsId}">
							<td width="20%">
								<a href="#" onclick="del('${item.goodsId}')">【删除】</a>&nbsp;&nbsp;
								<a href="${ctx}/points/goods/form?goodsId=${item.goodsId}">【修改】</a>
							</td>
							<td>${item.goodsId}</td>
							<td>${item.goodsName}</td>
							<td>
							<c:if test="${item.pcPicUrl eq '1'}">
							商品
							</c:if>
							<c:if test="${item.pcPicUrl eq '2'}">
							积分
							</c:if>
							</td>
							<td>${item.goodsPoints}</td>
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