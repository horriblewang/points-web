<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<%@ include file="/WEB-INF/views/include/head.jsp"%>
		<%@ include file="/WEB-INF/views/include/dialog.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>文件上传</title>
	</head>
<body>
	<div id="uploadFileDialog" style="padding:10px;">
		<form id="publicattachmentadminform"  class="adminform" method="post" action="${ctx}/points/goods/addGoodsPic"  enctype="multipart/form-data" novalidate>
		    <input id="restMsg" name="restMsg" type="hidden" value="${restMsg}" />
		    <input id="restCode" name="restCode" type="hidden" value="${restCode}" />
		   	<input id="url" name="url" type="hidden" value="${url}" />
		   	<label for="String_fundAbbreviation_id">名称/标题:</label>
		   	<input name="fileTitle" id="fileTitle" type="text" >
		   	<label for="String_fundAbbreviation_id">附件:</label>
		    <input type="file" name="fileName" id="fileName"  onchange="changeFile();" > 
		</form>
	</div>
</body>
<script type="text/javascript">
$(function(){
	if($("#restCode").val() == '1'){
		uploadBrokerSucc();
	}else if($("#restCode").val() == '0'){
		top.$.jBox.tip('上传失败' + $("#restMsg").val(), 'error');
		$("#restMsg").val('');
	}
})
//上传成功
function uploadBrokerSucc(){
	parent.setGoodsPicUrl($("#url").val());
}

/**
 * 文件上传
 */
function changeFile(obj){
     var f = document.getElementById("fileName").value;
     if(f == ""){ 
    	 top.$.jBox.tip('请选择图片！', 'error');
    	 return false;
     }else{
	     if(!/\.(gif|jpg|png|GIF|JPG|PNG)$/.test(f)){
	       top.$.jBox.tip('图片类型必须是.gif,jpg,png中的一种！', 'error');
	       return false;
	     }
     }
    $("#publicattachmentadminform").submit();
}
</script>
</html>