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
						top.$.jBox.tip('请选择购买商品！','error');
						return;
					}
					var arr = new Array();
					$("#contentTable tbody tr").each(function(i){
						var obj = {};
						$(this).find("td").each(function(j){
							if(j==1){
								obj['goodsId'] = $(this).attr("attr-name");
								obj['goodsName'] = $(this).text();
							}else if(j == 2){
								obj['goodsPoints'] = $(this).text();
							}else if(j == 3){
								obj['goodsNum'] = $(this).find("input:eq(0)").val();
							}
						});
						//alert(JSON.stringify(obj));
						arr.push(obj);
					});
					if (arr.length > 0){
					   $("#detailList").attr("value",JSON.stringify(arr));
					}
					var submit = function (v, h, f) {
					    if (v == 'ok'){
					    	$("#inputForm").submit();
					    }
					    return true; //close
					};
					top.$.jBox.confirm("确定提交订单吗？", "提示", submit);
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
				htl += '<td><input type="text" name="goodsNum" value="1"  class="input-medium required isZhengshu" attr-points="'+ points +'" onchange="calTotal(this)"/></td>';
				$("#contentTable tbody").append(htl);
				calTotal();
			});
		})
		
		function del(ths){
			$(ths).parent().parent().remove();
			calTotal();
		}
		
		function calTotal(ths){
			var sum  = 0;
			$(":input[name='goodsNum']").each(function(){
				sum += parseInt($(this).val()) * parseInt($(this).attr("attr-points"));
			});
			$("#sumPoints").val(sum);
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				  <ul class="nav nav-tabs">
						<li class="active"><a href="${ctx}/points/order/form?memberId=${memberInfo.memberId}">新建订单</a></li>
						<li><a href="${ctx}/points/order/list">订单列表</a></li>
				</ul>
				<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/points/order/save" method="post" class="breadcrumb form-search form-horizontal">
					<input type="hidden" name="detailList" id="detailList"/>
					<input type="hidden" name="memberId" value="${memberInfo.memberId}"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">会员名称：</label>
								<div class="controls">
									<form:input path="mobile" readonly="true"  class="input-medium required"/>							
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
							<label class="control-label">会员积分：</label>
								<div class="controls">
									<input type="text" name="aPoint" readonly="true"  class="input-medium required" value="${memberInfo.points}"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">积分规则：</label>
								<div class="controls">
									<select name="ruleId" id="ruleId" class="input-medium m-wrap required">
										<c:forEach var="item" items="${rules}">
											<option value="${item.ruleId}">${item.ruleName}</option>
										</c:forEach>
									</select>						
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">总计金额：</label>
								<div class="controls">
									<input type="text" name="points" id="sumPoints" readonly="true" value="0" class="input-medium"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">购买商品：</label>
								<div class="controls">
									<select name="sel_goods" id="sel_good" class="input-medium m-wrap">
										<c:forEach var="item" items="${goodsList}">
											<option value="${item.goodsId}">${item.goodsName}|${item.goodsPoints}</option>
										</c:forEach>
									</select>
									<input type="button"  id="buttonAddShop" class="btn btn-primary" value="添加商品"/>	
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
									<th>价格</th>
									<th>数量</th>
								</tr>
							</thead>
							<tbody id="content_body">
								
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