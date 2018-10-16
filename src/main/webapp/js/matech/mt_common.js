String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
};
var BlockDiv = function() {
	this.show = function(text) {
		var blockDiv = document.getElementById("divBlock");

		if (blockDiv) {
			blockDiv.style.display = "";
		} else {
			var div = document.createElement("div");
			document.body.appendChild(div);
			div.id = "divBlock";
			div.style.cssText = "position:absolute;width:100%;height:100%; top:0px; left:0px; z-index:1; padding:0px; margin:0px; background:#000000;filter:alpha(opacity=30); text-align:center; ";
		}
		
		if(text && text != "") {
			div.innerHTML = "<span style='margin-top:200px;'><img src='" + MATECH_SYSTEM_WEB_ROOT + "/img/loading.gif'>&nbsp;<font color='#ffffff'><strong>" + text + "</strong><font></span>";
		} else {
			div.innerHTML = "";
		}
	};

	this.hidden = function() { 
		var blockDiv = document.getElementById("divBlock");
		if (blockDiv) {
			try {
				blockDiv.style.display = "none";
				document.body.removeChild(blockDiv);
			}catch(e){}
		}
	};
	
};
//创建ajax 请求
function createRequest() {
	var request;
	  try {
	    request = new XMLHttpRequest();
	  } catch (trymicrosoft) {
	    try {
	      request = new ActiveXObject("Msxml2.XMLHTTP");
	    } catch (othermicrosoft) {
	      try {
	        request = new ActiveXObject("Microsoft.XMLHTTP");
	      } catch (failed) {
	        request = false;
	      }
	    }
	  }
	  if (!request)
	    alert("Error initializing XMLHttpRequest!");
	  
	  return request;
}
// 异步
function ajaxLoadPage(url,request,container) {
	var loading_msg='正在加载数据,请稍候...';
	var loader=createRequest();
	
	loader.open("POST",url,true);
	loader.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	loader.onreadystatechange = function(){
		if (loader.readyState==1) {
			try {
				if(container) {
					container.innerHTML = loading_msg;
				}
				
				showWaiting("100%","100%", loading_msg);
			} catch(e) {
				
			}
		}

		if (loader.readyState==4) {
			if(container) {
				container.innerHTML = loading_msg;
			} else {
				alert(loader.responseText);
			}
			
			try {
				stopWaiting();
			} catch(e) {

			}
		}
	};

	loader.send(request);
}

//异步回调
function ajaxLoadPageCallBack(url,request,callback) {
	var loader=createRequest();
	loader.open("POST",url,true);
	loader.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	loader.onreadystatechange = function(){
		if (loader.readyState==1) {
			showWaiting();
		}

		if (loader.readyState==4) {
			//alert(loader.responseText);

			if(callback) {
				try{
					if(typeof callback=="function"){
						callback();
					}else{
						eval(callback);
					}
					
				}catch(e){
					//alert(e);
				}
			}
			stopWaiting();
		}
	};

	loader.send(request);
}

//同步
function ajaxLoadPageSynch(url,request) {

	var loader=createRequest();

	loader.open("POST",url,false);
	loader.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	loader.send(request);

	return unescape(loader.responseText);
}
//异步
function ajaxExecuteAsync(url,request,success,failure) {
	var loader = createRequest();

	loader.open("POST",url,true);
	loader.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	loader.onreadystatechange = function(){
		if (loader.readyState == 4){
		    if (loader.status == 200){
		    	var result = loader.responseText;
		    	success(result);
		    }else{
		    	failure("请求无法正常返回！");
		    }
		    stopWaiting();
	 	};
	};
	loader.send(request);	
}

Ext.lib.Ajax.getConnectionObject = function() {
	var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
	function createXhrObject(transactionId) {
		var http;
		try {
			http = new XMLHttpRequest();
		} catch (e) {
			for (var i = 0; i < activeX.length; ++i) {
				try {
					http = new ActiveXObject(activeX[i]);
					break;
				} catch (e) {
				}
			}
		} finally {
			return {
				conn : http,
				tId : transactionId
			};
		}
	}

	var o;
	try {
		if (o = createXhrObject(Ext.lib.Ajax.transactionId)) {
			Ext.lib.Ajax.transactionId++;
		}
	} catch (e) {
	} finally {
		return o;
	}
};

