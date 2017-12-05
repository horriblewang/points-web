<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、flash、thumb"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<table id="${input}Preview"  class=" table table-striped table-bordered table-condensed"></table><a href="javascript:" onclick="${input}FinderOpen();" class="btn">${selectMultiple?'添加':'选择'}</a>&nbsp;<a href="javascript:" onclick="${input}DelAll();" class="btn">清除</a>
<script type="text/javascript">
	function ${input}FinderOpen(){//<c:if test="${type eq 'thumb'}"><c:set var="ctype" value="images"/></c:if><c:if test="${type ne 'thumb'}"><c:set var="ctype" value="${type}"/></c:if>	
	var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
		var url = "${ctxStatic}/ckfinder/ckfinder.html?type=${ctype}&start=${ctype}:${uploadPath}/"+year+"/"+month+
			"/&action=js&func=${input}SelectAction&thumbFunc=${input}ThumbSelectAction&cb=${input}Callback&dts=${type eq 'thumb'?'1':'0'}&sm=${selectMultiple?1:0}";
		windowOpen(url,"文件管理",1000,700);
		//top.$.jBox("iframe:"+url+"&pwMf=1", {title: "文件管理", width: 1000, height: 500, buttons:{'关闭': true}});
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
		
		
		var picval = "{'channelId':'','picturesUrl':'"+url+"','picturesNotes':'','picturesId':'','picturesType':''}";
		
		var pictures = eval("("+ $("#${input}Obj").val()  +")");
		pictures.push(eval("("+ picval +")"));
		var str = JSON.stringify(pictures); 
		$("#${input}Obj").val(str);
		${input}Preview();
		//top.$.jBox.close();
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
		//top.$.jBox.close();
	}
	function ${input}Callback(api){
		ckfinderAPI = api;
	}
	function ${input}Del(index){
		
		var url = $("#${input}imgurl"+index).attr("url");
		$("#${input}").val($("#${input}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
		
		${input}Preview();
	}
	function ${input}DelAll(){
		$("#${input}").val("");
		${input}Preview();
	}
	function ${input}Channel(obj,index){
		var channel = $(obj).val();
		var picStr = $("#${input}picture"+index).val();
		var picJson = eval("("+picStr+")");
		var picval = "{'channelId':'"+channel+"','picturesUrl':'"+picJson.picturesUrl+"','picturesNotes':'"+picJson.picturesNotes+"','picturesId':'"+picJson.picturesId+"','picturesType':'"+picJson.picturesType+"'}";
		$("#${input}picture"+index).val(picval);
	}
	function ${input}Notes(obj,index){
		var notes = $(obj).val();
		var picStr = $("#${input}picture"+index).val();
		var picJson = eval("("+picStr+")");
		var picval = "{'channelId':'"+picJson.channelId+"','picturesUrl':'"+picJson.picturesUrl+"','picturesNotes':'"+notes+"','picturesId':'"+picJson.picturesId+"','picturesType':'"+picJson.picturesType+"'}";
		$("#${input}picture"+index).val(picval);
	}
	
	function ${input}Preview(){
		
		//删除重复
		var url ="";
		var li, urls = $("#${input}").val().split("|");
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
		
		var pictures =  eval("("+ $("#${input}Obj").val()  +")");
		//alert($("#${input}Obj").val());
		var newpictures = [];
		for(var j = 0 ; j < pictures.length;j++){
			for(var k = 0; k < urls.length; k++ ){
				if(pictures[j].picturesUrl== urls[k]){
					newpictures.push(pictures[j]);
				}
			}
		}
	
		
		$("#${input}Preview").children().remove();
		li = "<tr><th>图片</th><th>渠道</th><th>备注</th><th>类型</th><th>移除</th></tr>";
		for (var i=0; i<newpictures.length; i++){
			if (newpictures[i].picturesUrl!=""){//<c:if test="${type eq 'thumb' || type eq 'images'}">
				li += "<tr><td><a id='${input}imgurl"+i+"' href=\""+newpictures[i].picturesUrl+"\" url=\""+newpictures[i].picturesUrl+"\" class=\"fancybox\" rel=\"gallery\"><img src=\""+newpictures[i].picturesUrl+"\" url=\""+newpictures[i].picturesUrl+"\" style=\"max-width:50px;max-height:50px;_height:50px;border:0;padding:3px;\"></a>";//</c:if><c:if test="${type ne 'thumb' && type ne 'images'}">
				li += "<td><a id='${input}imgurl"+i+"' href=\""+newpictures[i].picturesUrl+"\" url=\""+newpictures[i].picturesUrl+"\" target=\"_blank\">"+decodeURIComponent(newpictures[i].picturesUrl.substring(newpictures[i].picturesUrl.lastIndexOf("/")+1))+"</a>";//</c:if>
				li += "&nbsp;&nbsp;</td>";
				li+="<td><select class='input-medium' >";
				
				var url = "${ctx}/mkt/groupCoupon/channelinfo";
				$.ajax({
					type: "POST",
					async: false,
					url: url,
					dataType: "json",
					success: function(channels) {
						for(var c = 0;c<channels.length;c++){
							if(channels[c].value==newpictures[i].channelId){
								li+="<option select='select' value='"+channels[c].value+"'>"+channels[c].label+"</option>";
							}else{
								li+="<option value='"+channels[c].value+"'>"+channels[c].label+"</option>";
							}
						}
					}
				});
				
				
				li+="<select></td>";
				li += "<td><input type='text' class='input-medium' placeholder='请输入渠道信息' value='"+newpictures[i].channelId+"' onblur='${input}Channel(this,"+i+")'></td>";
				li += "<td><input type='text' class='input-medium' placeholder='备注' value='"+newpictures[i].picturesNotes+"' onblur='${input}Notes(this,"+i+")'></td>";
				li += "<td>&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"${input}Del("+i+");\"><i class='icon-minus share'></i></a></td>";
				var grouponPicturesjson = "{'channelId':'','picturesUrl':'"+newpictures[i].picturesUrl+"','picturesNotes':'','picturesType':'','picturesId':'"+newpictures[i].picturesId+"'}";
				li += "</tr><input type='hidden' id='${input}picture"+i+"' name='grouponPictures' value=\""+grouponPicturesjson+"\">";
				
				$("#${input}Preview").html(li);
			}
		}

	}
	${input}Preview();
</script>