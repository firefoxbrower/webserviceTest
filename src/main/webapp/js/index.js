var _tid=0;
var shown=false;

function hideAllMenu()
{
//ï¿½ï¿½Îªï¿½ï¿½ï¿½ï¿½Ò³ï¿½ï¿½Ã»ï¿½ï¿½ï¿½ï¿½È«loadï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ã»ï¿½ï¿½ï¿½ï¿½Íµï¿½ï¿½ï¿½ï¿½bodyï¿½ï£¬ï¿½ï¿½Ê±ï¿½ï¿½ï¿½ï¿½ÎªoTableÃ»ï¿½Ð£ï¿½ï¿½Í»ï¿½ï¿½ï¿½ï¿½
	try{
		var i = 0;
		var oTable = document.getElementById( "TABLE_Menu" );
		
		for( i = 0; i < oTable.rows(0).cells.length; i++ )
		{/*
			if(oTable.rows(0).cells(i).className == "menutopic_over"){    //----20130930改
				
				oTable.rows(0).cells(i).className = "menutopic";
				
			}*/
				
		}
	}catch(e){}
}


function hideAllMenu1(){
	//$('.divmenu').css("display","none") ;
	//$('.menutopic_over').addClass('menutopic');
	//$('.menutopic_over').removeClass('menutopic_over');	
	//hideIframe();
}

function hideAllMenu2(){
	
	$('.divmenu').css("display","none") ;
	$('.menutopic_over').addClass('menutopic');
	
	$('.menutopic_over').removeClass('menutopic_over');	
	hideIframe();
}

function hidex(o)
{
	if(o)
	o.style.display="none";
}
//Ò»ï¿½ï¿½ï¿½Ëµï¿½mouseoutï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ÎºÎ²ï¿½ï¿½ï¿½
function hideMenu(sID){
	
}
/**
 * ï¿½ï¿½ï¿½Ø²Ëµï¿½
 * @param sID
 */
function hideMenu2( sID )
{
	shown=false;
	//clearTimeout(_tid);
	
	var x = 0, y = 0;
	var oMenu = document.getElementById( sID );
	if(oMenu==null)
		return;
	
	var left = parseInt(oMenu.style.left);
	var top = parseInt(oMenu.style.top);
	var bottom = parseInt(oMenu.style.top) + oMenu.clientHeight;
	var right = parseInt(oMenu.style.left) + oMenu.clientWidth;
	
	x = event.clientX-2;// + document.body.scrollLeft;
	y = event.clientY;// + document.body.scrollTop;
	if( x < left || x > right || y < top || y > bottom ){
		
		oMenu.style.display = "none";
		oMenu.style.pixelLeft=0;
		oMenu.style.pixelTop=0;
		var i = 0;var oTable = document.getElementById( "TABLE_Menu" );
		 
		for( i = 0; i < oTable.rows[0].cells.length; i++ ){
			if(oTable.rows[0].cells[i].className.indexOf("menutopic_over")!=-1 )
				oTable.rows[0].cells[i].className = "menutopic";
		}		
	}
	//ie6ï¿½ï¿½ï¿½ÂµÄ°æ±¾ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ëµï¿½ï¿½á½«ï¿½ï¿½Î»Ñ¡ï¿½ï¿½ï¿½ï¿½ï¿½×¡ï¿½ï¿½ï¿½ï¿½ï¿½Òªï¿½ï¿½ï¿½â´¦ï¿½ï¿½
	var IEversion = parseFloat(navigator.appVersion.split("MSIE")[1]);
	if (IEversion < 7)
	{
		var oDrop = document.frames[0].document.getElementById("ID_SelectCompany");
		if (oDrop)
			oDrop.style.visibility = "visible";
	}
   if($("#"+sID).css("display")=="none"){
	   hideIframe();
   }
}

function showMenu(sID,o)
{
	if(!shown)
		//_tid=setTimeout(function(){showMenu2(sID,o)},200);
		showMenu2(sID,o);
	else
		showMenu2(sID,o);
}

