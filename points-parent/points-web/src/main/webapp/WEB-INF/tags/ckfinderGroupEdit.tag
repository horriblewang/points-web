<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、flash、thumb"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<ol id="${input}Preview"></ol><a href="javascript:" onclick="${input}FinderOpen();" class="btn">${selectMultiple?'添加':'选择'}</a>&nbsp;<a href="javascript:" onclick="${input}DelAll();" class="btn">清除</a>
<script type="text/javascript">
	function ${input}FinderOpen(){//<c:if test="${type eq 'thumb'}"><c:set var="ctype" value="images"/></c:if><c:if test="${type ne 'thumb'}"><c:set var="ctype" value="${type}"/></c:if>
		var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
		var url = "${ctxStatic}/ckfinder/ckfinder.html?type=${ctype}&start=${ctype}:${uploadPath}/"+year+"/"+month+
			"/&action=js&func=${input}SelectAction&thumbFunc=${input}ThumbSelectAction&cb=${input}Callback&dts=${type eq 'thumb'?'1':'0'}&sm=${selectMultiple?1:0}";
		//windowOpen(url,"文件管理",1000,700);
		top.$.jBox("iframe:"+url+"&pwMf=1", {title: "文件管理", width: 1000, height: 600, opacity: 0.5,buttons:{'关闭': true}});
	}
	function ${input}SelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){//<c:if test="${type eq 'thumb'}">
			url += files[i].getThumbnailUrl();//</c:if><c:if test="${type ne 'thumb'}">
			url += files[i].getUrl();//</c:if>
			if (i<files.length-1) url+="|";
		}//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);//</c:if>
		${input}Preview();
		top.$.jBox.close();
	}
	function ${input}ThumbSelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){
			url += files[i].getThumbnailUrl();
			if (i<files.length-1) url+="|";
		}//<c:if test="${selectMultiple}">
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));//</c:if><c:if test="${!selectMultiple}">
		$("#${input}").val(url);//</c:if>
		${input}Preview();
		top.$.jBox.close();
	}
	function ${input}Callback(api){
		ckfinderAPI = api;
	}
	function ${input}Del(obj){
		var url = $(obj).attr("attr-url");
		$("#${input}").val($("#${input}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
		$(obj).parent().parent().remove();
		//${input}Preview();
	}
	function ${input}DelAll(){
		$("#${input}").val("");
		${input}Preview();
	}
	
	function ${input}Preview(){
        //删除重复
        var url ="";
        var li, urls = $("#${input}").val();
        if(urls==null || ""==urls){
            return;
        }
        urls = $("#${input}").val().split("|");
        for (var i=0; i<urls.length; i++){
            if(url.indexOf(urls[i] + "|")<0) {
                url=url+urls[i]+"|";
            }
        }
        if(url.length>0) {
            url = url.substring(0,url.length-1);
        }
        $("#${input}").val(url);
        urls=url.split("|");
        $("#${input}Preview").children().remove();
        
        /* Add 马亮亮 Mall Add date:2015-06-03 begin 增加一个N行5列的表格 */
        li = "<table style='text-align:center;' class='table table-striped table-bordered table-condensed'><tr><th>图片</th><th>渠道</th><th>备注</th><th>类型</th><th>移除</th></tr>";
        for (var i=1; i<urls.length; i++){
             if (urls[i]!=""){
                 li += "<tr>";
                 /*  第一列:图片 */
                 li += "<td>";
                 /* <c:if test="${type eq 'thumb' || type eq 'images'}"> */
                 if('${type}' == 'thumb' || '${type}'=='images'){
                     li += "<a href=\""+urls[i]+"\" url=\""+urls[i]+"\" class=\"fancybox\" rel=\"gallery\"><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:60px;max-height:60px;_height:200px;border:0;padding:3px;\"></a>";
                 }
                 
                 /* </c:if><c:if test="${type ne 'thumb' && type ne 'images'}"> */
                 if('${type}'!='thumb' && '${type}'!='images'){
                     li += "<a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";
                 }
                 /* </c:if> */
                 li += "</td>";
                 
                 /* 第二列：渠道 */
                 li += "<td>"+channelStr+"</td>";
                 
                 /* 第三列，第四列：备注，类型 */
                 li += "<td><input type='text' name='picturesNotes' style='width:100px;' value=''/></td>";
                 li += "<td><select name='picturesType' style='width:100px;'>";
                 li += "<option value='1'>缩略图</option><option value='2'>详情图</option>";
                 li += "</select></td>";
                 
                 /* 第五列：移除 */
                 li += "<td>&nbsp;&nbsp;<a href=\"javascript:\" attr-url=\""+urls[i]+"\" onclick=\"${input}Del(this);\">×</a></td>";
                 li += "</tr>";
             }
        }
        li += "</table>";
        $("#${input}Preview").html(li);
        /* Add 马亮亮 Mall Add date:2015-06-03 end */
    }
	
	function ${input}PreviewLoading(){
		//删除重复
		var url ="";
		var li, urls = $("#${input}").val();
		if(urls==null || ""==urls){
			return;
		}
		urls = $("#${input}").val().split("|");
		for (var i=0; i<urls.length; i++){
			if(url.indexOf(urls[i] + "|")<0) {
				url=url+urls[i]+"|";
			}
		}
		if(url.length>0) {
			url = url.substring(0,url.length-1);
		}
		$("#${input}").val(url);
		urls=url.split("|");
		$("#${input}Preview").children().remove();
		
		/* Add 马亮亮 Mall Add date:2015-06-03 begin 增加一个N行5列的表格 */
		li = "<table style='text-align:center;' class='table table-striped table-bordered table-condensed'><tr><th>图片</th><th>渠道</th><th>备注</th><th>类型</th><th>移除</th></tr>";
		for (var i=1; i<urls.length; i++){
	         if (urls[i]!=""){
                 li += "<tr>";
	        	 /*  第一列:图片 */
	             li += "<td>";
	             /* <c:if test="${type eq 'thumb' || type eq 'images'}"> */
	             if('${type}' == 'thumb' || '${type}'=='images'){
	            	 li += "<a href=\""+urls[i]+"\" url=\""+urls[i]+"\" class=\"fancybox\" rel=\"gallery\"><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:60px;max-height:60px;_height:200px;border:0;padding:3px;\"></a>";
	             }
	             
	             /* </c:if><c:if test="${type ne 'thumb' && type ne 'images'}"> */
	             if('${type}'!='thumb' && '${type}'!='images'){
	            	 li += "<a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";
	             }
	             /* </c:if> */
	             li += "</td>";
	             
	             /* 第二列：渠道 */
	             li += "<td><select class='input-medium' >";
	                var url = "${ctx}/mkt/groupCoupon/channelinfo";
	                $.ajax({
	                    type: "POST",
	                    async: false,
	                    url: url,
	                    dataType: "json",
	                    success: function(channels) {
	                        for(var c = 0;c<channels.length;c++){
	                            if(channels[c].value==pictureJson[i-1]['channelId']){
	                                li+="<option selected='selected' value='"+channels[c].value+"'>"+channels[c].label+"</option>";
	                            }else{
	                                li+="<option value='"+channels[c].value+"'>"+channels[c].label+"</option>";
	                            }
	                        }
	                    }
	                });
	             li += "</select></td>";
	             
	             /* 第三列，第四列：备注，类型 */
	             li += "<td><input type='text' name='picturesNotes' style='width:100px;' value='"+pictureJson[i-1]['picturesNotes']+"'/></td>";
	             
	             li += "<td><select name='picturesType' style='width:100px;'>";
	             if(null!=pictureJson[i-1] && pictureJson[i-1]['picturesType']=='1'){
	            	 li += "<option value='1' selected='selected'>缩略图</option>";
	             }else{
	            	 li += "<option value='1'>缩略图</option>";
	             }
                 if(null!=pictureJson[i-1] && pictureJson[i-1]['picturesType']=='2'){
                	 li += "<option value='2' selected='selected'>详情图</option>";
                 }else{
                	 li += "<option value='2'>详情图</option>";
                 }
                 li += "</select></td>";
	             
	             /* 第五列：移除 */
	             li += "<td>&nbsp;&nbsp;<a href=\"javascript:\" attr-url=\""+urls[i]+"\" onclick=\"${input}Del(this);\">×</a></td>";
	             li += "</tr>";
	         }
		}
		li += "</table>";
		$("#${input}Preview").html(li);
		/* Add 马亮亮 Mall Add date:2015-06-03 end */
	}
	${input}PreviewLoading();/* 编辑时第一次会载 */
</script>