function syncAjax(url,parameter,method){
	var conn = Ext.lib.Ajax.getConnectionObject().conn;  
	var method = method || "POST";
	conn.open(method, url, false);  
	conn.send(parameter || null);  
	return conn.responseText;
}

function syncAjax_utf8(url,parameter,method){
	var conn = Ext.lib.Ajax.getConnectionObject().conn;  
	var method = method || "POST";	
	conn.open(method, url, false);
	conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
	conn.send(parameter || null);  
	return conn.responseText;
}

// 页面显示进度
var timer;   
function initMessage(key,time) {
	// 创建一个显示消息的等待框
	var msgBar = document.createElement("DIV") ;
	msgBar.className = "" ;
	msgBar.id = "msgBarDiv" ;
	msgBar.innerHTML = "<div class=\"msg_background_div\" id=\"bgDiv\"></div><div class=\"msg_info_div\" id=\"msg_info_div\"><div class=\"msg_center_div\" id=\"msg_center_div\"><strong>提示：</strong><p>请等待...</p></div></div>" ;
	document.body.appendChild(msgBar) ;
	timer = window.setTimeout("startMessageListener('"+key+"','"+time+"')",time); 
}   

var oXmlhttp;   
function startMessageListener(key,time){
	
	if(!oXmlhttp) { 
	    try{   
	        oXmlhttp = new ActiveXObject('Msxm12.XMLHTTP');   
	    }catch(e){   
	        try{   
	            oXmlhttp = new ActiveXObject('Microsoft.XMLHTTP');   
	        }catch(e){   
	            try{   
	                oXmlhttp = new XMLHttpRequest();   
	            }catch(e){}   
	        }   
	    } 
	}
	
    oXmlhttp.open("post",MATECH_SYSTEM_WEB_ROOT + "frontProcess.do?method=getMessage&key="+key+"&random="+Math.random(),true);   
     oXmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); 
     oXmlhttp.onreadystatechange = function(){
        if(oXmlhttp.readyState == 4){   
            if(oXmlhttp.status == 200){
            var msgCenter = document.getElementById("msg_center_div") ;
            var temp = oXmlhttp.responseText.indexOf("end");
            if (  temp > -1 ){
       			var msgBarDiv = document.getElementById("msgBarDiv");
       			if(msgBarDiv) {
       				msgBarDiv.style.display = "none" ;
       			}    			   
            	window.clearTimeout(timer);   
            }else{
            	 msgCenter.innerHTML = ""; 
           		 msgCenter.innerHTML = "<strong>提示：</strong><p>"+oXmlhttp.responseText+"</p>";
            	timer = window.setTimeout("startMessageListener('"+key+"')",time);   
            }   
            }   
        }   
    }
    oXmlhttp.send(null);   
}

