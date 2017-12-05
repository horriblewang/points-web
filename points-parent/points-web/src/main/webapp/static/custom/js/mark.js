$.fn.extend({
    tabControl:function(options){
        var defOpt = {initTabCount: 1, maxTabCount: 100, tabMaxLen: 10, tabW: 150, tabH: 15, tipTOffset: 5, tipLOffset: 0 };
        var opts = $.extend(defOpt, options);
        var checkReg = /[^A-Za-z0-9_\u4E00-\u9FA5]+/gi; //匹配非法字符
        var initTab = function (obj) 
        {
            var textHtml = "<input class='tabinput' name='tabinput' style='width:" + opts.tabW + "px;height:" + opts.tabH + "px;' type='text'/>";
            obj.append(textHtml);
            $("input[type='text'][name='tabinput']:last", obj).bind("keydown", function (event) 
            {
                if(event.keyCode == 13 || event.keyCode == 9) 
                {
                    event.preventDefault(); 
                    var inputObj = $(this);
                    var value = $(this).val().replace(/\s+/gi, "");
                    if (value != "") 
                    {
                        if (value.length > opts.tabMaxLen) 
                        {
                            showMes($(this), "请输入1到" + opts.tabMaxLen + "个字符的长度！");
                            return;
                        }
                        var _match = value.match(checkReg);
                        if (!_match) 
                        {
                            compTab(obj, inputObj, value);
                            //隐藏了
                            if($("input[type='text'][name='tabinput']",obj).length < opts.maxTabCount) 
                            {
                                if (!inputObj.data("isModify"))
                                    initTab(obj);
                                else if (!$("input[type='text'][name='tabinput']",obj).is(":hidden")) 
                                {
                                    initTab(obj);
                                }
                            }
                            $("input[type='text']:last", obj).focus();
                            hideErr();
                        }
                        else {
                            showMes(inputObj, "內容不能包含非法字符「{0}」！".replace("{0}", _match.join(" ")));
                        }
                    }
                    else
                        showMes(inputObj,"內容不为空");
                }
            }).bind("focus blur", function () {hideErr();});
        }
        
        var compTab = function (obj, inputObj, value) 
        {
            inputObj.next("span").remove();
            var _span = "<span name='tab' id='radius'><b>" + value + "</b><a>×</a></span>";
            inputObj.after(_span).hide();
            inputObj.next("span").find("a").click(function() 
            {
                    inputObj.next("span").remove();
                    inputObj.remove();
                    if ($("span[name='tab']", obj).length == opts.maxTabCount - 1)initTab(obj);
            });
            /*inputObj.next("span").dblclick(function () 
            {   
            	//修改tab
                inputObj.data("isModify", true).next("span").remove();
                inputObj.show().focus();
            });*/
        }

        return this.each(function () 
        {
            var jqObj = $(this);
            for (var i = 0; i < opts.initTabCount; i++) 
            {
                initTab(jqObj);
            }
        });
        //生成tip
        function showMes(inputObj, mes) 
        {
            var _offset = inputObj.offset();
            var _mesHtml = "<div id='errormes' class='radius_shadow' style='position:absolute;left:" + (_offset.left + opts.tipLOffset) + "px;top:" + (_offset.top + opts.tabH + opts.tipTOffset) + "px;'>" + mes + "</div>";
            $("#errormes").remove();
            $("body").append(_mesHtml);
        } 
        function hideErr() {
            $("#errormes").hide();
        } 
        function showErr() {
            $("#errormes").show();
        }
    },
   
    getTabVals: function () {
        var obj = $(this);
        var values = [];
        obj.children("span[name=\"tab\"][id^=\"radius\"]").find("b").text(function (index, text) {
            var checkReg = /[^A-Za-z0-9_\u4E00-\u9FA5]+/gi; //匹配非法字符
            values.push(text.replace(checkReg,""));
        });
        return values;
    }
});
$(function(){
	$("#marktab").tabControl({maxTabCount:5,tabW:80});
});
