/*
 *  全局公用函数
 * 该全局公用函数主要是考虑到mt_common函数中比较杂乱，故重新进行编写，将本系统中使用到的一些
 * 公用的函数进行归类，为了不与com文件函数冲突，增加命名空间global
 * 
 */
if (!this.matech) {
    this.matech = {};
}
(function () {
	matech.showObjectMsg=function(objs){
		var _str="";
		for(var obj in objs){
			_str=_str+obj+":"+objs[obj]+",";
		}
		alert(_str);
	};
	matech.ajaxLoadPage=function(url,request,callback,waitMsg) {
		var _key=new UUID().createUUID();
	   	var _resizeInterval=null;
	   	var _url="";
	   	var _request="";
	   	
	   	//加锁
	   	_url=MATECH_SYSTEM_WEB_ROOT+"/system.do?method=addSysGlobalLocked";
	   	_request="&key="+_key;
	   	var result=ajaxLoadPageSynch(_url, _request);
	   	if(result=="Y"){
	   		if(waitMsg){
		   		matech.showWaiting("100%","100%",waitMsg);
	   		}else{
		   		matech.showWaiting("100%","100%","正在更新数据，请稍后....");
	   		}
	   		_resizeInterval=setInterval(checkState,500);
	   		
	   		ajaxLoadPageCallBack(url,request,function(){
		   			removeState();
		   			matech.stopWaiting();
		   			callback();			   		
	   		});		
	   								   		 		
	   	}else{
	   		alert("无法执行加锁程序，请联系管理员！");
	   	}

    	function checkState(){
    		Ext.Ajax.request({      
    		       url:MATECH_SYSTEM_WEB_ROOT+'/system.do?method=isSysGlobalLocked',   
    		       params:{
    		    	   key:_key
    		        },   
    		        success: function(resp,opts) {  
    		            if(resp.responseText=="N"){
    		            	clearInterval(_resizeInterval);
    		            	callback();
    		            };		                    
    		        }     
    		});
    	}		
		
    	function removeState(){
    		Ext.Ajax.request({      
   		       url:MATECH_SYSTEM_WEB_ROOT+'/system.do?method=removeSysGlobalLocked',   
   		       params:{
   		    	   key:_key   
   		        } 
      		});
    	}
		
	};
	//在主界面mainTab中打开一个新的标签
	matech.openTab = function(jsonStr,iframUrl,parent) {
		var param=jsonStr;
		if(typeof jsonStr=="string"){
			 param=JSON.parse(jsonStr);
		}
		if(!parent.mainTab){
			parent=parent.parent;
		}
		if(!parent.mainTab){
			parent=parent.parent;
		}
		if(!parent.mainTab){
			alert("找不到主页面的窗口对象！");
		}
		var n = parent.mainTab.getComponent(param.id); 

		if (!n) { // 判断是否已经打开该面板
			n = parent.mainTab.add({    
				 id:param.id,    
				 title:param.title,  
				 closable:true,  // 通过html载入目标页
				 html:'<iframe name="'+param.iframName+'" id="'+param.iframName+'" scrolling="no" frameborder="0" width="100%" height="100%" src="' + iframUrl + '"></iframe>'

			});    
		}else{
			var openFrame=parent.window.frames[param.iframName];
			openFrame.location.href=iframUrl;
		} 	
		parent.mainTab.setActiveTab(n);        	
    };
	//在主界面中打开一个新的window窗口
    matech.openWin = function(jsonStr,iframUrl,parent,callback) {
    	var param=jsonStr;
    	if(typeof jsonStr=="string"){
    		param=JSON.parse(jsonStr);
    	}
    	var key="";
        if(!param.id){
        	key=new UUID().createUUID();
        	param.id=key;
        }
        key=param.id;

		if(!parent.openWin){
			parent=parent.parent;
		}
		if(!parent.openWin){
			parent=parent.parent;
		}
		if(parent.openWin){
			parent.openWin(param,iframUrl); 
		}else{
			alert("找不到对应的窗口对象！");
		}
        if(callback){
        	matech.openWinListener(key,callback);
        };
        return key;
    };
    matech.openWindow = function(_title,_Url,_width,_height,parent,callback) {
    	var param={};
    	param.id=new UUID().createUUID();
    	param.title=_title;
    	param.width=_width;
    	param.height=_height;
    	
    	matech.openWin(param,_Url,parent,callback);
    	
    };
    matech.closeWindow=function(parent){
		if(!parent.closeWin){
			parent=parent.parent;
		}
		if(!parent.closeWin){
			parent=parent.parent;
		}
		if(parent.closeWin){
			parent.closeWin(); 
		}else{
			alert("找不到对应的窗口对象！");
		}    	
    };
    //窗口关闭回调
    matech.openWinListener=function(winId,callback){
    	var _resizeInterval=null;
		Ext.Ajax.request({      
		       url:MATECH_SYSTEM_WEB_ROOT+'/system.do?method=addSysGlobalLocked',   
		       params:{
		    	   key:winId   
		        },   
		        success: function(resp,opts) {    
		        	_resizeInterval=setInterval(checkState,500);
		        },    
		        failure: function(resp,opts){
		        	alert("系统错误，无法增加监听器！");
		        }      
		});

    	function checkState(){
    		Ext.Ajax.request({      
    		       url:MATECH_SYSTEM_WEB_ROOT+'/system.do?method=isSysGlobalLocked',   
    		       params:{
    		    	   key:winId
    		        },   
    		        success: function(resp,opts) {  
    		            if(resp.responseText=="N"){
    		            	clearInterval(_resizeInterval);
    		            	callback();
    		            };		                    
    		        }     
    		});
    	}
    };
    
	//在浏览器中打开一个新的window窗口
	matech.OpenUrlByWindowOpen = function(url,target,param) {
		var targetTemp = "_blank";
		var paramTemp = "channelmode=1,resizable=yes,toolbar=no,menubar=no,titlebar=no,scrollbars=yes";

		if (target != "") {
			targetTemp = target;
		}

		if (param != "") {
			paramTemp = param;
		}
		window.open(url, targetTemp, paramTemp);  	
    };
    
   //判断JS函数是否存在
	matech.funExists=function(funName){
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
	};
    
    //显示遮蔽窗
	matech.showWaiting=function(hight,wight,msg){
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
	};

    //取消遮蔽窗
	matech.stopWaiting=function(){
		var obj =  document.getElementById("waiting") ;
		if(obj) {
		    obj.innerHTML="";
		    obj.style.display = "none" ;
	    }   		
	};
  //获取文件的扩展名
    matech.getfilenameext=function(filename){
      return filename.replace(/(.*\.)/g,'').toUpperCase();
    };
    
    //获取主机地址
    matech.getlocationhost=function (){
      return "http:\/\/"+window.location.host;
    };
    
/************树的级联选择******************************/
/** ***************** 级联选中支持开始 ******************** */
	 matech.cascadeParent=function() {
	 	var pn = this.parentNode;
	 	if (!pn || !Ext.isBoolean(this.attributes.checked))
	 		return;
	 	if (this.attributes.checked) {// 级联选中
	 		pn.getUI().toggleCheck(true);
	 	} else {// 级联未选中
	 		var b = true;
	 		Ext.each(pn.childNodes, function(n) {
	 					if (n.getUI().isChecked()) {
	 						return b = false;
	 					}
	 					return true;
	 				});
	 		if (b)
	 			pn.getUI().toggleCheck(false);
	 	}
	 	pn.cascadeParent();
	 };
	 matech.cascadeChildren=function () {
	 	var ch = this.attributes.checked;
	 	if (!Ext.isBoolean(ch))
	 		return;
	 	Ext.each(this.childNodes, function(n) {
	 				n.getUI().toggleCheck(ch);
	 				n.cascadeChildren();
	 			});
	 };
	 matech.cascadeInit=function(parent,children){
		 if(parent){
			 Ext.apply(Ext.tree.TreeNode.prototype, {
		 			cascadeParent : matech.cascadeParent
		 		});	
			 Ext.override(Ext.tree.TreeEventModel, {
		 			onCheckboxClick : Ext.tree.TreeEventModel.prototype.onCheckboxClick
		 					.createSequence(function(e, node) {
		 								node.cascadeParent();
		 							})
			 });
		 }
		 if(children){
			 Ext.apply(Ext.tree.TreeNode.prototype, {
			 			cascadeChildren : matech.cascadeChildren
			 		});
			 Ext.override(Ext.tree.TreeEventModel, {
			 			onCheckboxClick : Ext.tree.TreeEventModel.prototype.onCheckboxClick
			 					.createSequence(function(e, node) {
			 								node.cascadeChildren();
			 							})
			 });			 
		 }
	 };
    matech.cascadeFromChild=function(tree){
    	//级联父级
    	function nodeChecked(node,checked){
    	
    		if(node.parentNode.getUI().checkbox!=undefined){
    			var childs=node.parentNode.childNodes;
    				for(var i=0;i<childs.length;i++){
    					if(childs[i].attributes.checked){
    						node.parentNode.getUI().checkbox.checked=true;
    						node.parentNode.attributes.checked=true;
    					}else{
    						node.parentNode.getUI().checkbox.checked=false;
    						node.parentNode.attributes.checked=false;
    						break;
    					}
    				}
    			
    			nodeChecked(node.parentNode,checked);
    		}
    		
    	};  
    	//级联选中的操作
    	tree.on('checkchange', function(node, checked){   
    			node.expand();   
    			node.attributes.checked = checked; 
    			node.eachChild(function(child) {  
    				child.ui.toggleCheck(checked);   
    				child.attributes.checked = checked;   
    				child.fireEvent('checkchange', child, checked);  
    			}); 
    			nodeChecked(node,checked);
    		},tree);  
    };	 
   
/***Grid处理相关函数****************************************************************/
	matech.showMoney=function(content){
		if (content && content!="") {

			var d = parseFloat(content);
			s = d.toFixed(2); // -1,234,568

			if (s.trim().indexOf("-") == 0) {
				return ("<div style=\"text-align:right;color:#FF0000;\">" + s + "</div>");
			} else {
				return ("<div style=\"text-align:right;color:#0000FF;\">" + s + "</div>");
			}
		} else {
			return ("<div style=\"text-align:right; color:#0000FF;\"> - </div>");
		}
	};
	matech.showCurrency=function(num) {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    	num = "0";
	    
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    cents = num%100;
	    num = Math.floor(num/100).toString();
	    
	    if(cents<10)
	    	cents = "0" + cents;
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    	num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
	    	
		if (num.trim().indexOf("-") == 0) {
			return ("<div style=\"text-align:right;color:#FF0000;\">" + (((sign)?'':'-') + num + '.' + cents) + "</div>");
		} else {
			return ("<div style=\"text-align:right;color:#0000FF;\">" + (((sign)?'':'-') + num + '.' + cents)+ "</div>");
		}
	};
	matech.showPercent=function(content){
		if (content && content!="") {

			var d = parseFloat(content);
			s = d.toFixed(2); // -1,234,568

			if (s.trim().indexOf("-") == 0) {
				return ("<div style=\"text-align:right;color:#FF0000;\">" + s + "%</div>");
			} else {
				return ("<div style=\"text-align:right;color:#0000FF;\">" + s + "%</div>");
			}
		} else {
			return ("<div style=\"text-align:right; color:#0000FF;\"> - </div>");
		}
	};
	
	matech.mt_grid_destory=function(gridCmp,tableId){
		//状态发生了改变
		if(gridCmp.isStateDirty){
			 var col_width = "" ;
			 var col_hide = "" ;
			 var col_sort = "" ;
			 var col_seq = "" ;
			 
			 var cms = gridCmp.getColumnModel();
			 var store = gridCmp.getStore();
			 
			 for (var i = 0; i < cms.getColumnCount(); i++) {
				 var col_id = cms.getColumnId(i) ;
				 var colObj = cms.getColumnById(col_id) ;
				// alert("hidden:" + cms.isHidden(i) + " width:" + colObj.width + " id:"+colObj.id+" sort:");
				 col_width += colObj.id + ":" + colObj.width + "," ;
				 col_hide += colObj.id + ":" + cms.isHidden(i) + "," ;
				 col_seq += colObj.id + ":" + i + "," ;
			 }
			 
			 if(col_width != "") col_width = "{" + col_width.substring(0, col_width.length - 1) + "}" ;
			 if(col_hide != "") col_hide = "{" + col_hide.substring(0, col_hide.length - 1) + "}" ;
			 if(col_seq != "") col_seq = "{" + col_seq.substring(0, col_seq.length - 1) + "}" ;
			 
			 if(store.getSortState()){
				 col_sort = "{" + store.getSortState().field + ":'" + store.getSortState().direction + "'}" ;
			 }
			 
			 var url = MATECH_SYSTEM_WEB_ROOT+"/system.do?method=saveGridConfig" ;
			 var request = "&colWidth="+col_width+"&colHide="+col_hide+"&colSort="+col_sort+"&colSeq="+col_seq+"&tableId="+tableId;
			 ajaxLoadPageSynch(url,request) ;
			 
		}
	};	
	matech.addQuery=function (tableId,first,nameValue) {

		var trObj ;
		var tdObj ;
		
		var grid = Ext.getCmp("gridId_"+tableId);
		
		var columns = grid.getColumnModel().columns;
		
		var name = "" ;
		var value = "" ;
		var operator = "" ;
		if(nameValue){
			
			if(nameValue.indexOf("=") > -1) {
				operator = "=" ;
			}else if(nameValue.indexOf("!=") > -1) {
				operator = "!=" ;
			}else if(nameValue.indexOf(">") > -1) {
				operator = ">" ;
			}else if(nameValue.indexOf("<") > -1) {
				operator = "<" ;
			}else if(nameValue.indexOf(">=") > -1) {
				operator = ">=" ;
			}else if(nameValue.indexOf("<=") > -1) {
				operator = "<=" ;
			}else if(nameValue.indexOf("like") > -1) {
				operator = "like" ;
			}else if(nameValue.indexOf("not like") > -1) {
				operator = "not like" ;
			}
			var nameValueArr = nameValue.split(operator) ;
			name = nameValueArr[0].replace(new RegExp(" ","gm"),""); 
			value = nameValueArr[1].replace(new RegExp(" ","gm"),""); 
			value = value.replace(new RegExp("\\'","gm"),""); 
			value = value.replace(new RegExp("%","gm"),""); 
		}
		
		var tbody = document.getElementById("queryTBody_"+tableId);
		trObj = tbody.insertRow();
		trObj.id = "queryTr_" + tableId;
		
		//连接
		tdObj = trObj.insertCell();
		tdObj.align = "center";
		
		var display = "" ;
		if(first) {
			display = "display:none;" ;
		}
		tdObj.innerHTML = "<div class=selectDiv style=\"width:80px;"+display+"\">"
						+ "<select class=mySelect style=\"width:80px;\" name='query_logic_"+tableId+"' id='query_logic_" + tableId + "'>"
						+ "		<option value='and'>并且(and)</option>"
						+ "		<option value='or'>或者(or)</option>"
						+ "</select>"
						+ "</div>" ;
		
		//列名
		tdObj = trObj.insertCell();
		tdObj.align = "center";
		
		var columnHtml = "<div class=selectDiv style=\"width:120px;\">"
						+ "	<select class=mySelect style=\"width:120px;\" name='query_column_" + tableId + "' id='query_column_" + tableId + "'>" ;
						
		for(var i=0;i<columns.length;i++) {
			var hidden = columns[i].hidden ;
			
			if(hidden){
				continue;
			}
			
			var id = columns[i].freequery || columns[i].id||'' ;
			
			var header = columns[i].header||'' ;
			
			
			var selected = ""; 
			if(id == name) {
				selected = " selected " ;
			}
			 
			if(header != "选" && id != "numberer" && header != "trValue" && id != "chooseValue") {
				columnHtml += "<option value='" + id + "' "+selected+"> " + header + " </option>" ;
			}  
		}

		columnHtml += " </select>";
		columnHtml += " </div>";
		tdObj.innerHTML = columnHtml ;
		
		//运算符
		tdObj = trObj.insertCell();
		tdObj.align = "center";
		
		var index = Math.round(Math.random() * 10000) ;
		tdObj.innerHTML = "<div class=selectDiv style=\"width:80px;\">"
		+ "	<select class=mySelect style=\"width:80px;\" name='query_operator_" + tableId + "' id='query_operator_" + tableId + "_"+index+"'>"
		+ "		<option value='='> 等于(=) </option> "
		+ "		<option value='!='> 不等于(!=) </option> "
		+ "		<option value='>'> 大于(&gt;) </option> "
		+ "		<option value='<'> 小于(&lt;) </option> "
		+ "		<option value='>='> 大于等于(&gt;=) </option> "
		+ "		<option value='<='> 小于等于(&lt;=) </option> "
		+ "		<option value='like' selected> 包含 </option> "
		+ "		<option value='not like'> 不包含 </option> "
		+ " </select>";
		+ " </div>";
		
		if(operator != "") {
			document.getElementById("query_operator_" + tableId + "_"+index).value = operator ;
		}
		
		
		//内容
		tdObj = trObj.insertCell();
		tdObj.align = "center";

		tdObj.innerHTML = "<input type=text id='query_condition_" + tableId + "' name='query_condition_" + tableId + "' value='"+value+"'  size='30'>";
		
		//操作
		tdObj = trObj.insertCell();
		tdObj.align = "center";
		if(!first) {
			tdObj.innerHTML = "<a href='#' onclick='matech.removeQuery(this);' ><img src=" + MATECH_SYSTEM_WEB_ROOT + "/img/delete.gif></a>" ;
		}
		
	};
	matech.removeQuery=function(obj) {

		var tbody = obj.parentElement.parentElement.parentElement ;
		var trObj = obj.parentElement.parentElement ;
		if(trObj) {
			tbody.removeChild(trObj);
		}
	};
	
	matech.calculateValue=function() {
		var sTextValue = sText.value;
		try {
			sTextValue =  eval(sTextValue.replace(new RegExp(",","gm"),""));
			sValue.value = Ext.util.Format.number(sTextValue,'0,000.00') ;
		}catch(e){}
	};
	//计算重置
	matech.calculatorReset=function(tableId) {
		sText.value='';
		sValue.value='';
		Ext.getCmp("gridId_"+tableId).getSelectionModel().clearSelections();
		
		Ext.getCmp("gridId_"+tableId).getSelectionModel().selectedArea='';
	};
	//数据导出
	matech.expExcel=function(tableId) {
		
		var grid = Ext.getCmp("gridId_"+tableId);
		var columns = grid.getColumnModel().columns;
		var displayColName="";
		var colName="";
		var colWidth="";
		
		for(var i=0;i<columns.length;i++) {
			var hidden = columns[i].hidden ;
			
			if(hidden){
				continue;
			}
			if(!columns[i].dataIndex || columns[i].dataIndex==""){
				continue;
			}
			if(displayColName==""){
				displayColName=columns[i].header;
				colName=columns[i].dataIndex;
				colWidth=columns[i].width;
			}else{
				displayColName=displayColName+","+columns[i].header;
				colName=colName+","+columns[i].dataIndex;
				colWidth=colWidth+","+columns[i].width;
			}
		}
		var formId="grid_"+tableId+"_form";
		document.getElementById("gridTableId").value=tableId;		
		document.getElementById("gridDisplayColName").value=displayColName;
		document.getElementById("gridWidth").value=colWidth;
		document.getElementById("gridColName").value=colName;
		document.getElementById(formId).action=MATECH_SYSTEM_WEB_ROOT + '/common.do?method=expExcel';
		document.getElementById(formId).target="grid_"+tableId+"_iframe";
		document.getElementById(formId).submit();
	    return false;
	};
	//根据列JSON数据获取fields
	matech.getFieldsFromJson=function(columnJson){
		var fields=new Array();
		for(var i=0;i<columnJson.length;i++){
			fields.push(columnJson[i].dataIndex);
		}
		return fields;
	};
	
/****表单操作*****************************************************************************************/
	matech.setFormELAllEnabled=function(){
		$("form input").attr("readOnly","true");
		$("form select").attr("disabled","disabled");
		$("form textarea").attr("disabled","disabled");
		
		Ext.ComponentMgr.all.each(function(cmp){ 
			
			if(cmp.getXType()){
				if("|treepanel|panel|viewport|button|toolbar|".indexOf(cmp.getXType().toLowerCase())<=0){
					cmp.setDisabled("true");
				}
			}
		});  
	};
	//设置页面Ext按钮控件是否可以点击
	matech.setExtBtnShow=function(btnIndex,isEnabled){
		if(!Ext.isArray(btnIndex)){
			alert("参数类型错误,第一个参数必须是数组！");
			return;
		}
		var index;
		var extObj;
		for(index=0;index<btnIndex.length;index++){
			extObj=Ext.getCmp(btnIndex[index]);
			if(extObj){
				if(isEnabled){
					extObj.enable();
				}else{
					extObj.disable();
				}
			}
		}
	};
	//获取系统自动生成的编码
	matech.getAutoCode=function(_autoId,_refer,_value){
		if(!_autoId || _autoId==""){
			alert("自动编码生成关键字不能为空!");
			return;
		}
		var _url = MATECH_SYSTEM_WEB_ROOT+"/common.do?method=autoCode&autoId="+_autoId;
		var _param = "&refer="+_refer+"&value="+_value;
		
		var strResult = ajaxLoadPageSynch(_url,_param) ;
		
		return strResult;		
	};
	
	//从一串条件字符串"unit_id=10001,datayear=2012"中获取指定条件的值	
	matech.getValueFromQueryConditionStr=function(condStr,sParamStr){
		var str=condStr.toLowerCase();
		sParamStr=sParamStr.toLowerCase();
		
		var i=str.indexOf(sParamStr,0);	
		if(i>=0){
			var j=str.indexOf(",",i);
			if(j<0){
				j=str.length;
			}
			return condStr.substring(i+sParamStr.length, j);
		}else{
			return "";
		}
	};	
	//从一串条件字符串"unit_id=10001,datayear=2012"中删除指定条件的值	
	matech.removeValueFromQueryConditionStr=function(condStr,sParamStr){
		var str=condStr.toLowerCase();
		sParamStr=sParamStr.toLowerCase();
		
		var i=str.indexOf(sParamStr,0);	
		if(i>=0){
			var j=str.indexOf(",",i);
			if(j<0){
				j=str.length;
			}
			if(i==0){
				return condStr.substring(0,i)+condStr.substring(j,condStr.length);
			}else{
				return condStr.substring(0,i-1)+condStr.substring(j,condStr.length);
			}
		}else{
			return condStr;
		}
	};
	//根据文件类型用div图片进行渲染
	matech.photoRender=function(fileType){
		var s;
		if(fileType=="0"){
    		s = '<div style="width:15px;height:15px">' +
    		 	'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/pap.png"></div>';
     		return s;
		}				 
		if(fileType.indexOf("xls")>-1){
			s = '<div style="width:15px;height:15px">' +
	    		'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/excel.png"></div>';
	     	return s;
		}
		if(fileType.indexOf("doc")>-1){
			s = '<div style="width:15px;height:15px">' +
    		 	'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/word.png"></div>';
     		return s;
		}
		if(fileType.indexOf("pdf")>-1){
			s = '<div style="width:15px;height:15px">' +
				'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/pdf.png"></div>';
 			return s;
		}
		if(fileType.indexOf("jpg")>-1||fileType.indexOf("png")>-1||fileType.indexOf("gif")>-1){
			s = '<div style="width:15px;height:15px">' +
    		 	'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/pic.png"></div>';
     		return s;
		}else{
			s = '<div style="width:15px;height:15px">' +
    		 	'<img style="width:100%" src="'+TEMP_SYSTEM_WEB_ROOT+'/images/ext/other.png"></div>';
     		return s;
		}		
	};
	//获取URL中的参数
	matech.getUrlRequest=function(_url){
		    var theRequest = new Object();
	   		var _index=_url.indexOf("?");
	   		if (_index>-1) {
		      var str =_url.substr(_index+1);
		      strs = str.split("&");
		      for(var i = 0; i < strs.length; i ++) {
		      	if(strs[i]|| strs.indexOf("=")>-1){
		         	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
		        }
		      }
		   }
		   return theRequest;		
	};
	//获取URL中的参数
	matech.getStrRequest=function(_request){
		var theRequest = new Object();
	      strs =_request.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	    	 if(strs[i]|| strs.indexOf("=")>-1){
	    	 	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	    	 }
	      }
		  return theRequest;		
	};
	
	//调用拍摄设备
	matech.callCamere = function(parent,_key) {
		if(_key==""){
			alert("请选择附件拍摄需要说明的对象，如底稿、疑点等！");
			return;
		}
		if(!parent.showCamere){
			parent=parent.parent;
		}
		if(!parent.showCamere){
			parent=parent.parent;
		}
		if(!parent.showCamere){
			parent=parent.parent;
		}
		if(!parent.showCamere){
			alert("找不到拍摄设备,请联系管理员！");
			return;
		}
		parent.showCamere(_key);        	
    };
    
}());

//在一个页面获取另外一个页面url传过来的参数 -- 方法一
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null){
    	return unescape(r[2]);
    } else{
    	return null;
    }
}

//在一个页面获取另外一个页面url传过来的参数 -- 方法二
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

function hideinfo(){  
     window.status="";
}  
//document.onmouseover=hideinfo; //鼠标移上时调用 hideinfo 函数  
//document.onmousemove=hideinfo; //鼠标移动时调用 hideinfo 函数  
//document.onmousedown=hideinfo; //鼠标按下时调用 hideinfo 函数   