function showWaiting(hight,wight,msg){
	  var ShowDialog=1;
		if(msg==null||msg=="") {
			msg = "处理中，请稍等……";
			ShowDialog=0;
		}
	  var obj=document.getElementById("waiting");
	  if(!obj){
	    var oBody = document.body;
	  	oBody.insertAdjacentHTML("beforeEnd", "<div id='waiting' onselectstart='return false' ></div>");
	    obj=document.getElementById("waiting");
	  }

	  if(hight==null||hight==""){
	    hight="100%";
	  }
	  if(wight==null||wight==""){
	    wight="100%";
	  }
	  
	   var strTalk="";
	  if (ShowDialog==0){
	  	strTalk="<div id=bxDlg_bg1 oncontextmenu='return false' onselectstart='return false' style=\"position:absolute; width:100%;height:100%; top:expression(this.offsetParent.scrollTop); z-index:9999; padding:10px; background:#ffffff;filter:alpha(opacity=50); text-align:center;\"> </div>"
	  			+ "<div style=\"position:absolute;width:230px;height:60px; z-index:2;left:expression((document.body.clientWidth-200)/2);top:expression(this.offsetParent.scrollTop + 130); border:1px solid #666666; padding:20px 40px 20px 40px; background:#E4E4E4; \"> "
	    		// + " <img src='/AuditSystem/images/indicator.gif' />"
	    		+ "<img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/images/loading.gif\">"
	    		+ msg + "</div>";
	  }else{
		  strTalk="<span id=bxDlg_bg align=center oncontextmenu='return false'"
		    +" onselectstart='return false' style='width:"+wight+";height:"+hight+";position:absolute;left:0;top:0'>"
		    +"<div id=bxDlg_bg1 style=height:100%;background:white;filter:alpha(opacity=50)> </div></span>"
		    +"<span  style='background:#E4E4E4;POSITION:absolute;padding:20px 40px 20px 40px;left:150.5;top:164.5;"
		    +" width:400px; height:200px;  border:1px solid #666666;'>"
		    + msg + "</span>";
	  }
	  obj.innerHTML=strTalk;
	  obj.style.display = "" ;
}

function stopWaiting(){
	var obj =  document.getElementById("waiting") ;
	if(obj) {
	    obj.innerHTML="";
	    obj.style.display = "none" ;
    }
}
	
//-----------------------------------
//把表单内的input拼成url字符串返回
//-----------------------------------
function formToRequestString(form_obj) {
	var query_string='';
	var and='';
	// alert(form_obj.length);
	for (var i=0;i<form_obj.length ;i++ ) {
		e=form_obj[i];
		if ((e.tagName=='INPUT' || e.tagName=='SELECT' || e.tagName=='TEXTAREA') && e.name!='') {
			if (e.type=='select-one') {
				element_value=e.options[e.selectedIndex].value;
			} else if (e.type=='checkbox' || e.type=='radio') {
				if (e.checked==false) {
					// break;
					continue;
				}
				element_value=e.value;
			} else {
				element_value=e.value;
			}
			query_string+=and+e.name+'='+element_value.replace(/\&/g,"%26");
			and="&";
		}

	}
	return query_string;
}
// 表单提交post到另外一个新的标签页
function tabSubmit(form,url,tabTitle) {
	var randStr = Math.random();
      		
	var newTab = mainTab.add({    
		title:tabTitle,    
		closable:true,  // 通过html载入目标页
		html:'<iframe name="newTab_' + randStr + '" scrolling="auto" frameborder="0" width="100%" height="100%" src=""></iframe>'   
	}); 
	
	mainTab.setActiveTab(newTab);
	
	form.action = url;
	form.target = "newTab_" + randStr;
	form.submit();
}
// 检查目录名合法性
function checkFileName(strFile){
	var reg= new RegExp("/^[^/\\:\*\?,\",<>\|]+$/ig"); 

	if(!reg.test(strFile)){
		return " ";
	}else{
		return strFile;
	}
}

// 关闭标签页的方法
var closeTab = function(tab,callBack) {
	if(tab && tab.id == "mainFrameTab") {
		tab.remove(tab.getActiveTab()); 
		if(callBack){
			if(typeof callBack== "function"){
				callBack();
			}else{
				eval(callBack);
			}
		}
	}else {
		window.close();
	}
};

