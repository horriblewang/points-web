var url="blgroup-osp-site/a";
function selectStore() {
    url=arguments[0];
	var index=arguments[1];
	var width=arguments[2];
	var height=arguments[3];
	var storeType=arguments[4];
	top.$.jBox.open("iframe:"+url+"/site/store/selstore?type="+storeType,"选择门店",width,height, 
	{
		 submit : function(v, h, f) 
		 {
			var rgArray = h.find("iframe")[index].contentWindow.document.getElementsByName("radioGroup");
			
			for ( var i = 0; i < rgArray.length; i++) 
			{
				var flag = rgArray[i].checked;
				if (flag) {
					var rgv = rgArray[i].value;
					//$("#storeId").val(rgv.split("#")[0]);
					//$("#storeName").val(rgv.split("#")[1]);
					//$("#storeIdList").val($("#storeIdList").val()+","+rgv.split("#")[0]);
					
					if(!storeMap.has(escape(rgv.split("#")[1]))){
						$("#storeTags").addTag(rgv.split("#")[1]);
						storeMap.set(escape(rgv.split("#")[1]),rgv.split("#")[0]);
					}
					
				
				}
			}
		},
		loaded : function(h) 
		{
			$(".jbox-content", top.document).css("overflow-y", "hidden");
		}
	});
}
