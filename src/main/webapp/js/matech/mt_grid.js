function extDatagridSearch(tableId, limitValue,isRefresh)
{
	
  var sv='';
  for(var i=0;i<eval('sqlWhereVariables_'+tableId).length;i++){
   	var oTT=document.getElementById(eval('sqlWhereVariables_'+tableId)[i]);
  		if(!oTT)continue;
   		sv=sv+"<"+eval('sqlWhereVariables_'+tableId)[i]+">"+oTT.value+"</"+eval('sqlWhereVariables_'+tableId)[i]+">";
  }
  eval("getData_"+tableId+"(sv,document.getElementById(\"page_xml_\"+tableId).value,0,limitValue,isRefresh)") ;
}

function extDatagridSearch2(tableId){
	  //取得当前页面
	  var _currentPage=1;
	  if(Ext.getCmp("pagingToolbar_"+tableId)){
		  _currentPage=Ext.getCmp("pagingToolbar_"+tableId).get(4).getValue();
		  if(!_currentPage||!(_currentPage>=1)){
			  _currentPage=1;
		  }
	  }
	  Ext.getCmp("pagingToolbar_"+tableId).changePage(_currentPage); 
}
//显示当前页码currentPage的数据
function setDatagridSearchByPage(tableId,currentPage){
	  if(!currentPage||!(currentPage>=1)){
		  return;
	  }
	  if(Ext.getCmp("pagingToolbar_"+tableId)){	
		  var _currentPage=Ext.getCmp("pagingToolbar_"+tableId).get(4).getValue();
		  if(currentPage==_currentPage){
			  return;
		  }
		  Ext.getCmp("pagingToolbar_"+tableId).changePage(currentPage);
	  }			  
	  /*
	  var _setDatagridSearchByPageInterval;
	  _setDatagridSearchByPageInterval=setInterval(DatagridSearchByPage,500);
	  function DatagridSearchByPage(){
		  if(Ext.getCmp("pagingToolbar_"+tableId)){	
			  var _currentPage=Ext.getCmp("pagingToolbar_"+tableId).get(4).getValue();
			  if(currentPage==_currentPage){
				  return;
			  }
			  Ext.getCmp("pagingToolbar_"+tableId).changePage(currentPage);
			  clearInterval(_setDatagridSearchByPageInterval);
		  }		  
	  }*/
}
//取得当前页码
function getDatagridCurrentPage(tableId){
	  var _currentPage=1;
	  if(Ext.getCmp("pagingToolbar_"+tableId)){
		  _currentPage=Ext.getCmp("pagingToolbar_"+tableId).get(4).getValue();
		  if(!_currentPage||!(_currentPage>=1)){
			  _currentPage=1;
		  }
	  }	  
}

function extMoneyFormat(val) {

	if(val < 0){
		val = '<span style="color:red" onselectstart="return false">' + Ext.util.Format.number(val,'0,000.00') + "</span>";
	} else {
		val =  Ext.util.Format.number(val,'0,000.00');
	}
	
	return val;
}


function getPrintData(tableId,action) {
	var printContainer = Ext.get("printContainer_"+tableId).dom ;
	Ext.Ajax.request({
		method:'POST',
		url:MATECH_SYSTEM_WEB_ROOT+'/extGridPrint?tableId='+tableId ,
		success:function (response,options) {
			printContainer.innerHTML = "";
			printContainer.innerHTML = response.responseText;
		},
		failure:function (response,options) {
			alert("打印参数设置错误");
		}
	});
}

//行单选
function setChooseValue(obj,tableId) {
	var chooseValue = document.getElementById("chooseValue_"+tableId) ;
	chooseValue.value = obj.value ;
}

//多选
function getChooseValue(tableId) {
	var chooseValue = document.getElementsByName("choose_"+tableId) ;
	var str = "";
	for(var i=0;i<chooseValue.length;i++) {
		if(chooseValue[i].checked && chooseValue[i].value != "") {
			str += chooseValue[i].value + "," ;
		}	
	}
	
	if(str != "") {
		str = str.substr(0,str.length-1);
	}
	
	return str ;
}

function selectAllChooseValue(obj,tableId) {
	var chooseValue = document.getElementsByName("choose_"+tableId) ;
	for(var i=0;i<chooseValue.length;i++) {
		if(!chooseValue[i].disabled) {
			chooseValue[i].checked = obj.checked ;	
		}
	}
}