function openFullWindow(url,target,oldUrl, localUrl) {
	var x = window.open(url,target,'top=0,left=0,width=' + (window.screen.availWidth-8) + ',height=' + (window.screen.availHeight-50) + ',resizable=no,menubar=no,toolbar=no,scrollbars=yes,status=no,location=no');
	try {
		if(!x) {
			alert('对不起,系统的弹出窗口给您的浏览器阻止了\n请【关闭弹窗口阻止程序】或【点击】浏览器上方黄色提示框,选择：总是允许来自此站点的弹出窗口'); 
			if(oldUrl || oldUrl != '') {
				window.location = oldUrl;
			}
			
		} else {
			window.location = localUrl;
		}
	} catch(e) {
		// alert(e);
	}
}
// -----------------------------------
// 重置标签里面的所有文本框、复选框、单选框等
// -----------------------------------
function reset(objId) {
	var obj = document.getElementById(objId);
	
	for (i = 0; i < obj.length; i++ ) {
		e = obj[i];
		if ((e.tagName=='INPUT' || e.tagName=='SELECT' || e.tagName=='TEXTAREA') && e.name!='') {
		
			if (e.type=='text') {
				e.value = "";
			}else if (e.type=='select-one') {
				e.value = "";
			} else if (e.type=='checkbox' || e.type=='radio') {
				e.checked = false;
			} else {
				try{
					Ext.getCmp(e.id).clear();
				} catch(e) {
					e.value = "";
				}
			}
		}
	}
}
// 格式化数字
function formatDecimal(x,maxLength) {
   var f_x = parseFloat(x);
   if (isNaN(f_x)) {
      return x;
   }
   var f_x = Math.round(x*100)/100;
   var s_x = f_x.toString();
   var pos_decimal = s_x.indexOf('.');
   if (pos_decimal < 0) {
      pos_decimal = s_x.length;
      s_x += '.';
   }
   while (s_x.length <= pos_decimal + maxLength) {
      s_x += '0';
   }
   return s_x;
}

// 截取字符最大长度
function maxString(str) {
	if(str.length > 25) {
		str = str.substring(0,22) + "...";
	} 
	return str;
}
//调整gird框无法适应浏览器resize
//针对将grid框放到TabPanel中的情况
//_fromObj:grid框所属容器，该容器会随着浏览器变动而自动调整高度与长度
//_toObj:为需要根据_fromObj进行手工调整的gird的ID字符串，如：gridId_myDealList,gridId_myApplyList
//_adjSize[长度，高度]:需要减去的长度与高度，微调使用
//添加日期：2012-3-16
function resizeGridPanel(_fromObj,_toObj,_adjSize){
	var _resizeInterval;//计时器
	//监听浏览器变动
	Ext.EventManager.onWindowResize (function(){
		_resizeInterval=setInterval(GridPanelResize,500);
	});
	//调整页面gridpanel长度和宽度
	function GridPanelResize(){
		var realWidth=Ext.getCmp(_fromObj).getWidth();
		var realHeight=Ext.getCmp(_fromObj).getHeight();
		var gridPanels=_toObj.split(",");
		for(var i=0;i<gridPanels.length;i++){			
			Ext.getCmp(gridPanels[i]).setWidth(realWidth-_adjSize[i][0]);
			Ext.getCmp(gridPanels[i]).setHeight(realHeight-_adjSize[i][1]);
		}
		clearInterval(_resizeInterval);
	}	
}

//针对单个grid放到页面的情况
function resizeSingleGridPanel(_toObj,_adjWidth,_adjHeigh){

	var _resizeInterval;//计时器
	//监听浏览器变动
	Ext.EventManager.onWindowResize (function(){
		_resizeInterval=setInterval(GridPanelResize,500);
	});
	//调整页面gridpanel长度和宽度
	function GridPanelResize(){

		var realWidth=Ext.getBody().getWidth()-_adjWidth;
		var realHeight=Ext.getBody().getHeight()-_adjHeigh;
		
		Ext.getCmp(_toObj).setWidth(realWidth);
		Ext.getCmp(_toObj).setHeight(realHeight);
		
		clearInterval(_resizeInterval);
	}	
}

//阻止input按钮在disabled和readOnly时按backspace返回前一个页面
Ext.EventManager.on(Ext.getBody(),"keydown",function(e, t) {   
    if (e.getKey() == e.BACKSPACE &&(t.disabled || t.readOnly)) {   
        e.stopEvent();   
    }
});

