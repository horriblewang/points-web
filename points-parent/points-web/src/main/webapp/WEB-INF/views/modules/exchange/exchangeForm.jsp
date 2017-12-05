<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖活动管理</title>
	<meta name="decorator" content="default" />
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<script type="text/javascript">
		$(function(){
			$("#inputForm").validate();
			$.validator.addMethod("isZhengshu", function(value, element) {   
				var reg = /^[0-9]*[1-9][0-9]*$/;
				var temp = true;
			    if (!reg.test(value)) {
			    	temp = false;
			    }
			    return temp;
			}, "'值' 请输入正整数");
			$("#btnSave").click(function(){
				if($("#inputForm").valid()){
					var size = $("#contentTable tbody").find("tr").size();
					if(size == 0){
						top.$.jBox.tip('请添加兑换礼品！','error');
						return;
					}
					var arr = new Array();
					$("#contentTable tbody tr").each(function(i){
						var obj = {};
						$(this).find("td").each(function(j){
							if(j==1){
								obj['goodsId'] = $(this).attr("attr-name");
							}else if(j == 2){
								obj['percent'] = $(this).text();
							}else if(j == 3){
								obj['orderSec'] = $(this).find("input:eq(0)").val();
							}
						});
						//alert(JSON.stringify(obj));
						arr.push(obj);
					});
					if (arr.length > 0){
					   $("#detailList").attr("value",JSON.stringify(arr));
					}
					$("#inputForm").submit();
				}else{
					return;
				}
			});
			
			/** 添加门店*/
			$("#buttonAddShop").click(function(){
				var htl = '<tr>'
				htl +=	"<td><a href='#' onclick='del(this)'>【删除】</a>";
				htl += '</td>';
				var goodName = $("#sel_good").find('option:selected').text();
				var goodid = $("#sel_good").val();
				var name = goodName.split("|")[0];
				var points = goodName.split("|")[1];
				htl += '<td attr-name="'+goodid +'">' + name + '</td>';
				htl += '<td>'+ points +'</td>';
				htl += '<td><input type="text" name="orderSec" value="1"  class="input-medium required isZhengshu"/></td>';
				$("#contentTable tbody").append(htl);
			});
		})
		
		function del(ths){
			$(ths).parent().parent().remove();
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
					<c:if test="${not empty activity.actId}">
						<li class="active"><a href="${ctx}/points/exchange/form?actId=${activity.actId}">修改活动</a></li>
					</c:if>
					<c:if test="${empty activity.actId}">
						<li class="active"><a href="${ctx}/points/exchange/form">新建活动</a></li>
					</c:if>
					<c:if test="${empty activity.actId}">
						<li><a href="${ctx}/points/exchange/list">活动列表</a></li>
					</c:if>
				</ul>
				<form:form id="inputForm" modelAttribute="activity" action="${ctx}/points/exchange/save" method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="actId"/>
					<input type="hidden" name="detailList" id="detailList"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">活动名称：</label>
							<div class="controls">
							  <form:input path="actName"  htmlEscape="false" maxlength="50" class="input-medium required"/>
							</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
							<label class="control-label">活动描述：</label>
							<div class="controls">
							  <form:input path="actDesc"  htmlEscape="false" maxlength="150" class="input-medium required"/>
							</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">开始时间：</label>
								<div class="controls">
									<input type="text" id="startTime" name="startTime" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00:00',isShowClear:false,maxDate:'#F{$dp.$D(\'endTime\')}'});" value="<fmt:formatDate value="${activity.startTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-medium Wdate required valid" />								
							</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">结束时间：</label>
								<div class="controls">
									<input type="text" id="endTime" name="endTime" readonly="readonly" onclick="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:false,minDate:'#F{$dp.$D(\'startTime\')}'});" value="<fmt:formatDate value="${activity.endTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" class="input-medium Wdate required valid" />								
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">兑换礼品：</label>
								<div class="controls">
									<select name="sel_goods" id="sel_good" class="input-medium m-wrap">
										<c:forEach var="item" items="${goodsList}">
											<option value="${item.goodsId}">${item.goodsName}|${item.goodsPoints}</option>
										</c:forEach>
									</select>
									<input type="button"  id="buttonAddShop" class="btn btn-primary" value="添加奖品"/>	
								</div>
							</div>
						</div>
					</div>
					<tags:message content="${message}"/>
					<div class="row-fluid">
						<table style="font-size: 13px;"id="contentTable" class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>操作</th>
									<th>商品</th>
									<th>积分</th>
									<th>排序</th>
								</tr>
							</thead>
							<tbody id="content_body">
								<c:forEach var="item" items="${details}">
									<tr>
									<td><a href='#' onclick='del(this)'>【删除】</a></td>
									<td attr-name='${item.goodsId}'>${item.goodsName}</td>
									<td>${item.percent}</td>
									<td><input type="text" name="orderSec" value="${item.orderSec}" class="input-medium required isZhengshu valid"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="form-actions">
						<input id="btnSave"  class="btn btn-primary" type="button" value="保 存"/>&nbsp;&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			 </form:form>
		</div>
	</div>
</body>
</html>