//创建计算器
var calWin = null;
function createcalculater(tableId) {
 	
 	var divObj = document.getElementById("calculater");
 	if(!divObj) {
 		divObj = document.createElement("<DIV id=\"calculater\" style=\"position:absolute;width:expression(document.body.clientWidth);height:20;left:0;bottom:45;padding:10 0 10 0;\"></div>") ;
 		document.body.insertBefore(divObj,document.body.firstChild);
 		
		if(!calWin) {
		    calWin = new Ext.Window({
		     title: '计算器',
		     renderTo :'calculater',
		     width: document.body.clientWidth,
		     height:20,
		        closeAction:'hide',
		        listeners   : {
		        	'hide':{fn: function () {
						calWin.hide();	         	
		        	}}
		        },
		      layout:'fit',
			  html:'<input type="<input type="text" size="120" id="sText" onpropertychange="calculateValue()" value="" /> = <input type="text" size="30" id="sValue" value="" />'
			  		+'<button onclick="calculatorReset(\''+tableId+'\')" >重置</button>'
		    });
	   }
	  
 	}
 	 calWin.show();
 }
 
String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
};


function calculateValue() {
	var sTextValue = sText.value;
	try {
		sTextValue =  eval(sTextValue.replaceAll(",",""));
		sValue.value = Ext.util.Format.number(sTextValue,'0,000.00') ;
	}catch(e){}
}

function calculatorReset(tableId) {
	sText.value='';
	sValue.value='';
	Ext.getCmp("gridId_"+tableId).getSelectionModel().clearSelections();
	
	Ext.getCmp("gridId_"+tableId).getSelectionModel().selectedArea='';
}

function expExcel(tableId) {
	//var grid = Ext.getCmp("gridId_"+tableId);
	//grid.exportExcel();
	window.open(MATECH_SYSTEM_WEB_ROOT + '/common.do?method=expExcel&tableId='+tableId);
}


//自定义查询
function customQryWinFun(tableId) {
	var customQryWin = this["customQryWin_"+tableId] ;
	document.getElementById("customQry_"+tableId).style.display = "";
	
	if(customQryWin == null) { 
		customQryWin = new Ext.Window({
			title: '自定义查询条件',
			width: 600,
			height:300,
			contentEl:'customQry_'+tableId, 
	        closeAction:'hide',
	        autoScroll:true,
	        modal:true,
	        listeners:{
				'hide':{fn: function () {
					 document.getElementById("customQry_"+tableId).style.display = "none";
				}}
			},
	        layout:'fit',
		    buttons:[{
	            text:'确定',
	            icon:MATECH_SYSTEM_WEB_ROOT+'/img/confirm.gif',
	          	handler:function() {
	          		var qryWhere = createQryWhere(tableId);
	          		if(qryWhere == false) return ;
	          		document.getElementById("qryWhere_"+tableId).value = qryWhere ;
	          		eval("goSearch_"+tableId+"();");
	          		customQryWin.hide();
	          	}
	        },{
	            text:'显示全部',
	            icon:MATECH_SYSTEM_WEB_ROOT+'/img/refresh.gif',
	          	handler:function() {
	          		document.getElementById("qryWhere_"+tableId).value = "all" ;
	          		eval("goSearch_"+tableId+"();");
	          		customQryWin.hide();
	          	}
	        },{
	            text:'取消',
	            icon:MATECH_SYSTEM_WEB_ROOT+'/img/close.gif',
	            handler:function(){
	            	customQryWin.hide();
	            }
	        }]
	    }); 
		this["customQryWin_"+tableId] = customQryWin ;
		
		var qryWhere = document.getElementById("qryWhere_"+tableId).value ;
		if(qryWhere != "") {
			var qryWhereArr = qryWhere.split("and") ; 
			for(var i=0;i<qryWhereArr.length;i++){
				if(qryWhereArr[i] != "") {
					var qryOrArr = qryWhereArr[i].split("or") ;
					for(var j=0;j<qryOrArr.length;j++){
						var trimqryOrArr = qryOrArr[j].replace(new RegExp(" ","gm"),""); 
						if(qryOrArr[j] != "" && trimqryOrArr != "") {
							addQuery(tableId,false,qryOrArr[j]);
						}
						
					}
				}
			}
		}else {
			addQuery(tableId,true);
		}
		
	}
	
	customQryWin.show();
}

function addQuery(tableId,first,nameValue) {

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
		
		var id = columns[i].freequery || columns[i].id||'' ;
		
		var header = columns[i].header||'' ;
		var hidden = columns[i].hidden ;
		
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
		tdObj.innerHTML = "<a href='#' onclick='removeQuery(this);' ><img src=" + MATECH_SYSTEM_WEB_ROOT + "/img/delete.gif></a>" ;
	}
	
}


function removeQuery(obj) {

	var tbody = obj.parentElement.parentElement.parentElement ;
	var trObj = obj.parentElement.parentElement ;
	if(trObj) {
		tbody.removeChild(trObj);
	}
}