//判断是否为IE浏览器
function isIE(){
	 var Ka=navigator.userAgent.toLowerCase();
	 var rt=Ka.indexOf("opera")!=-1; 
	 var r=Ka.indexOf("msie")!=-1&&(document.all&&!rt);return r;
		
}
//***********************************************************************************
//判断JS函数是否存在
function funExists(funName){ 
	try{  
		if(typeof eval(funName)=="undefined"){
			return false;
		} 
		if(typeof eval(funName)=="function"){
			return true;
		}
	} catch(e){
		return false;
	}
}

//js 小写人民币转化为大写人民币
function RMBToCapital(num) { //转成人民币大写金额形式
    var str1 = '零壹贰叁肆伍陆柒捌玖'; //0-9所对应的汉字
    var str2 = '万仟佰拾亿仟佰拾万仟佰拾元角分'; //数字位所对应的汉字
    var str3; //从原num值中取出的值
    var str4; //数字的字符串形式
    var str5 = ''; //人民币大写金额形式
    var i; //循环变量
    var j; //num的值乘以100的字符串长度
    var ch1; //数字的汉语读法
    var ch2; //数字位的汉字读法
    var nzero = 0; //用来计算连续的零值是几个
    num = num+"" ;
    num = num.replace(new RegExp(",","gm"),""); //去掉,号
    num = parseFloat(num) ;
    num = Math.abs(num).toFixed(2); //将num取绝对值并四舍五入取2位小数
    str4 = (num * 100).toFixed(0).toString(); //将num乘100并转换成字符串形式
    j = str4.length; //找出最高位
    if (j > 15) {
        return '溢出';
    }
    str2 = str2.substr(15 - j); //取出对应位数的str2的值。如：200.55,j为5所以str2=佰拾元角分
    //循环取出每一位需要转换的值
    for (i = 0; i < j; i++) {
        str3 = str4.substr(i, 1); //取出需转换的某一位的值
        if (i != (j - 3) && i != (j - 7) && i != (j - 11) && i != (j - 15)) { //当所取位数不为元、万、亿、万亿上的数字时
            if (str3 == '0') {
                ch1 = '';
                ch2 = '';
                nzero = nzero + 1;
            }
            else {
                if (str3 != '0' && nzero != 0) {
                    ch1 = '零' + str1.substr(str3 * 1, 1);
                    ch2 = str2.substr(i, 1);
                    nzero = 0;
                }
                else {
                    ch1 = str1.substr(str3 * 1, 1);
                    ch2 = str2.substr(i, 1);
                    nzero = 0;
                }
            }
        }
        else { //该位是万亿，亿，万，元位等关键位
            if (str3 != '0' && nzero != 0) {
                ch1 = "零" + str1.substr(str3 * 1, 1);
                ch2 = str2.substr(i, 1);
                nzero = 0;
            }
            else {
                if (str3 != '0' && nzero == 0) {
                    ch1 = str1.substr(str3 * 1, 1);
                    ch2 = str2.substr(i, 1);
                    nzero = 0;
                }
                else {
                    if (str3 == '0' && nzero >= 3) {
                        ch1 = '';
                        ch2 = '';
                        nzero = nzero + 1;
                    }
                    else {
                        if (j >= 11) {
                            ch1 = '';
                            nzero = nzero + 1;
                        }
                        else {
                            ch1 = '';
                            ch2 = str2.substr(i, 1);
                            nzero = nzero + 1;
                        }
                    }
                }
            }
        }
        if (i == (j - 11) || i == (j - 3)) { //如果该位是亿位或元位，则必须写上
            ch2 = str2.substr(i, 1);
        }
        str5 = str5 + ch1 + ch2;

        if (i == j - 1 && str3 == '0') { //最后一位（分）为0时，加上"整"
            str5 = str5 + '整';
        }
    }
    if (num == 0) {
        str5 = '零元整';
    }
    return str5;
}