function showMenu2( sID, o )
{
	hideAllMenu2();
	
	shown=true;
	var i = 0;
	//var oTD = event.srcElement;
	var oTD = o;

	var oTable = oTD.parentNode.parentNode.parentNode;
	
	//alert(oTable.rows[0].cells.length);
	for( i = 0; i < oTable.rows[0].cells.length; i++ )
	{
		//oTable.rows[0].cells[i].attachEvent("onMouseOver",showMenu); 
		if(oTable.rows[0].cells[i].className == "menutopic_over" ){
			oTable.rows[0].cells[i].className = "menutopic";
		}
			
	}
	
	//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ó²Ëµï¿½
	for( var i = 0; i < 20; i++ )
	{
		oDiv = document.getElementById( "DIV_xmenu_" + i.toString() );
		if(oDiv!=null)
			if(oDiv.style.display!="none")
				oDiv.style.display="none";
	}
	var oMenu = document.getElementById( sID );
	
	
	if(oMenu==null)
		return false;
	
	//ï¿½ï¿½ï¿½Ã»ï¿½ï¿½ï¿½Ó²Ëµï¿½ï¿½ï¿½ï¿½Í»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ò»ï¿½ï¿½ï¿½ï¿½Ê½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ê½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ü¶ï¿½ï¿½Ð±ß¿ï¿½
	if(oMenu.getElementsByTagName("DIV").length==0)
	{
		oTD.className = "menutopic_over2";
		return false;
	}
	else{
		oTD.className = "menutopic_over";
	}
		

	
	//ï¿½ï¿½ï¿½â²¿ï¿½Ê¼ï¿½ï¿½Ëµï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½×ªï¿½ï¿½ÎªÊµï¿½Êµï¿½ï¿½Ê¼ï¿½ï¿½ï¿½Ö·
	if (oMenu.innerHTML.indexOf("*mail*")!=-1)
	{
		oMenu.innerHTML = oMenu.innerHTML.replace(/\*mail\*/g,g_sUserMailFile);
	}
	//oMenu.style.display = "";

	//setTimeout("fade('"+oMenu.id+"')",100);
	var s = "";
	var l = 0, t = 0;
	t = parseInt(o.offsetHeight);
	
	while( o != null )
	{
//		s += o.tagName + " " + o.height + "\n";
		l += o.offsetLeft;
		t += o.offsetTop;
		o = o.offsetParent;
	}
	
//	alert(l+" "+t);
//	if(g_sUserMailFile.indexOf("liubin")!=-1)
	oMenu.style.top = t+"px";
	oMenu.style.display = "block";
	
//	alert(typeof(document.body.clientWidth)+ " " + document.body.clientWidth + "\nMenu width: " + oMenu.clientWidth + " MenuID: " + oMenu.id);
	var ww=document.body.clientWidth,w=oMenu.clientWidth;
	//ï¿½ï¿½ï¿½Î»ï¿½Ã²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ«ï¿½ï¿½Ò»ï¿½Â¡ï¿½ï¿½ï¿½22ï¿½ï¿½ï¿½Ç¹ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ä¿ï¿½ï¿½
	if(w>ww-l)
		oMenu.style.left = ((ww-w-22)<0?"0":((ww-w-22).toString()))+"px";
	else
		oMenu.style.left = l+"px";
	
	//ie6ï¿½ï¿½ï¿½ÂµÄ°æ±¾ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ëµï¿½ï¿½á½«ï¿½ï¿½Î»Ñ¡ï¿½ï¿½ï¿½ï¿½ï¿½×¡ï¿½ï¿½ï¿½ï¿½ï¿½Òªï¿½ï¿½ï¿½â´¦ï¿½ï¿½
	var IEversion = parseFloat(navigator.appVersion.split("MSIE")[1]);
	if (IEversion < 7)
	{
		var oDrop = document.frames[0].document.getElementById("ID_SelectCompany");
		if (oDrop)
		{
			var pos = sID.lastIndexOf("_");
			if (parseInt(sID.substring(pos+1,sID.lengh))<=2)
			{
				oDrop.style.visibility = "hidden";
			}else{
				oDrop.style.visibility = "visible";
			}
		}
	}
	var $menu = $('#'+sID) ;
	
	createIframe({top:$menu.offset().top,left:$menu.offset().left,width:$menu.width(),height:$menu.height()}) ; 
}

function createIframe(coordinate) {

	var myIframe = document.getElementById("myMenuIframe") ;
	
	if(!myIframe){
		 var myIframe = document.createElement('iframe');  
		 myIframe.id='myMenuIframe';  
		 myIframe.style.zIndex='0'; 
		 myIframe.setAttribute('frameborder','0');  
		 myIframe.setAttribute('src',''); 
		 document.getElementById("iframe-div").appendChild(myIframe);  
	}
	
    myIframe.style.position='absolute';  
    myIframe.style.top=coordinate.top;  

    myIframe.style.left=coordinate.left-1;     
    myIframe.style.width=coordinate.width+2;   
    myIframe.style.height=coordinate.height; 
    myIframe.style.filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';
    myIframe.style.display = "" ;
   
   
} 

function hideIframe() {
	var myIframe = document.getElementById("myMenuIframe") ;
	
	if(myIframe){
		 myIframe.style.display = "none" ;
	}
} 

//Õ¹ï¿½ï¿½ï¿½ï¿½ï¿½Ûµï¿½ï¿½ï¿½Í·
function TopCollapse(t)
{
	var height=Ext.getCmp("center-panel").getHeight();
	if(t.className == "current_03")
	{
		$("#DIV_TOP").css("display","none");
		Ext.getCmp("north-panel").setHeight(28);
		Ext.getCmp("center-panel").setHeight(height+70);
		document.getElementById("homepageHead").style.height = "25px";
		t.className = "current_03_active";
	}
	else{
		$("#DIV_TOP").css("display","block");
		$("#DIV_TOP").css("height","70px");
		Ext.getCmp("north-panel").setHeight(98);
		Ext.getCmp("center-panel").setHeight(height-70);
		t.className = "current_03";
	}
	Ext.getCmp("indexLayout").doLayout();
}