function createQryWhere(tableId) {
	
	var query_logic = document.getElementsByName("query_logic_"+tableId) ;
	var query_column = document.getElementsByName("query_column_"+tableId) ;
	var query_operator = document.getElementsByName("query_operator_"+tableId) ;
	var query_condition = document.getElementsByName("query_condition_"+tableId) ;
	
	var qryWhere = "" ;
	for(var i=0;i<query_logic.length;i++) {
		var logic = query_logic[i].value ;
		var column = query_column[i].value ;
		var operator = query_operator[i].value ;
		var condition = query_condition[i].value ;
		
		if(column == "") {
			alert("请选择列名,列名不得为空!") ;
			return false ;
		}
		
		if(operator.indexOf("like") > -1) {
			if(condition != "") {
				condition = "'%" + condition + "%'" ;
			}
		}else {
			condition = "'" + condition + "'" ;
		}
		
		qryWhere += " " + logic + " " + column + " " + operator + " " + condition ;
	}
	return qryWhere ; 
}


function mt_grid_destory(gridCmp,tableId){
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
	
}


function mt_gird_applyState(grid,state) {
	  
	  var cm = grid.colModel ;
	  
	  var colHide = state.colHide ;
	  var colSeq = state.colSeq ;
	  var colSort = state.colSort ;
	  var colWidth = state.colWidth ;
	  
	  var store = grid.getStore();
	  
	  //设置列隐藏
	  if(colHide){
		  colHide = Ext.decode(colHide) ;
		  for(var i in colHide) {
			  var column = cm.getColumnById(i);
			  if(column) {
				  var colIndex = cm.getIndexById(i) ;
				  cm.setHidden(colIndex,colHide[i]);
			  }
		  }
	  }
	  
	  //设置列宽度
	  if(colWidth){
		  colWidth = Ext.decode(colWidth) ;
		  for(var j in colWidth) {
			  var column = cm.getColumnById(j);
			  if(column) {
				  var colIndex = cm.getIndexById(j) ;
				  cm.setColumnWidth(colIndex,colWidth[j]);
			  }
		  }
	  }
	  
	  //设置列顺序
	  if(colSeq){
		  colSeq = Ext.decode(colSeq) ;
		  for(var k in colSeq) {
			  var column = cm.getColumnById(k);
			  if(column) {
				  var colIndex = cm.getIndexById(k) ;
				  if(colIndex != colSeq[k]){
		              cm.moveColumn(colIndex,colSeq[k]);
		          }
			  }
		  }
	  }
	  
	  //设置排序
	  if(store && colSort){
		  colSort = Ext.decode(colSort) ;
          for(var z in colSort) {
 			  var column = cm.getColumnById(z);
 			  if(column) {
 				 store.setDefaultSort(z,colSort[z]);
 			  }
 		  }
       }
	
}

//清除操作习惯
function clearConfigWin(tableId) {
	var gridConfigWin = this["gridConfigWin_"+tableId] ;
	document.getElementById("gridConfig_"+tableId).style.display = "";
	
	if(gridConfigWin == null) { 
		gridConfigWin = new Ext.Window({
			title: '清除操作习惯',
			width: 420,
			height:280,
			contentEl:'gridConfig_'+tableId, 
	        closeAction:'hide',
	        autoScroll:true,
	        modal:true,
	        listeners:{
				'hide':{fn: function () {
					 document.getElementById("gridConfig_"+tableId).style.display = "none";
				}}
			},
	        layout:'fit',
		    buttons:[{
	            text:'确定',
	            icon:MATECH_SYSTEM_WEB_ROOT+'/img/confirm.gif',
	          	handler:function() {
	          		clearGridConfig(tableId) ;
	          		gridConfigWin.hide();
	          	}
	        },{
	            text:'取消',
	            icon:MATECH_SYSTEM_WEB_ROOT+'/img/close.gif',
	            handler:function(){
	            	gridConfigWin.hide();
	            }
	        }]
	    }); 
		this["gridConfigWin_"+tableId] = gridConfigWin ;
		
		
	}
	
	gridConfigWin.show();
}


function clearGridConfig(tableId){
	
	var colWidth = document.getElementById("colWidth_clear_"+tableId) ;
	var colSort = document.getElementById("colSort_clear_"+tableId) ;
	var colSeq = document.getElementById("colSeq_clear_"+tableId) ;
	var colHide = document.getElementById("colHide_clear_"+tableId) ;
	
	colWidth = colWidth.checked ;
	colSort = colSort.checked ;
	colSeq = colSeq.checked ;
	colHide = colHide.checked ;
	
	if(!colWidth && !colSort && !colSeq && !colHide) {
		alert("请选择要清除的类型！") ;
	}
	
	var request = "&tableId="+tableId ;
	
	if(colWidth) {
		request += "&colWidth=true" ;
	}
	
	if(colSort) {
		request += "&colSort=true" ;
	}
	
	if(colSeq) {
		request += "&colSeq=true" ;
	}
	
	if(colHide) {
		request += "&colHide=true" ;
	}
	
	var url = MATECH_SYSTEM_WEB_ROOT+"/system.do?method=clearGridConfig" ;
	var text = ajaxLoadPageSynch(url,request) ;
	if(text == "ok") {
		
		if(confirm("清除成功,是否需要刷新表格?")){
			window.location.reload();
		}
	}else {
		alert("后台发生异常,清除失败！");
	}
}