//把2012改成贰零壹贰，转为票据打印使用
function DateToCapital(rq){
   if (rq){
   		rq=replaceAll(rq,'0','零');
   		rq=replaceAll(rq,'1','壹');
		rq=replaceAll(rq,'2','贰');
   		rq=replaceAll(rq,'3','叁');
   		rq=replaceAll(rq,'4','肆');
   		rq=replaceAll(rq,'5','伍');
   		rq=replaceAll(rq,'6','陆');
   		rq=replaceAll(rq,'7','柒');
   		rq=replaceAll(rq,'8','捌');
   		rq=replaceAll(rq,'9','玖');
   		return rq;
   }else{
   		return '';
   } 
}


function setObjDisabled(name){
	var oElem=document.getElementById(name);
		var sTag=oElem.tagName.toUpperCase();
		switch(sTag)
		{
		case	"BUTTON":
			oElem.disabled=true;
			break;
		case	"SELECT":
		case	"TEXTAREA":
			oElem.readOnly=true;
			break;
		case	"INPUT":
			{
			var sType=oElem.type.toUpperCase();

			if(sType=="TEXT")oElem.readOnly=true;
			if(sType=="BUTTON"||sType=="IMAGE")oElem.disabled=true;
			if(sType=="CHECKBOX")oElem.disabled=true;
			if(sType=="RADIO")oElem.disabled=true;
			}
			break;
		default:
			oElem.disabled=true;
			break;
		}
	//set style
	oElem.style.backgroundColor="#eeeeee";
}

function setObjEnabled(name){
	var oElem=document.getElementById(name);
	var sTag=oElem.tagName.toUpperCase();
	switch(sTag)
	{
		case	"BUTTON":
			oElem.disabled=false;
			break;
		case	"SELECT":
		case	"TEXTAREA":
			oElem.readOnly=false;
			break;
		case	"INPUT":
			{
			var sType=oElem.type.toUpperCase();

			if(sType=="TEXT")oElem.readOnly=false;
			if(sType=="BUTTON"||sType=="IMAGE")oElem.disabled=false;
			if(sType=="CHECKBOX")oElem.disabled=false;
			if(sType=="RADIO")oElem.disabled=false;
			}
			break;
		default:
			oElem.disabled=false;
			break;
		}
	//set style
	oElem.style.backgroundColor="#FFFFFF";
}

//替换所有字符
function replaceAll(str,oldStr,newStr) {
	return str.replace(new RegExp(oldStr,"gm"),newStr); 
}

Array.prototype.contains = function (element) { 
	for (var i = 0; i < this.length; i++) { 
		if (this[i] == element) { 
			return true; 
		} 
	} 
	return false; 
}


//模板打印
function mt_PrintByTemplate(names,values,templateid,tabname){

	var url=MATECH_SYSTEM_WEB_ROOT+'/template/printtemplate.jsp?templateid='+templateid;
	
	mt_PrintByTemplate1(url,names,values,templateid,tabname);
}


function mt_PrintByTemplate1(url,names,values,templateid,tabname){

	var tab = parent.parent.mainTab ;

     //先用URL提交； 
     if(tab){
          var random =  Math.random();
          var n = tab.add({    
          title:tabname,    
          closable:true,  //通过html载入目标页
          id:random ,
          html:'<iframe id="frame' + random + '" name="frame' + random + '" scrolling="no" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'   
          }); 

          tab.setActiveTab(n);
		
           //再用FORM提交
          printform.names.value=names;
          printform.values.value=values;
          printform.tabname.value=tabname;
          printform.action=url;
          printform.target="frame"+random ;
          
          
          printform.submit();
          stopWaiting();
	}else {
		window.open(url);
	}
}


function templatePrint(url){

	var tab = parent.parent.mainTab ;

     //先用URL提交； 
     if(tab){
          var random =  Math.random();
          var n = tab.add({    
          title:"打印",    
          closable:true,  //通过html载入目标页
          id:random ,
          html:'<iframe id="frame' + random + '" name="frame' + random + '" scrolling="no" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'   
          }); 

          tab.setActiveTab(n);
		
	}else {
		window.open(url);
	}
}


