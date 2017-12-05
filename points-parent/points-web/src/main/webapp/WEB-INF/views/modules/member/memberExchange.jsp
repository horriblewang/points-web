<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品兑换</title>
	<meta name="decorator" content="default" />
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(function() {
		$("#inputForm").validate();
		$.validator.addMethod("isZhengshu", function(value, element) {   
			var reg = /^[0-9]*[1-9][0-9]*$/;
			var temp = true;
		    if (!reg.test(value)) {
		    	temp = false;
		    }
		    return temp;
		}, "'值' 请输入正整数");
		/** 添加门店*/
		$("#buttonAddShop").click(function(){
			var htl = '<tr>'
			htl +=	"<td><a href='#' onclick='del(this)'>【删除】</a>";
			htl += '</td>';
			var goodName = $("#sel_good").find('option:selected').text();
			var goodid = $("#sel_good").val();
			var points = goodName.split("|")[1];
			htl += '<td attr-name="'+goodid +'">' + goodName + '</td>';
			htl += '<td><input type="text" name="godsNum" class="input-medium required isZhengshu" attr-points="'+ points +'" onchange="addTotal(this)"/></td>';
			htl += '<td><input type="text" name="total" value="0" readonly="true" class="input-medium"/></td>';
			$("#contentTable tbody").append(htl);
		});
		
		$("#btnSave").click(function(){
			if ($("#inputForm").valid()){
				$(this).attr("disabled",true);
			}else{
				return false;
			}
			//校验是否兑换积分超额
			if(parseInt($("#sumPoints").val()) > parseInt($("#points").val())){
				top.$.jBox.tip("积分不足，无法兑换！",'error');
				$(this).attr("disabled",false);
				return false;
			}
			var arr = new Array();
			$("#contentTable tbody tr").each(function(i){
				var obj = {};
				$(this).find("td").each(function(j){
					if(j==1){
						obj['goodsId'] = $(this).attr("attr-name");
						obj['goodsName'] = $(this).text().split("|")[0];
						obj['goodsPoints'] = $(this).text().split("|")[1];
					}else if(j == 2){
						obj['goodsNum'] = $(this).find("input:eq(0)").val();
					}
				});
				//alert(JSON.stringify(obj));
				arr.push(obj);
			});
			if (arr.length > 0){
			   $("#goodsList").attr("value",JSON.stringify(arr));
			}
			$("#inputForm").submit();
		});
	});
	
	function del(ths){
		$(ths).parent().parent().remove();
		calTotal();
	}
	//计算每行的积分总数
	function addTotal(ths){
		var ve = $(ths).val();
		if(!isNaN(ve)){
			var point =  $(ths).attr("attr-points");
			$(ths).parent().parent().find(":input[name='total']").val(ve*point);
		}else{
			$(ths).parent().parent().find(":input[name='total']").val('0');
		}
		calTotal();
	}
	//计算总分
	function calTotal(){
		var sum  = 0;
		$(":input[name='total']").each(function(){
			sum += parseInt($(this).val());
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
					<li class="active"><a href="${ctx}/points/redeem?memberId=${memberInfo.memberId}">积分兑换礼品</a></li>
					<li><a href="${ctx}/points/member">会员查询</a></li>
				</ul>
				<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/points/exGoods" method="post" class="breadcrumb form-search form-horizontal">
					<form:hidden path="memberId" />
					<input type="hidden" id="goodsList" name="goodsList"/>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
							<label class="control-label">会员名称：</label>
							<div class="controls">
							  <form:input path="memberName" readonly="true" htmlEscape="false" maxlength="30" class="input-medium"/>
							</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">手机号：</label>
								<div class="controls">
									<form:input path="mobile" readonly="true"  htmlEscape="false" maxlength="30" class="input-medium"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">可用积分：</label>
								<div class="controls">
									<form:input path="points"  readonly="true"  htmlEscape="false" maxlength="30" class="input-medium"/>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">总计：</label>
								<div class="controls">
									<input type="text" name="sumPoints" id="sumPoints" readonly="true" value="0" class="input-medium"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">可选礼品：</label>
								<div class="controls">
									<select id="sel_good" class="input-medium m-wrap required">
										<c:forEach var="item" items="${goodsList}">
											<option value="${item.goodsId}">${item.goodsName}|${item.goodsPoints}</option>
										</c:forEach>
									</select>
									<input type="button" id="buttonAddShop" class="btn btn-primary" value="兑换礼品"/>	
								</div>
							</div>
						</div>
					</div>
				<tags:message content="${message}"/>
				<table style="font-size: 13px;"id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>操作</th>
							<th>商品</th>
							<th>兑换数量</th>
							<th>所需积分</th>
						</tr>
					</thead>
					<tbody id="content_body">
						
					</tbody>
				</table>
				<div class="form-actions">
					<input id="btnSave"  class="btn btn-primary" type="submit" value="确 定"/>&nbsp;&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1);"/>
				</div>
				</div>
			 </form:form>
		</div>
	</div>
</body>
</html>