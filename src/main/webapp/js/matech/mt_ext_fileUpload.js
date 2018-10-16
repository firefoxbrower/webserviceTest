/*****上传下载控件************************************/   
	var attachUploadWin = null;
	var attachUploadForm = null;
	//获取附件数量
	matech.getAttachCount=function (inputId) {
		var inputObj = document.getElementById(inputId);
		var prefix = inputObj.id;
		var attachUlId = "attachUl_" + prefix; 
		
		return document.getElementById(attachUlId).children.length;
	};
	//检查最大附件数
	matech.checkMaxAttach=function (inputId) {

		var inputObj = document.getElementById(inputId);
		var maxAttach = inputObj.maxAttach || 0;
		
		if(maxAttach != 0 && matech.getAttachCount(inputId) >= maxAttach) {
			alert("对不起，只允许上传" + maxAttach + "个文件,请先删除后再上传!");
			return false;
		} else {
			return true;
		}
	};	
	//上传附件
	matech.attachUpload=function (inputId) {
		
		var inputObj = document.getElementById(inputId);
		
		var indexTable = inputObj.indexTable;
		var indexId = inputObj.value;
		var callback = inputObj.ext_callback;
		var handler = inputObj.handler || "CommonHandler";
		
		//文件类型选择
		var fileType = inputObj.ext_filetype;
		var fileTypeReg="";
		if(fileType){
			var fileTypeArray=fileType.toLowerCase().split("|");
			Ext.each(fileTypeArray,function(_obj){
				if(fileTypeReg==""){
					fileTypeReg="fileTypeReg=/\\.("+_obj;
				}else{
					fileTypeReg=fileTypeReg+"|"+_obj;
				}
			});
			fileTypeReg=fileTypeReg+")$/";
			eval(fileTypeReg);
		}else{
			fileTypeReg=/.*/;
		}
		
		if(!matech.checkMaxAttach(inputId)) {
			return;
		}
		if(attachUploadForm == null) {
			attachUploadForm = new Ext.FormPanel({
				url: "",
				border:false,
		        fileUpload: true,
		        autoHeight: true,
		        autoWidth: true,
		        frame: true,
				bodyStyle: 'padding: 5px;',
		        labelWidth: 1,
		        defaults: {
		            anchor: '95%',
		            allowBlank: false,
		            msgTarget: 'side',
		            regex:fileTypeReg
		        },
		        items: [{
		            xtype: 'fileuploadfield',
		            id: 'form-file',
		            emptyText: '请选择需要上传的文件',
		            name: 'attachPath',
		            buttonText: '',
		            buttonCfg: {
		            	text:'选择文件'
		            }
		        }]
		    });
		} else {
			attachUploadForm.getForm().reset();
		}
		
	
		//每次重置表单url地址
		attachUploadForm.form.url = MATECH_SYSTEM_WEB_ROOT + '/common.do?method=attachUpload&handler='+handler+'&indexTable=' + indexTable + "&indexId=" + indexId;
		//改为每次创建新窗口
		attachUploadWin = new Ext.Window({
			title: '文件上传',
			width: 500,
			height:116,
			modal:true,
			resizable:false,
			layout:'fit',
			closeAction:'hide',
			items: attachUploadForm,
			buttons: [{
				text: '确定',
				icon:MATECH_SYSTEM_WEB_ROOT + '/img/confirm.gif',
				handler: function(){
		             if(attachUploadForm.getForm().isValid()){
		             	// 显示进度条
		             	Ext.MessageBox.show({ 
							    title: '上传文件', 
							    width:240, 
							    progress:true, 
							    closable:false
							}); 
							
							var formUrl = MATECH_SYSTEM_WEB_ROOT + "/common.do?method=getAttachNumber";
							var formRequest = "&indexId=" + indexId+"&handler="+handler;
							var beforeLength = ajaxLoadPageSynch(formUrl, formRequest);
						
							// 提交表单
			                attachUploadForm.getForm().submit();
			                
			                var i = 0;
						    var timer = setInterval(function(){
								// 请求事例
								Ext.Ajax.request({
									url: MATECH_SYSTEM_WEB_ROOT + '/common.do?method=attachUploadProcess&rand=' + Math.random(),
									method: 'post',
									// 处理ajax的返回数据
									success: function(response, options){
										status = response.responseText + " " + i++;
										var obj = Ext.util.JSON.decode(response.responseText);
										if(obj.success!=false){
											
										var url = MATECH_SYSTEM_WEB_ROOT + "/common.do?method=getAttachNumber";
										var request = "&indexId=" + indexId+"&handler="+handler;
										var afterLengh = ajaxLoadPageSynch(url, request);
																			
										if(afterLengh>beforeLength){	
											if(obj.finished){
												clearInterval(timer);	
												// status = response.responseText;
												Ext.MessageBox.updateProgress(1, 'finished', 'finished');
												Ext.MessageBox.hide();
												attachUploadWin.hide();
												matech.attachInit(inputId);
												
												if(callback){
													eval(callback+"("+Ext.util.JSON.encode(obj.params)+")");
												}
											} else {
												Ext.MessageBox.updateProgress(obj.percentage, obj.msg);	
											}
										}else{
												Ext.MessageBox.updateProgress(obj.percentage, obj.msg);	
										}
										
										}
									},
									failure: function(){
										clearInterval(timer);
										Ext.Msg.alert('错误', '上传文件出错。');
									} 
								});
						    }, 500);
		             }
		         }
			},{
	         text: '重置',
	         icon:MATECH_SYSTEM_WEB_ROOT + '/img/refresh.gif',
	         handler: function(){
	             attachUploadForm.getForm().reset();
	         }
	    	},{
	    		text: '取消',
	    		icon:MATECH_SYSTEM_WEB_ROOT + '/img/close.gif',
	    		handler: function(){
	    			attachUploadWin.hide();
	    		}
	    	}]
	 });
		attachUploadWin.show();
	};

	matech.attachInit=function (inputId) {

		var inputObj = document.getElementById(inputId);
		
		// 按钮文字,默认为添加附件
		var buttonText = inputObj.buttonText || "添加附件";
		
		var handler = inputObj.handler || "CommonHandler";
		
		var showButton = true;
		var remove = true;
		
		if(inputObj.readOnly) {
			showButton = false;
			remove = false;
		}
		
		var _showButton=inputObj.showButton||"";
		if(_showButton!=""){
			if(_showButton=="true"){
				showButton=true;
			}else{
				showButton=false;
			}			
		}
		
		var _showRemove=inputObj.showRemove||"";

		if(_showRemove!=""){
			if(_showRemove=="true"){
				remove=true;
			}else{
				remove=false;
			}
		}
		
		//不再单独控制，通过只读来设置
		//
		// 是否显示上传按钮,默认为true
		//var showButton = inputObj.showButton == false ? false : true;
		// 是否允许删除,默认为true
		//var remove = inputObj.remove == false ? false : true;
		
		var indexTable = inputObj.indexTable;
		
		if(inputObj.value == "") {
			alert("上传附件控件初始值为空,无法初始化...");
			return;
			//inputObj.value = new UUID().createUUID();
		}
		
		var indexId = inputObj.value;
		var prefix = inputObj.id;
		
		var url = MATECH_SYSTEM_WEB_ROOT + "/common.do?method=getAttachList";
		var request = "handler="+handler+"&indexTable=" + indexTable + "&indexId=" + indexId;
		
		var result = ajaxLoadPageSynch(url, request);
		
		var attachList = Ext.util.JSON.decode(result);

		var html = "";
		for(var i=0; i < attachList.length; i++) {
			var attach = attachList[i];
			if(matech.parentObj){
				html += "<li>"
					  + "<span>"
					  + "<a href=\"#\" onclick=\"matech.openAttach('"+handler+"','" + attach.attachId + "');\" title=\"下载：" + attach.attachName + "\">" + maxString(attach.attachName) + "</a>"
					  + "&nbsp;<font style=\"color:#CCCCCC;\">" + formatDecimal((attach.fileSize/1024),2) + " KB</font>"
					  + "</span>"
					  + "&nbsp;<a href=\"#\" onclick=\"matech.openAttach('"+handler+"','" + attach.attachId + "');\" title=\"下载：" + attach.attachName + "\"><img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/img/download.gif\"></a>";				
			}else{
				html += "<li>"
					  + "<span>"
					  + "<a href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\">" + maxString(attach.attachName) + "</a>"
					  + "&nbsp;<font style=\"color:#CCCCCC;\">" + formatDecimal((attach.fileSize/1024),2) + " KB</font>"
					  + "</span>"
					  + "&nbsp;<a href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\"><img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/img/download.gif\"></a>";			
			}
			if(remove) {
				html += "&nbsp;<a href=\"#\" onclick=\"matech.attachRemove('" + attach.attachId + "','" + inputId +"','" + handler + "');\" title=\"删除\"><img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/img/delete.gif\"></a>";
			}
			
			html += "</li>";
		}
		//alert(11);
		var attachUlId = "attachUl_" + prefix; 
		var attachButtonId = "attachButton_" + prefix;
		var attachDivId = "attachDiv_" + prefix;
		
		var ul = document.getElementById(attachUlId);
		if(ul == null || !ul) {
			
			var divObj = document.createElement("<div id=\"" + attachDivId + "\"></div>");
						
			divObj = inputObj.parentElement.insertBefore(divObj);
			
			var buttonDiv = document.createElement("<div id=\"" + attachButtonId +"\"></div>");
			ul = document.createElement("<ul id=\"" + attachUlId + "\"></ul>");
			
			divObj.appendChild(buttonDiv);
			divObj.appendChild(ul);
		}else{
			ul.innerHTML="";
		}

		ul.innerHTML = html;
		
		// 是否显示按钮
		if(showButton) {
			var attachButton = document.getElementById(attachButtonId);
			attachButton.innerHTML = "<input type=\"button\" class=\"flyBT\" value=\"" + buttonText + "\" onclick=\"matech.attachUpload('" + inputId + "')\" ><br/><br/>";
		}
	};

	
	matech.attachInitHtml=function (indexTable,indexId,callback,handler) {
		handler = handler||"CommonHandler";
		
		var url = MATECH_SYSTEM_WEB_ROOT + "/common.do?method=getAttachList";
		var request = "handler="+handler+"&indexTable=" + indexTable + "&indexId=" + indexId;
		
		var result = ajaxLoadPageSynch(url, request);
		
		var attachList = Ext.util.JSON.decode(result);
		
		var html = "";
		for(var i=0; i < attachList.length; i++) {
			var attach = attachList[i];
			if(callback){
				html += "<li>"
					  + "<span>"
					  + "<a onclick=\""+callback+"('"+indexId+"')\" href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\">" + maxString(attach.attachName) + "</a>"
					  + "&nbsp;<font style=\"color:#CCCCCC;\">" + formatDecimal((attach.fileSize/1024),2) + " KB</font>"
					  + "</span>"
					  + "&nbsp;<a onclick=\""+callback+"('"+indexId+"')\" href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\"><img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/img/download.gif\"></a>";
				
				html += "</li>";							
			}else{
				html += "<li>"
					  + "<span>"
					  + "<a href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\">" + maxString(attach.attachName) + "</a>"
					  + "&nbsp;<font style=\"color:#CCCCCC;\">" + formatDecimal((attach.fileSize/1024),2) + " KB</font>"
					  + "</span>"
					  + "&nbsp;<a href=\"" + MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachDownload&handler="+handler+"&attachId=" + attach.attachId + "\" title=\"下载：" + attach.attachName + "\"><img src=\"" + MATECH_SYSTEM_WEB_ROOT + "/img/download.gif\"></a>";
				
				html += "</li>";				
			}
		}

		var divObj = "<div><ul>"+html+"</ul></div>";
		
		return divObj;
	};
	
	//删除附件
	matech.attachRemove=function (attachId,inputId,handler) {

		var url = MATECH_SYSTEM_WEB_ROOT + "/common.do?method=attachRemove";
		var request ="handler="+handler+"&attachId=" + attachId;
		var result = ajaxLoadPageSynch(url, request);
		
		if(result == "success") {
			matech.attachInit(inputId);
		}
	};	 
	//打开附件
	matech.openAttach=function(handler,attachId){
		var parent=matech.parentObj;
		if(!parent.showAttach){
			parent=parent.parent;
		}
		if(!parent.showAttach){
			parent=parent.parent;
		}
		if(!parent.showAttach){
			parent=parent.parent;
		}
		if(!parent.showAttach){
			alert("找不到附件下载控件,请联系系统管理员！");
			return;
		}
		parent.showAttach(handler,attachId);
		
	};	
	
	Ext.onReady(function (){
		var inputArray ;

		inputArray = Ext.query("input[ext_type=attachFile]") ;
		
		Ext.each(inputArray,function(input){
			matech.attachInit(input.id);
		});
	}) ;
	