function changeTwoDecimal(x) {
	var f_x = parseFloat(x);
	if (isNaN(f_x)) {
		return x;   
	}
	f_x = Math.round(f_x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
	return s_x;
}

function formatMoney(number) {
	var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;  
	number=number.replace(re,"$1,");  
	return number ;
}



function dayToBig(day){
	var r2 = parseInt(day,10);
	var day;

	if(r2==1) day="零壹";
	if(r2==2) day="零贰";
	if(r2==3) day="零叁";
	if(r2==4) day="零肆";
	if(r2==5) day="零伍";
	if(r2==6) day="零陆";
	if(r2==7) day="零柒";
	if(r2==8) day="零捌";
	if(r2==9) day="零玖";

	if(r2==11) day="壹拾壹";
	if(r2==12) day="壹拾贰";
	if(r2==13) day="壹拾叁";
	if(r2==14) day="壹拾肆";
	if(r2==15) day="壹拾伍";
	if(r2==16) day="壹拾陆";
	if(r2==17) day="壹拾柒";
	if(r2==18) day="壹拾捌";
	if(r2==19) day="壹拾玖";
	if(r2==10) day="零壹拾";

	if(r2==21) day="贰拾壹";
	if(r2==22) day="贰拾贰";
	if(r2==23) day="贰拾叁";
	if(r2==24) day="贰拾肆";
	if(r2==25) day="贰拾伍";
	if(r2==26) day="贰拾陆";
	if(r2==27) day="贰拾柒";
	if(r2==28) day="贰拾捌";
	if(r2==29) day="贰拾玖";
	if(r2==20) day="零贰拾";
   
   
	if(r2==31) day="叁拾壹";
	if(r2==30) day="零叁拾";
    return day;

}

function monthToBig(month){//月转化大写
	
	var mon;
	if(month==1 || month==01) mon="零壹";
	if(month==2 || month==02) mon="零贰";
	if(month==3 || month==03) mon="零叁";
	if(month==4 || month==04) mon="零肆";
	if(month==5 || month==05) mon="零伍";
	if(month==6 || month==06) mon="零陆";
	if(month==7 || month==07) mon="零柒";
	if(month==8 || month==08) mon="零捌";
	if(month==9 || month==09) mon="零玖";
	if(month==10) mon="零壹拾";
	if(month==11) mon="壹拾壹";
	if(month==12) mon="壹拾贰";
    return  mon;
}

function changeDateYear(year)//年转换大写
{

    var year1 = yearToBig(year.substring(0,1));
    var year2 = yearToBig(year.substring(1,2));
    var year3 = yearToBig(year.substring(2,3));
    var year4 = yearToBig(year.substring(3,4));
    return year1+year2+year3+year4+ " ";
               
}

function yearToBig(year){
    jiaow=year;
    if(jiaow==1) year="壹"
    if(jiaow==2) year="贰"
    if(jiaow==3) year="叁"
    if(jiaow==4) year="肆"
    if(jiaow==5) year="伍"
    if(jiaow==6) year="陆"
    if(jiaow==7) year="柒"
    if(jiaow==8) year="捌"
    if(jiaow==9) year="玖"
    if(jiaow==0) year="零"
    return year;               
}

/* decimals:精度默认2
decimal_sep:  小数点标记符默认 .
thousands_sep: 千分位标记符 默认,
*/
Number.prototype.toMoney = function(decimals, decimal_sep, thousands_sep)
{ 
   var n = this,
   c = isNaN(decimals) ? 2 : Math.abs(decimals), 
   d = decimal_sep || '.', 
   t = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep, 
  sign = (n < 0) ? '-' : '',
 i = parseInt(n = Math.abs(n).toFixed(c)) + '', 
  j = ((j = i.length) > 3) ? j % 3 : 0; 
   return sign + (j ? i.substr(0, j) + t : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : ''); 
}

//得到地址栏名字
function getParameterByName(name)
{
  name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
  var regexS = "[\\?&]" + name + "=([^&#]*)";
  var regex = new RegExp(regexS);
  var results = regex.exec(window.location.search);
  if(results == null)
    return "";
  else
    return decodeURIComponent(results[1].replace(/\+/g, " "));
}

//从千分位到 数字的转换
function toNumberFromMoney(money){
	if(typeof money =="undefined"){
			return "";
		}
	return Number(replaceAll(money,",",""));
}
//验证子表是否有数据
function validate_sub_table_has_data(sub_table_name){
	var message="";
	var has_data=true;
   	if(sub_table_name!=""){
  			var sub_table_name_array =sub_table_name.split(",");
  			for(var c=0;c<sub_table_name_array.length;++c){
  				if((funExists("validate_sub_table")&&!validate_sub_table(sub_table_name_array[c]))){
  					continue;
  				}
  				var sub_table_id_ext=Ext.get(sub_table_name_array[c]);
	  			if(sub_table_id_ext){
	  				if(sub_table_id_ext.child("tr")==null){
	  					var caption=sub_table_id_ext.parent("table").child("caption"); //找到表头
	  					if(caption){
	  						message+="【"+caption.dom.innerHTML+"】";
	  					}
	  					message+="子表至少需要有一条数据 \n";
	  					has_data=false;
	  				}
	  			}
  			}
   	}
   	if(!has_data){
   		alert(message);
   	}
	return has_data;
}
//textarea 弹窗编辑
function to_window_textarea_edit(target_id,title){
	var target_document=document.getElementById(target_id);
	if(!target_document){
		alert(target_id+"不存在");
		return;
	}
	var view=target_document.readOnly;  //查看或者被设置为只读
	
	var default_title=(target_document.ext_id||target_document.id||"")+(view?"查看":"编辑");
	title=title|| default_title;
	var	edit_window_$=
		new Ext.Window({
				title: title,
				width: 800,
				height:400,
				maximizable : true,
			    items :{
						xtype:"textarea",
						value :target_document.value,
						id :"edit_window_$_textarea",
						readOnly:view,
						maxLength :target_document.maxLength||1000,
						listeners :{
									invalid:function(this_text_area,msg){
										edit_window_$.setTitle(title+'   （<span style="color:red">'+msg+'</span>）');
									}
									,
									valid:function(this_text_area){
										edit_window_$.setTitle(title);
									}
								}
						},
		        modal:true,
		        layout:'fit',
			    buttons:[{
		            text:'保存',
		            icon:'img/confirm.gif',
		          	handler:function() {
		          		var edit_window_$_textarea_cmp=Ext.getCmp("edit_window_$_textarea");
		          		if(!edit_window_$_textarea_cmp.validate()){
		          			return;
		          		}
		          		mt_form_setFieldValue(target_id,edit_window_$_textarea_cmp.getValue()) ;
		          		edit_window_$.close();
					}
		        },{
		            text:'关闭',
		            icon:'img/close.gif',
		            handler:function(){
		            	edit_window_$.close();
		            }
		        }]
		    });
	    if(view){
	    	edit_window_$.getFooterToolbar().items.itemAt(0).hide(); 
	    }
	    edit_window_$.show();
}
//验证textarea最大值
function validateTextareaMaxLength(){
	var message="";
	var is_success=true;
	Ext.each(Ext.query("textarea"),function(item){
		var itemElement=Ext.get(item);
		var maxLength=itemElement.getAttribute("maxLength");
		if(maxLength){
		   var length=item.value.length;
		   if(length>maxLength){
		   		message+="【"+item.id+"】最大长度【"+maxLength+"】,实际长度【"+length+"】\n";
		   		itemElement.addClass("validation-failed");
		   		is_success=false;
		   }else{
		   		itemElement.removeClass("validation-failed");
		   }
		}
	});
	if(!is_success){
		alert(message);
	}
	return is_success;
}
//js获取url参数值之正则分析法
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//js获取url参数值之方法二
function GetRequest() {
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}